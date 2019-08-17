package com.example.superheros.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superheros.R
import com.example.superheros.adapters.ResultsAdapter
import com.example.superheros.models.Hero
import com.example.superheros.models.Result
import com.example.superheros.utils.EndPoint
import com.example.superheros.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchView.setOnQueryTextListener(this)
        recyclerViewHeros.layoutManager = LinearLayoutManager(this)
    }

    override fun onQueryTextSubmit(search: String?): Boolean {
        recyclerViewHeros.adapter = ResultsAdapter(ArrayList())
        searchView.clearFocus()
        progressBar.visibility = View.VISIBLE
        if (search != null) {
            getData(search)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    /*inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            var text: String
            val connection = URL(params[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            handleJson(result)
        }

    }*/

    private fun getData(search: String) {
        val retrofitCliente =
            NetworkUtils.getRetrofitInstance("https://superheroapi.com/api/2258940520886064/")

        val endpoint = retrofitCliente.create(EndPoint::class.java)
        val callback = endpoint.getHeros(search)

        callback.enqueue(object : Callback<Hero> {
            override fun onResponse(call: Call<Hero>, response: Response<Hero>) {
                if (response.body()?.response == "error") {
                    Toast.makeText(baseContext, "Character with given name not found!!", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                    return
                }


                val listResults = ArrayList<Result>()

                for (hero in response.body()?.results!!) {

                    listResults.add(
                        Result(
                            hero.id,
                            hero.name,
                            hero.powerstats,
                            hero.biography,
                            hero.appearance,
                            hero.work,
                            hero.connections,
                            hero.image
                        )
                    )
                }

                if (listResults != null) {
                    recyclerViewHeros.adapter = ResultsAdapter(listResults)
                    progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<Hero>, t: Throwable) {
                Toast.makeText(baseContext, "Character with given name not found!!", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                return
            }
        })
    }

    /*private fun handleJson(result: String) {

        if (result == "") {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Não encontrado!", Toast.LENGTH_LONG).show()
            return
        }

        val json = Json(JsonConfiguration.Default)

        val jsonData = json.parse(Hero.serializer(), result)

        val listResults = ArrayList<Result>()

        for (hero in jsonData.results) {

            listResults.add(
                Result(
                    hero.id,
                    hero.name,
                    hero.powerstats,
                    hero.biography,
                    hero.appearance,
                    hero.work,
                    hero.connections,
                    hero.image
                )
            )
        }
        if (listResults != null) {
            recyclerViewHeros.layoutManager = LinearLayoutManager(this)
            recyclerViewHeros.adapter = ResultsAdapter(listResults)
            progressBar.visibility = View.GONE
        }

    }*/
}

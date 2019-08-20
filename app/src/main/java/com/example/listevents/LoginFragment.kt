package com.example.listevents

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*
import androidx.appcompat.app.AppCompatActivity
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody

/**
 * Fragment representing the login screen for Shrine.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val json = JSONObject()

        val view = inflater.inflate(R.layout.login_fragment, container, false)

        view.login.setOnClickListener({
           println("inside")
            if (Build.VERSION.SDK_INT >= 9) {
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)
            }

            Users.setUsername("CHAITHRA")

            val gotresponse = registeredEvents("david")
            Users.setResponse(gotresponse) ///setting the response so that the next fragment can use it
            //val jsonarray = JSONArray(gotresponse)
            println("printing the response")
            println(gotresponse)


            // Navigate to the next Fragment.
            (activity as NavigationHost).navigateTo(RegisteredEvents(), false)
        })
       return view
    }
    private fun fetchInfo(): String {
       // val url = "https://apad19.appspot.com/list/"
        val url = "https://2107fc86.ngrok.io/userpagejson"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "Android")
            .build()
        print("************")
        print(request)
        val response = client.newCall(request).execute()
        val bodystr =  response.body().string() // this can be consumed only once

        return bodystr
    }

    fun registeredEvents(
        username: String?
    ): String {
        val url = "https://b7e99eec.ngrok.io/userpagejson"
        val json = """
{
    "user":"${username}"
}
""".trimIndent()

        val client = OkHttpClient()
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .header("User-Agent", "Android")
            .build()
        val response = client.newCall(request).execute()
        val bodystr = response.body().string() // this can be consumed only once

        return bodystr
    }




}

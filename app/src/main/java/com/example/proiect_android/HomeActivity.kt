package com.example.proiect_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.proiect_android.databinding.ActivityHomeBinding
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var textViewTime: TextView
    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var btn_GetStarted: Button
    private lateinit var buttonShowRecipes: Button
    private lateinit var buttonLogout: Button

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textViewTime = findViewById(R.id.textViewTime)
        btn_GetStarted = findViewById(R.id.btn_GetStarted)
        buttonShowRecipes = findViewById(R.id.buttonShowRecipes)
        buttonLogout = findViewById(R.id.buttonLogout)

        btn_GetStarted.text = "Search for Recipes"
        btn_GetStarted.setOnClickListener {
            val intent = Intent(this@HomeActivity, HomeActivity1::class.java)
            startActivity(intent)
            finish()
        }

        buttonShowRecipes.setOnClickListener {
            btn_GetStarted.visibility = Button.GONE
            buttonShowRecipes.visibility = Button.GONE
            buttonLogout.visibility = Button.GONE

            supportFragmentManager.commit {
                replace(R.id.fragment_container, RecipeListFragment())
                addToBackStack(null)
            }
        }

        buttonLogout.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this@HomeActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        firebaseAuth = FirebaseAuth.getInstance()

        val url = "https://worldtimeapi.org/api/timezone/Europe/Bucharest"
        FetchTimeTask().execute(url)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            btn_GetStarted.visibility = Button.VISIBLE
            buttonShowRecipes.visibility = Button.VISIBLE
            buttonLogout.visibility = Button.VISIBLE
        }
    }

    inner class FetchTimeTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val urlConnection = URL(params[0]).openConnection() as HttpURLConnection
            return try {
                val inputStream = urlConnection.inputStream
                inputStream.bufferedReader().use { it.readText() }
            } finally {
                urlConnection.disconnect()
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            result?.let {
                val jsonObject = JSONObject(it)
                val datetime = jsonObject.getString("datetime")
                val formattedDateTime = formatDateTime(datetime)
                textViewTime.text = formattedDateTime
            }
        }

        private fun formatDateTime(datetime: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE, MMM d, yyyy h:mm a", Locale.getDefault())
            val date: Date = inputFormat.parse(datetime)
            return outputFormat.format(date)
        }
    }
}

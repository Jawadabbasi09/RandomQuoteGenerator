package com.example.randomquotegenerator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val quoteTextView: TextView = findViewById(R.id.quoteTextView)
        val authorTextView: TextView = findViewById(R.id.authorTextView)
        val fetchButton: Button = findViewById(R.id.fetchButton)

        // Retrofit client setup
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.quotable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(QuoteService::class.java)

        // Fetch a random quote when the activity is created
        fetchQuote(service, quoteTextView, authorTextView)

        // Set up onClickListener for the fetchButton to fetch a new quote when clicked
        fetchButton.setOnClickListener {
            fetchQuote(service, quoteTextView, authorTextView)
        }
    }

    // Function to fetch a random quote using Coroutine
    @SuppressLint("SetTextI18n")
    private fun fetchQuote(
        service: QuoteService,
        quoteTextView: TextView,
        authorTextView: TextView
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val quote = service.getRandomQuote()
                // Update UI with the fetched quote
                quoteTextView.text = "\"${quote.content}\""
                authorTextView.text = "- ${quote.author}"
            } catch (e: Exception) {
                // Handle error
                quoteTextView.text = "Failed to fetch quote"
                authorTextView.text = ""
            }
        }
    }
}
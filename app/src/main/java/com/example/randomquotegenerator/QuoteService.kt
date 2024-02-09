package com.example.randomquotegenerator

import retrofit2.http.GET

interface QuoteService {
        @GET("random")
        suspend fun getRandomQuote(): Quote
    }
package com.example.myquizapp.api

import com.example.myquizapp.Constants.Companion.API_KEY
import com.example.myquizapp.models.Quizs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {

    @GET("api.php")
    suspend fun selectCategory(
        @Query("amount")
        amount: Int = 50,

        @Query("category")
        category: Int = 9,

        @Query("difficulty")
        difficulty: String = "easy",

        @Query("type")
        type: String = "multiple",

        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<Quizs>
}
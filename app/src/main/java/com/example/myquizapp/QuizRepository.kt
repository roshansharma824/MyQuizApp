package com.example.myquizapp

import com.example.myquizapp.api.RetrofitInstance

class QuizRepository {

    suspend fun selectCategory() =
        RetrofitInstance.api.selectCategory()


}
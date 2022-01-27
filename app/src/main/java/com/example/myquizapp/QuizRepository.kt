package com.example.myquizapp

import com.example.myquizapp.api.RetrofitInstance

class QuizRepository {

    suspend fun selectCategory(amount:Int,category:Int,difficulty:String) =
        RetrofitInstance.api.selectCategory(amount,category,difficulty)


}

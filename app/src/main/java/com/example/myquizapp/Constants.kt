package com.example.myquizapp

import android.content.Context
import android.content.Intent

class Constants {
    companion object {
        const val API_KEY = "20220124112948"
        const val BASE_URL = "https://opentdb.com/"
        const val SEARCH_NEWS_TIME_DELAY = 500L
        const val QUERY_PAGE_SIZE = 20
    }
}

//fun Context.restart(){
//    val intent = Intent(this, MainActivity::class.java).apply {
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//    }
//    startActivity(intent)
//}
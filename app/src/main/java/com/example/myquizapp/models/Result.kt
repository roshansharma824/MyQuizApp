package com.example.myquizapp.models

data class Result(
    val category: String,
    val correct_answer: String,
    val difficulty: String,
    val incorrect_answers: ArrayList<String>,
    val question: String,
    val type: String
)
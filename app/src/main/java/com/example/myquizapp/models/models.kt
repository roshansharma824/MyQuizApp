package com.example.myquizapp.models

class StatFeed(val name: String, val image: Int)

//class AllResults(
//    val response_code: Int,
//    val results: List<ResultFeed>
//)

class QuizResults(
    val results: String
)

//class ResultFeed(
//    val category: String,
//    val type: String,
//    val difficulty: String,
//    val question: String,
//    val correct_answer: String,
//    val incorrect_answers: ArrayList<String>
//)

class JoinedFeed(
    val questions: ArrayList<String>,
    val answers: ArrayList<ArrayList<String>>,
    val correct_answer: ArrayList<String>
)

class DoneFeed(
    val qNumbers: String,
    val qCorrectAnswer: String,
    val qAttemppted: String,
    val qNegetive: String
)


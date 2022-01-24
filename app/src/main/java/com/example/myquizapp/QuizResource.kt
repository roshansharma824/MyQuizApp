package com.example.myquizapp



sealed class QuizResource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : QuizResource<T>(data)
    class Error<T>(message: String, data: T? = null) : QuizResource<T>(data, message)
    class Loading<T> : QuizResource<T>()
}
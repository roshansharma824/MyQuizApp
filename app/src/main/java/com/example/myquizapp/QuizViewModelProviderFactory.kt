package com.example.myquizapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuizViewModelProviderFactory(
    val quizRepository: QuizRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizViewModel(quizRepository) as T
    }
}
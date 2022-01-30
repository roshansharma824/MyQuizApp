package com.example.myquizapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import com.example.myquizapp.models.Quizs
import kotlinx.coroutines.launch
import retrofit2.Response

class QuizViewModel(
    private val quizRepository: QuizRepository
): ViewModel() {

    val TAG = "Main_TAG"
    val quizs: MutableLiveData<QuizResource<Quizs>> = MutableLiveData()
    var amount = 10


    init {
        getQuiz(9,"easy")
    }

    fun getQuiz(category: Int, difficulty:String) = viewModelScope.launch {
        val quizResponse = quizRepository.selectCategory(amount, category, difficulty)
        quizs.postValue(handleGetQuiz(quizResponse))

    }

    private fun handleGetQuiz(response: Response<Quizs>) : QuizResource<Quizs> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return QuizResource.Success(resultResponse)
            }
        }
        return QuizResource.Error(response.message())
    }


}
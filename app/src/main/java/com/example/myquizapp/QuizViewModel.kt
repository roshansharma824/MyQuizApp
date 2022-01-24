package com.example.myquizapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource

import com.example.myquizapp.models.Quizs
import kotlinx.coroutines.launch
import retrofit2.Response

class QuizViewModel(
    val quizRepository: QuizRepository
): ViewModel() {

    val TAG = "Main_TAG"
    val quizs: MutableLiveData<QuizResource<Quizs>> = MutableLiveData()


    init {
        getQuiz()
    }

    fun getQuiz() = viewModelScope.launch {
        val quizResponse = quizRepository.selectCategory()
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



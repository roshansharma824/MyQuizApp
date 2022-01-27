package com.example.myquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myquizapp.models.Result

class QuizActivity : AppCompatActivity() {

    lateinit var viewModel: QuizViewModel
    lateinit var quizs:List<Result>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)


        val category = intent.getStringExtra("v1")
        val key = category!!.toInt()
        val difficulty = intent.getStringExtra("v2")
        val questionsList: MutableList<Result> = mutableListOf()
        var count:Int = 0
        var i:Int = 0

        val quizRepository = QuizRepository()
        val viewModelProviderFactory = QuizViewModelProviderFactory(quizRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[QuizViewModel::class.java]

        if (difficulty != null) {
            viewModel.getQuiz(key,difficulty)
        }


        viewModel.quizs.observe(this, Observer { response ->
            when (response) {
                is QuizResource.Success -> {
                    response.data?.let { quizResponse ->
                        quizs = quizResponse.results


                    }
                }

            }
        })



        println(questionsList)
        println(questionsList.size)





    }
}


package com.example.myquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myquizapp.models.Result
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)


        val category = intent.getStringExtra("v1")
        val difficulty = intent.getStringExtra("v2")
        val questionsList: MutableList<Result> = mutableListOf()
        var count:Int = 0

        val quizRepository = QuizRepository()
        val viewModelProviderFactory = QuizViewModelProviderFactory(quizRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(QuizViewModel::class.java)



        viewModel.quizs.observe(this, Observer { response ->
            when (response) {
                is QuizResource.Success -> {
                    response.data?.let { quizResponse ->
                        val quiz = quizResponse.results
                        for (i in quiz.indices){
                            if (quiz[i].category == category && quiz[i].difficulty == difficulty){
                                if(count <10) {
                                    questionsList.add(count, quiz[i])
                                    count++
                                }
                                else{
                                    break
                                }
                            }
                        }
                    }
                }
            }
        })

        var i:Int = 0

//        nextBtn.setOnClickListener {
//            question.text = questionsList[i].question
//            i++
//        }



    }
}
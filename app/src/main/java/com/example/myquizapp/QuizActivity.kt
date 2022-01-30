package com.example.myquizapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myquizapp.models.DoneFeed
import com.example.myquizapp.models.JoinedFeed
import com.example.myquizapp.models.Result
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlin.math.abs

class QuizActivity : AppCompatActivity() {


    lateinit var viewModel: QuizViewModel
    companion object{
        var questionsList: ArrayList<Result> = ArrayList()
        var allJoined: ArrayList<JoinedFeed> = ArrayList()
        var selectedAnswers: ArrayList<String> = ArrayList()
        var questionNr: Int = 0
        var isCorrect: Int = 0
        var isFailed: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_quiz)

        done.visibility = View.GONE



        val category = intent.getStringExtra("v1")
        val key = category!!.toInt()
        val difficulty = intent.getStringExtra("v2")

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
                        questionsList.addAll(quizResponse.results)
                        setQuizList()
                    }
                }

            }
        })











    }

    private fun setQuizList() {
        val questions: ArrayList<String> = ArrayList()
        val allanswers: ArrayList<ArrayList<String>> = ArrayList()
        val allcorrectanswers: ArrayList<String> = ArrayList()

        for ((i,v) in questionsList.withIndex()){

            val question = questionsList[i].question
            questions.add(question)
            val c = v.correct_answer

            val answers = questionsList[i].incorrect_answers
            val randomIndex: Int = (0..3).random()
            answers.add(randomIndex, c)
            allanswers.add(answers)

            allcorrectanswers.add(questionsList[i].correct_answer)

            allJoined.add(
                JoinedFeed(
                    questions = questions,
                    answers = allanswers,
                    correct_answer = allcorrectanswers
                )
            )

            startQuiz()
        }

    }

    private fun startQuiz() {
        // Display Number
        var questionNum = questionNr

        // get current Question
        val currentQuestion = allJoined[0].questions[questionNr]

        // Increase the Display Number to +1
        questionNum++
        total_num.text = "$questionNum/${allJoined[0].questions.count()}"

        // set the first Question
        main_question.text = currentQuestion

        // set the first Question Answers
        var qanswers: ArrayList<String> = allJoined[0].answers[questionNr]
        setAnswers(qanswers)

        answers_container.setOnItemClickListener { parent, view, position, id ->
            val clickedID = id.toInt()
            val correctanswer = allJoined[0].correct_answer[questionNr]
            val selectedanswer = allJoined[0].answers[questionNr][clickedID]
            val answerIsCorrect = selectedanswer == correctanswer

            // Check if answer is correct
            if (answerIsCorrect) {
                isCorrect++
            } else {
                isFailed--
            }

            if (questionNr == allJoined[0].questions.count() - 1 && questionNum === 10) {
                quiz.visibility = View.GONE
                done.visibility = View.VISIBLE

                val info: DoneFeed = DoneFeed(
                    qNumbers = "${allJoined[0].questions.count()}",
                    qCorrectAnswer = "${isCorrect}",
                    qNegetive = "${abs(isFailed)}",
                    qAttemppted = "${10}"
                )
                done_pop.adapter = DoneAdapter(this, info)
//                restart.setOnClickListener {
//                    startActivity(Intent(this@QuizActivity,MainActivity::class.java))
//             questionsList = ArrayList()
//            allJoined = ArrayList()
//             selectedAnswers = ArrayList()
//             questionNr = 0
//             isCorrect = 0
//             isFailed = 0
//        }
            } else {
                questionNum++
                questionNr++
            }

            total_num.text = "$questionNum/${allJoined[0].questions.count()}"
            main_question.text = allJoined[0].questions[questionNr]

            //update answers
            val newAnswers = allJoined[0].answers[questionNr]
            setAnswers(newAnswers)
        }






    }

    private fun setAnswers(qanswers: ArrayList<String>) {
        answers_container.adapter = AnswerAdapter(this,qanswers)
    }
}


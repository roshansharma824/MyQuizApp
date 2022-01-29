package com.example.myquizapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.myquizapp.models.DoneFeed
import com.example.myquizapp.models.JoinedFeed
import com.example.myquizapp.models.Result
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlin.math.abs

class QuizActivity : AppCompatActivity() {

    lateinit var viewModel: QuizViewModel
    lateinit var quizs:List<Result>


    val TAG = "QUIZACTIVITYLAYOUT"

    companion object {
        var questionsList: MutableList<Result> = mutableListOf()
        var allJoined: MutableList<JoinedFeed> = mutableListOf()
        var selectedAnswers: MutableList<String> = mutableListOf()
        var questionNr: Int = 0
        var isCorrect: Int = 0
        var isFailed: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        done.visibility = View.GONE


        val category = intent.getStringExtra("v1")
        val key = category!!.toInt()
        val difficulty = intent.getStringExtra("v2")




        val quizRepository = QuizRepository()
        val viewModelProviderFactory = QuizViewModelProviderFactory(quizRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[QuizViewModel::class.java]

        if (difficulty != null) {
            viewModel.getQuiz(key,difficulty)
        }


        viewModel.quizs.observe(this) { response ->
            when (response) {
                is QuizResource.Success -> {
                    response.data?.let { quizResponse ->
                        questionsList.addAll(quizResponse.results)
                        setQuizList()

                    }
                }

            }
        }







    }

    private fun setQuizList() {
        val questions: MutableList<String> = mutableListOf()
        val allanswers: MutableList<MutableList<String>> = mutableListOf()
        val allcorrectanswers: MutableList<String> = mutableListOf()


        for (i in 0 until questionsList.size){
            val question = questionsList[i].question
            questions.add(question)

            val answers = questionsList[i].incorrect_answers
            answers.add((0..3).random(),questionsList[i].correct_answer)
            allanswers.add(answers)

            val canswers = questionsList[i].correct_answer
            allcorrectanswers.add(canswers)

        }
//        Log.d(TAG,questions.toString())

        allJoined.add(
            JoinedFeed(
                questions = questions,
                answers = allanswers,
                correct_answer = allcorrectanswers
            )
        )

        startQuiz()

    }


    @SuppressLint("SetTextI18n")
    private fun startQuiz() {
        val nextbtn = findViewById<ImageButton>(R.id.next_btn)
        val totalnum: TextView = findViewById<TextView>(R.id.total_num)
        val mainquestion: TextView = findViewById<TextView>(R.id.main_question)
        val donelayout: ConstraintLayout = findViewById(R.id.done)
        val quizlayout: ConstraintLayout = findViewById(R.id.quiz)
        val donepop: ListView = findViewById(R.id.done_pop)

        // Display Number
        var questionNum = questionNr

        // Get current Question
        val currentQuestion = allJoined[0].questions[questionNr]

        // Grab the listview
        val answerListView: ListView = findViewById(R.id.answers_container)

        // Increase the Display number to +1
        questionNum++
        totalnum.text = "$questionNum/${allJoined[0].questions.count()}"

        // Set the first Question
        mainquestion.text = currentQuestion

        // Set the first Question Answers
        //size = 4
        var qanswers: MutableList<String> = allJoined[0].answers[questionNr]
        setAnswers(qanswers)

        answerListView.setOnItemClickListener { parent, view, position, id ->
            val clickedID = id.toInt()
            val correctanswer = allJoined[0].correct_answer[questionNr]
            val selectedanswer = allJoined[0].answers[questionNr][clickedID]
            val answerIsCorrect = selectedanswer == correctanswer
            selectedAnswers.add(selectedanswer)
            // Check if answer is correct
            if (answerIsCorrect) {
                isCorrect++
            } else {
                isFailed--
            }

            if (questionNr == allJoined[0].questions.count() - 1 && questionNum === 10) {
                quizlayout.visibility = View.GONE
                donelayout.visibility = View.VISIBLE

                val info: DoneFeed = DoneFeed(
                    qNumbers = "${allJoined[0].questions.count()}",
                    qCorrectAnswer = "$isCorrect",
                    qNegetive = "${abs(isFailed)}",
                    qAttemppted = "${10}"
                )
                donepop.adapter = DoneAdapter(this, info)
            } else {
                questionNum++
                questionNr++
            }

            totalnum.text = "$questionNum/${allJoined[0].questions.count()}"
            mainquestion.text = allJoined[0].questions[questionNr]

            //update answers
            //size = 5
            val newAnswers = allJoined[0].answers[questionNr]
            setAnswers(newAnswers)
        }

    }
    private fun setAnswers(qanswers: MutableList<String>) {
        val answers: ListView = findViewById(R.id.answers_container)
        answers.adapter = AnswerAdapter(this, qanswers)
    }
}


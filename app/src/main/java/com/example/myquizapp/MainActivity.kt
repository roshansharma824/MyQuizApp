package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myquizapp.databinding.ActivityMainBinding
import com.example.myquizapp.models.Quizs
import android.widget.ArrayAdapter


import android.widget.Spinner
import android.widget.Toast


class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: QuizViewModel
    var category:String? = null
    var difficulty: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val quizRepository = QuizRepository()
        val viewModelProviderFactory = QuizViewModelProviderFactory(quizRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(QuizViewModel::class.java)

        val result: Result<String>

        viewModel.quizs.observe(this, Observer { response ->
            when (response) {
                is QuizResource.Success -> {
                    response.data?.let { quizResponse ->
                        val quiz = quizResponse.results
                        binding.quiz = quiz[0]
//                        binding.question.text = it.toString()
                    }
                }
            }
        })


        val categoryList = listOf("General Knowledge","Entertainment: Books","Entertainment: Film","Entertainment: Music",
        "Entertainment: Musicals & Theatres","Entertainment: Television",
        "Entertainment: Video Games","Entertainment: Board Games","Science & Nature",
        "Science: Computers","Science: Mathematics",
        "Mythology","Sports","Geography","History","Politics",
        "Art","Celebrities","Animals","Vehicles","Entertainment: Comics",
        "Science: Gadgets","Entertainment: Japanese Anime & Manga",
        "Entertainment: Cartoon & Animations")

        val difficultyLevel = listOf("Easy","Medium","Hard")


        val adapter1 = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,categoryList)
        val adapter2 = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,difficultyLevel)


        binding.categorySelect.adapter = adapter1
        binding.difficultyLevelSelect.adapter = adapter2

        binding.categorySelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView:  AdapterView<*>?, view: View?, pos: Int, id: Long) {
                category = adapterView?.getItemAtPosition(pos).toString()
                Toast.makeText(this@MainActivity,
                "$category",
                Toast.LENGTH_LONG).show()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.difficultyLevelSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                difficulty = adapterView?.getItemAtPosition(pos).toString()
                Toast.makeText(this@MainActivity,
                    "$difficulty",
                    Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        binding.submit.setOnClickListener {

            Intent(this,QuizActivity::class.java).also {
                it.putExtra("v1",category)
                it.putExtra("v2",difficulty)
                startActivity(it)
            }
        }



    }
}
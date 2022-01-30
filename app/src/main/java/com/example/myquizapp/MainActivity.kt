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
//    lateinit var viewModel: QuizViewModel
    var category:String? = null
    var difficulty: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        val quizRepository = QuizRepository()
//        val viewModelProviderFactory = QuizViewModelProviderFactory(quizRepository)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(QuizViewModel::class.java)

        var key:Int? = null

//        viewModel.quizs.observe(this, Observer { response ->
//            when (response) {
//                is QuizResource.Success -> {
//                    response.data?.let { quizResponse ->
//                        val quiz = quizResponse.results
//                        binding.quiz = quiz[0]
////                        binding.question.text = it.toString()
//                    }
//                }
//            }
//        })


        val categoryList = listOf("General Knowledge","Entertainment: Books","Entertainment: Film","Entertainment: Music",
            "Entertainment: Musicals & Theatres","Entertainment: Television",
            "Entertainment: Video Games","Entertainment: Board Games","Science & Nature",
            "Science: Computers","Science: Mathematics",
            "Mythology","Sports","Geography","History","Politics",
            "Art","Celebrities","Animals","Vehicles","Entertainment: Comics",
            "Science: Gadgets","Entertainment: Japanese Anime & Manga",
            "Entertainment: Cartoon & Animations")

        val difficultyLevel = listOf("easy","medium","hard")


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
            val keyCategory = mapOf(9 to "General Knowledge",10 to "Entertainment: Books",11 to "Entertainment: Film",12 to "Entertainment: Music",
                13 to "Entertainment: Musicals & Theatres",14 to "Entertainment: Television",
                15 to "Entertainment: Video Games",16 to "Entertainment: Board Games", 17 to "Science & Nature",
                18 to "Science: Computers",19 to "Science: Mathematics",
                20 to "Mythology",21 to "Sports", 22 to "Geography",23 to "History",24 to "Politics",
                25 to "Art",26 to "Celebrities",27 to "Animals",28 to "Vehicles",29 to "Entertainment: Comics",
                30 to "Science: Gadgets",31 to "Entertainment: Japanese Anime & Manga",
                32 to "Entertainment: Cartoon & Animations")

            keyCategory.forEach { (k, v) ->
                if (category == v){
                    key = k

                }
            }

            Intent(this,QuizActivity::class.java).also {
                it.putExtra("v1",key.toString())
                it.putExtra("v2",difficulty)
                startActivity(it)
            }
        }



    }
}
package com.example.quizapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.quizapp.Adapter.QuestionAdapter
import com.example.quizapp.Domain.QuestionModel
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity(), QuestionAdapter.score{
    private lateinit var binding: ActivityQuestionBinding
    var position: Int = 0
    var receivedList: MutableList<QuestionModel> = mutableListOf()
    var allScore =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        receivedList = intent.getParcelableArrayListExtra<QuestionModel>("list")!!.toMutableList()

        binding.apply {
            btnBack.setOnClickListener { finish() }

            progressBar.progress = 1

            questionTxt.text = receivedList[position].question

            val drawableResourceId: Int = binding.root.resources.getIdentifier(
                receivedList[position].picPath,
                "drawable",binding.root.context.packageName
            )

            Glide.with(this@QuestionActivity)
                .load(drawableResourceId)
                .centerCrop()
                .apply (RequestOptions.bitmapTransform(RoundedCorners(60)))
                .into(pic)

            loadAnswer()
            rightArrow.setOnClickListener {
                if (progressBar.progress == 10){
                    val intent = Intent(this@QuestionActivity, ScoreActivity::class.java)
                    intent.putExtra("Score",allScore)
                    startActivity(intent)
                    finish()
                    return@setOnClickListener
                }

                position++
                progressBar.progress = progressBar.progress + 1
                questionNumbertxt.text = "Question " + progressBar.progress + "/10"
                questionTxt.text = receivedList[position].question

                val drawableResourceId:Int = binding.root.resources.getIdentifier(
                    receivedList[position].picPath,
                    "drawable",binding.root.context.packageName
                )

                Glide.with(this@QuestionActivity)
                    .load(drawableResourceId)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                    .into(pic)

                loadAnswer()
            }

            leftArrow.setOnClickListener {
                if (progressBar.progress == 1){
                    val intent = Intent(this@QuestionActivity, ScoreActivity::class.java)
                    intent.putExtra("Score",allScore)
                    startActivity(intent)
                    finish()
                    return@setOnClickListener
                }

                position--
                progressBar.progress = progressBar.progress - 1
                questionNumbertxt.text = "Question " + progressBar.progress + "/10"
                questionTxt.text = receivedList[position].question

                val drawableResourceId:Int = binding.root.resources.getIdentifier(
                    receivedList[position].picPath,
                    "drawable",binding.root.context.packageName
                )

                Glide.with(this@QuestionActivity)
                    .load(drawableResourceId)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                    .into(pic)

                loadAnswer()
            }
        }
    }

    private fun loadAnswer(){
        val users: MutableList<String> = mutableListOf()
        users.add(receivedList[position].answer_1.toString())
        users.add(receivedList[position].answer_2.toString())
        users.add(receivedList[position].answer_3.toString())
        users.add(receivedList[position].answer_4.toString())

        if (receivedList[position].clickedAnswer != null ) users.add(receivedList[position].clickedAnswer.toString())

        val questionAdapter by lazy {
            QuestionAdapter(
                receivedList[position].correct_answer.toString(), users,this
            )
        }

        questionAdapter.differ.submitList(users)
        binding.questionList.apply {
            layoutManager = LinearLayoutManager(this@QuestionActivity)
            adapter = questionAdapter
        }
    }


    override fun amount(number: Int, clickedAnswer: String) {
        allScore += number
        receivedList[position].clickedAnswer = clickedAnswer
    }
}
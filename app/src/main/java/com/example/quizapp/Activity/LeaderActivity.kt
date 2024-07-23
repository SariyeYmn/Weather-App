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
import com.example.quizapp.Adapter.LeaderAdapter
import com.example.quizapp.Domain.UserModel
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityLeaderBinding

class LeaderActivity : AppCompatActivity() {
    lateinit var binding: ActivityLeaderBinding

    private val leaderAdapter by lazy { LeaderAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            scoreText1.text = loadData().get(0).score.toString()
            scoreText2.text = loadData().get(1).score.toString()
            scoreText3.text = loadData().get(2).score.toString()
            title1.text = loadData().get(0).name
            title2.text = loadData().get(1).name
            title3.text = loadData().get(2).name

            val drawableResourcesId1: Int = binding.root.resources.getIdentifier(
                loadData().get(0).pic,"drawable", binding.root.context.packageName
            )

            Glide.with(root.context)
                .load(drawableResourcesId1)
                .into(pic1)

            val drawableResourcesId2: Int = binding.root.resources.getIdentifier(
                loadData().get(1).pic,"drawable", binding.root.context.packageName
            )

            Glide.with(root.context)
                .load(drawableResourcesId2)
                .into(pic2)

            val drawableResourcesId3: Int = binding.root.resources.getIdentifier(
                loadData().get(2).pic,"drawable", binding.root.context.packageName
            )

            Glide.with(root.context)
                .load(drawableResourcesId3)
                .into(pic3)

            bottomMenu.setItemSelected(R.id.board)
            bottomMenu.setOnItemSelectedListener {
                if (it == R.id.home){
                    val intent = Intent(this@LeaderActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            val list:MutableList<UserModel> = loadData()
            list.removeAt(0)
            list.removeAt(1)
            list.removeAt(2)
            leaderAdapter.differ.submitList(list)

            leaderView.apply {
                layoutManager = LinearLayoutManager(this@LeaderActivity)
                adapter = leaderAdapter
            }
        }
    }

    private fun loadData():MutableList<UserModel>{
        val users: MutableList<UserModel> = mutableListOf()
        users.add(UserModel(1,"Sophia","person1",4850))
        users.add(UserModel(2,"lara","person2",4560))
        users.add(UserModel(3,"Alex","person3",3873))
        users.add(UserModel(4,"Deneme","person4",3250))
        users.add(UserModel(5,"Denem","person5",3015))
        users.add(UserModel(6,"Sophia","person6",2970))
        users.add(UserModel(7,"Sophia","person7",2860))
        users.add(UserModel(8,"Sophia","person8",2380))
        users.add(UserModel(9,"Sophia","person9",2388))
        users.add(UserModel(10,"Sophia","person9",2360))
        return users
    }
}
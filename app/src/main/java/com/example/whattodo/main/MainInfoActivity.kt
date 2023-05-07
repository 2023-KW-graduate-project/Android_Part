package com.example.whattodo.main

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.example.whattodo.*
import com.example.whattodo.databinding.ActivityMainInfoBinding
import com.example.whattodo.db.UserDatabase
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.reflect.Array.getInt

private const val TAG="MainInfoActivity"
class MainInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainInfoBinding
    var fatigue=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        val barList= listOf<String>(getString(R.string.app_name),getString(R.string.make_course),getString(R.string.mypage))
        val textList= listOf<String>(getString(R.string.home),getString(R.string.make_course),getString(R.string.mypage))
        val iconList=listOf(R.drawable.home,R.drawable.course,R.drawable.mypage)
        val myAdapter=MyMainAdapter(this)

        myAdapter.addFragment(CourseFragment())
        myAdapter.addFragment(PlaceFragment())
        myAdapter.addFragment(MypageFragment())
        binding.viewpager.adapter=myAdapter

        binding.viewpager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                supportActionBar?.setTitle(barList[position])
            }
        })

        TabLayoutMediator(binding.tab,binding.viewpager) { tab,position->
            tab.text=textList[position]
            tab.setIcon(iconList[position])
        }.attach()
        val editor=getSharedPreferences(USER_INFO,Context.MODE_PRIVATE)
        fatigue=editor.getInt(FATIGUE,0)
        Log.d(TAG,"$fatigue")
    }
}



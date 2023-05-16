package com.example.whattodo.FirstFeature

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.whattodo.R
import com.example.whattodo.databinding.FragmentPlaceBinding
import com.example.whattodo.datas.Category


class PlaceFragment : Fragment(), PlaceFragmentAdapter.ItemClickListener {
    private lateinit var binding : FragmentPlaceBinding
    private lateinit var categoryAdapter:PlaceFragmentAdapter
//    private var selectedCategory:Category?=null
    private val categoryList= mutableListOf<Category>()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPlaceBinding.inflate(layoutInflater)

        /* 뒤로가기 눌렀을 때 앱 종료할 것인지 물어보는 다이얼로그 띄우는 부분 */
        val onBackPressedCallback=object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder=AlertDialog.Builder(requireActivity())
                builder.setMessage("정말로 종료하시겠습니까?")
                builder.setPositiveButton("Ok") {_,_->
                    requireActivity().finish()
                }
                builder.setNegativeButton("Cancel") {dialog,_ ->
                    dialog.dismiss()
                }
                builder.show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,onBackPressedCallback
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryList()
        initRecyclerView()
    }



    private fun initRecyclerView() {
        categoryAdapter= PlaceFragmentAdapter(categoryList,this)
        binding.categoryRecyclerView.apply {
            adapter=categoryAdapter
            categoryAdapter.notifyDataSetChanged()
            layoutManager=GridLayoutManager(context,3)
        }
    }

    override fun onClick(category: Category) {
        Toast.makeText(context,"${category.title}가 클릭되었습니다",Toast.LENGTH_SHORT).show()
        val intent= Intent(activity,ShowMapActivity::class.java)
        intent.putExtra("selected",category.title)
        startActivity(intent)
    }

    private fun initCategoryList() {
        categoryList.add(0, Category(R.drawable.sports,"스포츠"))
        categoryList.add(1,Category(R.drawable.nature,"자연"))
        categoryList.add(2,Category(R.drawable.activity,"액티비티"))
        categoryList.add(3,Category(R.drawable.alone,"1인가능"))
        categoryList.add(4,Category(R.drawable.many,"여러명"))
        categoryList.add(5,Category(R.drawable.cook,"요리&베이킹"))
        categoryList.add(6,Category(R.drawable.flower,"식물"))
        categoryList.add(7,Category(R.drawable.makeup,"메이크업"))
        categoryList.add(8,Category(R.drawable.dado,"다도"))
        categoryList.add(9,Category(R.drawable.art,"미술"))
        categoryList.add(10,Category(R.drawable.handmade,"핸드메이드"))
        categoryList.add(11,Category(R.drawable.show,"관람"))
        categoryList.add(12,Category(R.drawable.besttrip,"관광명소"))
        categoryList.add(13,Category(R.drawable.themaroad,"테마거리"))
        categoryList.add(14,Category(R.drawable.scene,"풍경"))
        categoryList.add(15,Category(R.drawable.relax,"휴식"))
        categoryList.add(16,Category(R.drawable.shopping,"쇼핑"))
        categoryList.add(17,Category(R.drawable.cafe,"테마카페"))
        categoryList.add(18,Category(R.drawable.alchol,"음주"))
        categoryList.add(19,Category(R.drawable.gallery,"전시시설"))
        categoryList.add(20,Category(R.drawable.concert,"공연시설"))
    }

    /* onResume일때 backbutton 처리 */
    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher
    }


}
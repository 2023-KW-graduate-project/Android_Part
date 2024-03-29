package com.example.whattodo.FindingIdPass

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.whattodo.R
import com.example.whattodo.databinding.FragmentFindIdBinding
import com.example.whattodo.datas.User
import com.example.whattodo.network.RetrofitAPI
import retrofit2.Call
import retrofit2.Response


/* 아이디찾기 프래그먼트 */
private const val TAG = "FindIdFragment"

class FindIdFragment : Fragment() {

    private lateinit var binding: FragmentFindIdBinding

    private var nameFlag = false
    private var emailFlag = false
    private var birthFlag = false
    private var genderFlag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindIdBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* 이름 영역 입력여부 확인 */
        binding.nameArea.addTextChangedListener { text ->
            if (text != null) {
                nameFlag = when {
                    text.isEmpty() -> false
                    else -> true
                }
            }
        }
        /* 이메일 영역 입력여부 확인*/
        binding.emailArea.addTextChangedListener { text ->
            if (text != null) {
                emailFlag = when {
                    text.isEmpty() -> {
                        false
                    }
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches() -> {
                        false
                    }
                    else -> {
                        true
                    }
                }
            }
        }
        /* 생일 입력 여부 확인 */
        binding.birthArea.addTextChangedListener { text ->
            if (text != null) {
                birthFlag = when {
                    text.isEmpty() -> false
                    else -> true
                }
            }
        }
        /* 성별부분 체크 여부 확인 */
        binding.genderArea.setOnCheckedChangeListener { _, checkedId ->
            genderFlag = when (checkedId) {
                R.id.male -> true
                R.id.female -> true
                else -> false
            }
            checkFlag()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.findButton.setOnClickListener {
            val userName: String = binding.nameArea.text.toString()
            val userEmail: String = binding.emailArea.text.toString()
            val userBirth: String = binding.birthArea.text.toString()
            val userGender = when {
                binding.male.isChecked -> binding.male.text.toString()
                binding.female.isChecked -> binding.female.text.toString()
                else -> null
            }

            val userData = User(
                null, null, null ,userName,null,
                userEmail, userBirth, userGender, null, null, null
            )
            val findIdCall = RetrofitAPI.findService.findId(userData)
            findIdCall.enqueue(object : retrofit2.Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val receiveData = response.body()
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("아이디")
                        builder.setMessage("${receiveData?.memberId.toString()} 입니다")
                        builder.setPositiveButton(
                            R.string.ok,
                            DialogInterface.OnClickListener { _, _ ->
                                activity!!.finish()
                            })
                        builder.create()
                        builder.show()
                    } else {
                        Log.d(TAG, "Failure")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("일치하는 정보가 없습니다.")
                    builder.setPositiveButton(R.string.ok, null)
                    builder.create()
                    builder.show()
                }
            })
        }
    }

    private fun checkFlag() {
        binding.findButton.isEnabled = nameFlag && emailFlag && birthFlag && genderFlag
    }
}
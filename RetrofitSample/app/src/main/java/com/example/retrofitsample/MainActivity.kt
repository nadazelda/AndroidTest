package com.example.retrofitsample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitsample.databinding.ActivityMainBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: ListAdapter
    var coinList = listOf<Coin>()
    var memberList = listOf<Member>()
    var logTag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //setContentView(R.layout.activity_main)
        listAdapter = ListAdapter()

        binding.btn01.setOnClickListener {
            listAdapter.setMemberList(memberList)
            listAdapter.notifyDataSetChanged()
        }

        binding.recycler01.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        //회원가입
        binding.joinButton.setOnClickListener {
            registerNewMember()
        }
        //로그인
        binding.loginButton.setOnClickListener {
            loginMember()
        }

        //initList()
    }
    private fun initList() {
        val call = ApiObject.getRetrofitService.getMemberAll()
        call.enqueue(object: Callback<List<Member>> {
            override fun onResponse(call: Call<List<Member>>, response: Response<List<Member>>) {
                Toast.makeText(applicationContext, "Call Success", Toast.LENGTH_SHORT).show()
                if(response.isSuccessful) {
                    memberList = response.body() ?: listOf()
                    listAdapter.setMemberList(memberList)
                }
            }
            override fun onFailure(call: Call<List<Member>>, t: Throwable) {
                Toast.makeText(applicationContext, "Call Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerNewMember() {
        val newMember = Member(
            id = binding.etLoginId.text.toString(),
            name = "test",
            pw = binding.etLoginPw.text.toString()
        )

        Log.d(logTag, newMember.id+"  pw ="+ newMember.pw)
        /*
        * enqueue 메서드는 콜백을 이용해야한다 .
        * onRespons 는 서버로 부터 응답
        * onFailure 는 네트워크 실패
        * */
        val call = ApiObject.getRetrofitService.registerMember(newMember) //인터페이스에 정의된 메서드
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "회원가입성공  Success", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Toast.makeText(applicationContext, "Registration Failed : $errorMessage", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Registration Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
    private fun loginMember() {
        var input_id = binding.etLoginId.text.toString()
        var input_pw = binding.etLoginPw.text.toString()

        val loginRequest = Member(
            id = input_id,
            pw = input_pw,
            name=""
        )
        Log.d(logTag, "id="+loginRequest.id+"  pw ="+ loginRequest.pw)

        val call = ApiObject.getRetrofitService.loginMember(loginRequest)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d(logTag," Login Sucess")
                    Log.d(logTag,response.body().toString())

                    Toast.makeText(applicationContext, "서버 통신 확인 로그인 성공", Toast.LENGTH_SHORT).show()
                } else {

                    when(response.code()){
                        404 ->Toast.makeText(applicationContext, "요청한 리소스를 찾을 수 없습니다(404)", Toast.LENGTH_SHORT).show()
                        401 ->Toast.makeText(applicationContext, "회원이 존재하지 않습니다(401)", Toast.LENGTH_SHORT).show()
                        500 ->Toast.makeText(applicationContext, "서버오류발생(500)", Toast.LENGTH_SHORT).show()
                        else ->Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext, "Login Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    
    
}
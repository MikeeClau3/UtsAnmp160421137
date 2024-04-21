package com.example.utsanmp160421137.viewModel
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.utsanmp160421137.model.Users
import com.google.gson.Gson

class userViewModel(application: Application): AndroidViewModel(application){
    val userLD = MutableLiveData<Users>()
    val registerLD = MutableLiveData<Boolean>()
    val updateLD = MutableLiveData<Boolean>()
    val checkLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun checkLogin(username:String, password:String){
        queue = Volley.newRequestQueue(getApplication())
        //IP Change
        val url = "http://192.168.227.70/novels/login.php"

        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
//                userLD.value = Gson().fromJson(response, Users::class.java)
                try {
                    val userLog = Gson().fromJson(response,Users::class.java)
                    if(userLog == null || userLog.id.isNullOrEmpty()){
                        checkLD.value =false
                    }
                    else{
                        userLD.value = userLog
                        checkLD.value = true
                    }

                }
                catch (e: Exception){
                    checkLD.value = false
                    Log.e("Login Fail", "Error parsing response : $response",e)
                }


                Toast.makeText(getApplication(), "Login Successful", Toast.LENGTH_SHORT).show()
                Log.d("Success", "Response: ${response}")
            }, {
                Toast.makeText(getApplication(), "Login Failed", Toast.LENGTH_SHORT).show()
                Log.d("login error", it.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }
    fun registerUser(username: String,firstname:String,lastname:String,email:String,password: String){
        queue = Volley.newRequestQueue(getApplication())

        val url =  "http://192.168.227.70/novels/register.php"
        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                registerLD.value = true
                Toast.makeText(getApplication(), "Register Successful", Toast.LENGTH_SHORT).show()
                Log.d("Success", "Response: ${response}")
            }, {
                registerLD.value = false
//                Toast.makeText(getApplication(), "Register Failed", Toast.LENGTH_SHORT).show()
                Log.d("Register error", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = username
                params["firstname"] = firstname
                params["lastname"] = lastname
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    fun updateUser(id:String, firstname: String, lastname: String, password: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.227.70/novels/user_update.php"
        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                updateLD.value = true
                Toast.makeText(getApplication(), "Update Successful", Toast.LENGTH_SHORT).show()
                Log.d("Success", "Response: ${response}")
            }, {
                updateLD.value = false
                Toast.makeText(getApplication(), "Update Failed", Toast.LENGTH_SHORT).show()
                Log.d("Update error", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = id
                params["firstname"] = firstname
                params["lastname"] = lastname
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }




}

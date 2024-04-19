package com.example.utsanmp160421137.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.utsanmp160421137.model.Novels
import com.google.gson.Gson


class novelDetailModel(application: Application):AndroidViewModel(application){
    val novelDetailLD = MutableLiveData<Novels>()
    val TAG = "volleyTag"
    private var queue:RequestQueue?= null


    fun novelDetail(id:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/novels/novel_detail.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                novelDetailLD.value = Gson().fromJson(it, Novels::class.java)

                Log.d("Success", it)
            }, {
                Log.d("Error", it.toString())
            }

        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
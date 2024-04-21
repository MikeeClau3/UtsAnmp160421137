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
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class novelViewModel(application: Application):AndroidViewModel(application){
    val novelLD = MutableLiveData<ArrayList<Novels>>()
    val novelLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue?=null

    fun refresh() {
        loadingLD.value = true
        novelLoadErrorLD.value = false

//        novelLD.value = arrayListOf(
//            Novels(1, "Ancika", "Ancika adalah anak SMA", "http://dummyimage.com/75x100.jpg/cc0000/ffffff", "Pidi Baik"),
//            Novels(2, "Dilan", "Dilanda pusing", "http://dummyimage.com/75x100.jpg/cc0000/ffffff", "Harono")
//        )
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.227.70/novels/novel_data.json"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                val sType = object : TypeToken<ArrayList<Novels>>() {}.type
                val result = Gson().fromJson<List<Novels>>(it, sType)
                novelLD.value = result as ArrayList<Novels>?

                Log.d("Success", it)
                loadingLD.value = false
            }, {
                loadingLD.value = false
                novelLoadErrorLD.value = false
                Log.d("load error", it.toString())
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
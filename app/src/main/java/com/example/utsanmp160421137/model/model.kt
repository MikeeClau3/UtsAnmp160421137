package com.example.utsanmp160421137.model

data class Users(
    val id:String?,
    val username:String?,
    val firstname:String?,
    val lastname:String?,
    val email:String?,
    val password:String?


)
data class Novels(
    val id:Int?,
    val title:String?,
    val description:String?,
    val imageUrl:String?,
    val creatorName:String?,
    val paragraph:List<String>?
)

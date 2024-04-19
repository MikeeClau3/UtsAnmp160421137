package com.example.utsanmp160421137.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.android.volley.RequestQueue
import com.example.utsanmp160421137.R
import com.example.utsanmp160421137.databinding.FragmentLoginBinding
import com.example.utsanmp160421137.viewModel.userViewModel


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var  viewModel: userViewModel
    private lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity){
            mainActivity = context
        }
    }
//    val TAG = "volleyTag"
//    private var queue: RequestQueue?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        (requireActivity() as AppCompatActivity).supportActionBar?.title ="Login"
        val createAccountTxt = binding.buttonCreateNewAcc
        createAccountTxt.setOnClickListener{
            findNavController().navigate(R.id.actionRegisterFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(userViewModel::class.java)
        binding.btnSignIn.setOnClickListener{view->
            val username = binding.textInputLayoutUsername.editText?.text.toString()
            val pwd = binding.textInputLayoutPass.editText?.text.toString()
            if(username.isEmpty() || pwd.isEmpty()){
                Toast.makeText(activity, "Username atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.checkLogin(username, pwd)

                val accLog = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
                val accSet = accLog.edit()
                viewModel.userLD.observe(viewLifecycleOwner, Observer {
                    var userLogin = it

                    if (userLogin != null){

                        accSet.putString("id",userLogin.id)
                        accSet.putString("firstname", userLogin.firstname)
                        accSet.putString("lastname", userLogin.lastname)
                        accSet.putString("password", userLogin.password)
                        accSet.apply()
                        val action = LoginFragmentDirections.actionNovelListFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                    else{
                        Toast.makeText(activity, "No user found with that username or password", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }
        binding.buttonCreateNewAcc.setOnClickListener{
            val action = LoginFragmentDirections.actionRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }



}
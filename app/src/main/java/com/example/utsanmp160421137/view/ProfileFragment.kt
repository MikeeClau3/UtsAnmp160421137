package com.example.utsanmp160421137.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.utsanmp160421137.R
import com.example.utsanmp160421137.databinding.FragmentProfileBinding
import com.example.utsanmp160421137.viewModel.userViewModel


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: userViewModel
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity){
            mainActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(userViewModel::class.java)

        val loginUser = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        val id = loginUser.getString("id", null)
        val firstnameOld = loginUser.getString("firstname", null)
        val lastnameOld = loginUser.getString("lastname", null)
        val passwordOld = loginUser.getString("password", null)

        binding.txtInputLayoutFirstName.editText?.setText(firstnameOld)
        binding.txtInputLayoutLastName.editText?.setText(lastnameOld)

        binding.btnUpdate.setOnClickListener{
            Log.d("Cek", it.toString())
//            if(passwordOld == binding.txtInputLayoutPassword.editText?.text.toString()){
//
//                Log.d("Cek", it.toString())
//            }
            viewModel.updateUser(id.toString(), binding.txtInputLayoutFirstName.editText?.text.toString(), binding.txtInputLayoutLastName.editText?.text.toString(), binding.txtInputLayoutPassword.editText?.text.toString())

            viewModel.updateLD.observe(viewLifecycleOwner, Observer {
                if (it == true){
                    binding.txtInputLayoutPassword.editText?.setText("")
                }
            })

        }




    }


}
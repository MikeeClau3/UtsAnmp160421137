package com.example.utsanmp160421137.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.utsanmp160421137.R
import com.example.utsanmp160421137.databinding.FragmentRegisterBinding
import com.example.utsanmp160421137.viewModel.userViewModel


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: userViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreate.setOnClickListener{ view->
            var username =binding.txtInputLayoutUnameRegis.editText?.text.toString()
            var firstname = binding.textInputLayoutFirstnameRegis.editText?.text.toString()
            var lastname = binding.textInputLayoutLastnameRegis.editText?.text.toString()
            var email = binding.textInputLayoutEmailRegis.editText?.text.toString()
            var password = binding.textInputLayoutPwdRegis.editText?.text.toString()
            var rePassword = binding.textInputLayoutRePwdRegis.editText?.text.toString()


            if (password == rePassword){
                viewModel = ViewModelProvider(this).get(userViewModel::class.java)
                viewModel.registerUser(username,firstname, lastname, email, password)

                viewModel.registerLD.observe(viewLifecycleOwner, Observer {
                    if (it == true) {
                        val action = RegisterFragmentDirections.actionLoginFragment()
                        Navigation.findNavController(view).navigate(action)

                    }
                    else{
                        Toast.makeText(activity, "Register Failed", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else{
                Toast.makeText(activity, "Password does not match Re-type Password", Toast.LENGTH_SHORT).show()
            }

        }
    }


}
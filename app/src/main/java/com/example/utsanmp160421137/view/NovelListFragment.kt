package com.example.utsanmp160421137.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utsanmp160421137.R
import com.example.utsanmp160421137.databinding.FragmentNovelDetailBinding
import com.example.utsanmp160421137.databinding.FragmentNovelListBinding
import com.example.utsanmp160421137.databinding.NovelListItemBinding
import com.example.utsanmp160421137.viewModel.novelViewModel


class NovelListFragment : Fragment() {
    private lateinit var viewModel: novelViewModel
    private lateinit var binding: FragmentNovelListBinding
    private val novelListAdapter = NovelListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNovelListBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.novelListFragment)
            }
        })
        viewModel = ViewModelProvider(this).get(novelViewModel::class.java)
        viewModel.refresh()

        binding.recView.layoutManager =LinearLayoutManager(context)
        binding.recView.adapter = novelListAdapter

        binding.refreshLayout.setOnRefreshListener {
            binding.recView.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }

        observeViewModel()

    }
    fun observeViewModel(){
        viewModel.novelLD.observe(viewLifecycleOwner, Observer { novelListAdapter.updateNovelList(it) })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if( it == true){
                binding.recView.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE

            }
            else{
                binding.recView.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
        viewModel.novelLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true){
                binding.txtError.visibility = View.VISIBLE

            }
            else{
                binding.txtError.visibility = View.GONE
            }
        })
    }


}
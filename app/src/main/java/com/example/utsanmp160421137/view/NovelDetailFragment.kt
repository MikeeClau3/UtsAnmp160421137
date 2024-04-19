package com.example.utsanmp160421137.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.utsanmp160421137.R
import com.example.utsanmp160421137.databinding.FragmentNovelDetailBinding
import com.example.utsanmp160421137.viewModel.novelDetailModel
import com.squareup.picasso.Picasso


class NovelDetailFragment : Fragment() {
    private lateinit var binding: FragmentNovelDetailBinding
    private lateinit var viewModel: novelDetailModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNovelDetailBinding.inflate(inflater,container,false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null){
            viewModel =ViewModelProvider(this).get(novelDetailModel::class.java)
            viewModel.novelDetail(NovelDetailFragmentArgs.fromBundle(requireArguments()).id)

        }

        viewModel.novelDetailLD.observe(viewLifecycleOwner, Observer {
            if (it == null){
            }
            else{
                binding.txtTitleRead.setText(it.title)
                binding.txtCreator.setText("@${it.creatorName}")
                val picasso = Picasso.Builder(binding.root.context)
                picasso.listener{picasso, uri, exception-> exception.printStackTrace()}
                picasso.build().load(it.imageUrl).into(binding.imagePhoto)
                binding.progressBar.visibility = View.INVISIBLE
                binding.imagePhoto.visibility = View.VISIBLE

                var sizeDesc = it.paragraph?.size ?:0
                var index = 0
                if (sizeDesc > 0){
                    binding.txtDescription.setText(it.paragraph?.get(index))
                    binding.btnPrevious.isEnabled = false

                    binding.btnNext.setOnClickListener{ view ->
                        index += 1
                        binding.txtDescription.setText(it.paragraph?.get(index))
                        if((index+1) == sizeDesc){
                            binding.btnNext.isEnabled = false

                        }
                        binding.btnPrevious.isEnabled = true
                    }
                    binding.btnPrevious.setOnClickListener { view->
                        index -= 1
                        binding.txtDescription.setText(it.paragraph?.get(index))
                        if(index == 0){
                            binding.btnPrevious.isEnabled = false
                        }
                        binding.btnNext.isEnabled = true


                    }
                }
                else{
                    binding.txtDescription.setText(" Tidak ada isi")
                }
            }
        })
    }


}
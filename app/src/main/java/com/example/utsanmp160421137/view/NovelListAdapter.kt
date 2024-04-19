package com.example.utsanmp160421137.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.utsanmp160421137.databinding.FragmentNovelListBinding
import com.example.utsanmp160421137.databinding.NovelListItemBinding
import com.example.utsanmp160421137.model.Novels
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class NovelListAdapter(val novelList: ArrayList<Novels>)
    :RecyclerView.Adapter<NovelListAdapter.NovelViewHolder>()
{
    class NovelViewHolder(var binding: NovelListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val binding = NovelListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return NovelViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return novelList.size
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        holder.binding.txtTitleRead.text = novelList[position].title
        holder.binding.txtCreator.text = novelList[position].creatorName
        holder.binding.txtDescription.text = novelList[position].description

        holder.binding.btnRead.setOnClickListener{
            val action = NovelListFragmentDirections.actionNovelDetailFragment(novelList[position].id.toString())
            Navigation.findNavController(it).navigate(action)
        }
        val picasso = Picasso.Builder(holder.itemView.context)
        picasso.listener{picasso,uri, exception ->exception.printStackTrace()}
        picasso.build().load(novelList[position].imageUrl).into(holder.binding.imagePhoto,object :Callback{

            override fun onSuccess() {
                holder.binding.progressBar.visibility = View.INVISIBLE
                holder.binding.imagePhoto.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
                Log.d("Cek", "Error")
            }
        })
    }
    fun updateNovelList(newNovelList:ArrayList<Novels>){
        novelList.clear()
        novelList.addAll(newNovelList)
        notifyDataSetChanged()
    }
}
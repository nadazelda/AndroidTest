package com.example.retrofitsample


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitsample.databinding.ItemListBinding


class ListAdapter: RecyclerView.Adapter<ListAdapter.MyView>() {
    private var MemberList = listOf<Member>()

    inner class MyView(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.userId.text = MemberList[pos].id
            binding.userName.text = MemberList[pos].name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return MemberList.size
    }

    fun setMemberList(list: List<Member>) {
        MemberList = list
    }
}
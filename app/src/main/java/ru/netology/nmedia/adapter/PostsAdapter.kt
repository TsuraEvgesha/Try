package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.counter
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
class PostsAdapter (private val onLikeListener: OnLikeListener,private val onShareListener: OnShareListener): RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
    var list= emptyList<Post>()
    set(value) {
        field= value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  PostViewHolder(binding,onLikeListener, onShareListener)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size

class PostViewHolder (
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
    ): RecyclerView.ViewHolder(binding.root){
    fun bind(post: Post){
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.liked){
                likes.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                likes.setImageResource(R.drawable.ic_outline_favorite_border_24)
            }
            numberOfLikes.text = counter(post.likes)
            numberOfShare.text = counter(post.share)
            likes.setOnClickListener{
                onLikeListener(post)

            }
            share.setOnClickListener {
                onShareListener(post)
                share.setImageResource(R.drawable.ic_baseline_sharetrue_24)
            }
        }

    }
}

}

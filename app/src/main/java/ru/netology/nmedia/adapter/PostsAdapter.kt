package ru.netology.nmedia.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.counter
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

interface OnInteractionListener {
    fun onLike(post: Post){}
    fun onShare(post: Post){}
    fun onEdit(post: Post){}
    fun onRemote(post: Post){}

}
class PostsAdapter (private val listener: OnInteractionListener): ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  PostViewHolder(
            binding,
            listener
        )
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }


class PostViewHolder (
    private val binding: CardPostBinding,
    private val listener: OnInteractionListener
    ): RecyclerView.ViewHolder(binding.root){
    fun bind(post: Post){
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            share.isChecked = post.shareReal
            like.isChecked = post.liked
            numberOfLikes.text = counter(post.likes)
            numberOfShare.text = counter(post.share)
            like.setOnClickListener{
                listener.onLike(post)
            }
            share.setOnClickListener {
                listener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_menu)
                    setOnMenuItemClickListener {item->
                        when(item.itemId){
                            R.id.remove -> {
                                listener.onRemote(post)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                return@setOnMenuItemClickListener  true

                            }else -> false
                        }
                    }
                }.show()
            }
        }

    }
}
    class PostDiffCallback: DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}

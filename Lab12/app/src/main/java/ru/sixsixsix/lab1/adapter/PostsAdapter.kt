package ru.sixsixsix.lab1.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.sixsixsix.lab1.R
import ru.sixsixsix.lab1.model.Post

class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onCommentClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val postText: TextView = itemView.findViewById(R.id.postText)
        private val postImage: ImageView = itemView.findViewById(R.id.postImage)
        private val likeButton: Button = itemView.findViewById(R.id.likeButton)
        private val commentButton: Button = itemView.findViewById(R.id.commentButton)

        @SuppressLint("SetTextI18n")
        fun bind(post: Post) {
            val bindTag = "bindTag"
            postText.text = post.text

            Log.i(bindTag, "loading $post")

            if (post.imageUrl != null) {
                postImage.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(post.imageUrl)
                    .centerCrop()
                    .into(postImage)
            } else {
                postImage.visibility = View.GONE
            }

            likeButton.text = "❤ ${post.likes}"
            commentButton.text = "💬 ${post.comments}"

            likeButton.setOnClickListener {
                onLikeClicked(post)
            }

            commentButton.setOnClickListener {
                onCommentClicked(post)
            }
        }
    }
}

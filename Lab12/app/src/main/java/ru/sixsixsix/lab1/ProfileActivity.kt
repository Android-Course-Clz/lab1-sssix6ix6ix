package ru.sixsixsix.lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.sixsixsix.lab1.adapter.PostsAdapter
import ru.sixsixsix.lab1.model.Post
import ru.sixsixsix.lab1.model.User

class ProfileActivity : AppCompatActivity() {

    private lateinit var adapter: PostsAdapter
    private val user = User(
        "https://main-cdn.sbermegamarket.ru/big1/hlr-system/-25/704/079/052/412/2/600018357022b0.jpg",
        "Иван Иванов",
        "@ivanov",
        followers = 123,
        following = 40,
        posts = 3,
        isFollowing = false
    )

    private val demoPosts = listOf(
        Post(1,"Мой первый пост!", null, 15, 2, false),
        Post(2,"Сегодня отличный день!", "https://ae01.alicdn.com/kf/Hcfdd6e2b77844393b8ad5e54782968f0Z/-.jpg", 120, 45, false),
        Post(3,"И еще один пост", null, 10, 1, false),
        Post(4, "Детка, ты выполнила задание на 5 с плюсом", "https://lastfm.freetls.fastly.net/i/u/ar0/a673ace8df72bd9a382a9c840a818ac0.png", 1337, 777, false),
        Post(5, "Детка, ты выполнила задание на 5 с плюсом", "https://lastfm.freetls.fastly.net/i/u/ar0/a673ace8df72bd9a382a9c840a818ac0.png", 1337, 777, false),
        Post(6, "Детка, ты выполнила задание на 5 с плюсом", "https://lastfm.freetls.fastly.net/i/u/ar0/a673ace8df72bd9a382a9c840a818ac0.png", 1337, 777, false),
        Post(7, "Детка, ты выполнила задание на 5 с плюсом", "https://lastfm.freetls.fastly.net/i/u/ar0/a673ace8df72bd9a382a9c840a818ac0.png", 1337, 777, false),
        Post(8, "Детка, ты выполнила задание на 5 с плюсом", "https://lastfm.freetls.fastly.net/i/u/ar0/a673ace8df72bd9a382a9c840a818ac0.png", 1337, 777, false)
    )

    private val onCreateTag = "onCreateTag"

    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Log.i(onCreateTag, "start glide load")
        Glide.with(this).load(user.avatarUrl).circleCrop().into(findViewById(R.id.avatar))
        Log.i(onCreateTag, "end glide load")
        findViewById<TextView>(R.id.userName).text = user.name
        findViewById<TextView>(R.id.userNickname).text = user.username
        findViewById<TextView>(R.id.followersCount).text = "${user.followers}\nподписчиков"
        findViewById<TextView>(R.id.followingCount).text = "${user.following}\nподписок"
        findViewById<TextView>(R.id.postsCount).text = "${user.posts}\nпостов"

        val followBtn = findViewById<Button>(R.id.followButton)
        followBtn.setOnClickListener {
            user.isFollowing = !user.isFollowing
            (if(user.isFollowing) "Отписаться" else "Подписаться").also { followBtn.text = it }
        }

        findViewById<Button>(R.id.messageButton).setOnClickListener {
            Toast.makeText(this, "Написать сообщение", Toast.LENGTH_SHORT).show()
        }

        adapter = PostsAdapter(
            { post ->
                post.isLiked = !post.isLiked
                post.likes += if (post.isLiked) 1 else -1
                adapter.notifyItemChanged(demoPosts.indexOf(post))
            },
            { post ->
                Toast.makeText(this, "Комментарии (${post.comments})", Toast.LENGTH_SHORT).show()
            }
        )

        findViewById<RecyclerView>(R.id.postsRecycler).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.postsRecycler).adapter = adapter

        adapter.submitList(demoPosts)
    }
}

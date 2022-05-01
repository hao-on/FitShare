package com.example.fitshare.Feeds

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.MainActivity
import com.example.fitshare.Profile.Profile
import com.example.fitshare.R
import com.example.fitshare.fitApp
import com.google.android.material.textfield.TextInputLayout
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_comment.*
import kotlinx.android.synthetic.main.fragment_feeds.*
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.SpannableString
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.fitshare.User.User
import kotlinx.android.synthetic.main.layout_upload_post.*
import org.bson.types.ObjectId


class CommentFragment : Fragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var commentLayout: TextInputLayout
    private lateinit var rvComment: RecyclerView
    private lateinit var adapter: CommentAdapter
    private lateinit var partition: ObjectId
    private lateinit var postRealm: Realm
    private lateinit var commentRealm: Realm
    private lateinit var profileRealm: Realm
    private var profile: Profile ?= null
    private var post: Post ?= null

    private var bottomAppBarVisibility = View.GONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is MainActivity) {
            var  mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomAppBarVisibility)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_comment, container, false)
        user = fitApp.currentUser()
        var postID = arguments?.getString("postID")

        if (postID != null) {
            partition = ObjectId(postID)
        }

        val profile_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "Profile")
                .build()

        Realm.getInstanceAsync(profile_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                this@CommentFragment.profileRealm = realm
                profile = profileRealm.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()
            }
        })

        val post_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "Feeds")
                .build()

        Realm.getInstanceAsync(post_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                this@CommentFragment.postRealm = realm
                post = postRealm.where(Post::class.java).
                equalTo("_id", partition).findFirst()
            }
        })

        val config = SyncConfiguration.Builder(user!!, partition.toString()).build()
        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                this@CommentFragment.commentRealm = realm
                rvComment = view.findViewById(R.id.rvComments)
                rvComment.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
                rvComment.setHasFixedSize(true)
                adapter = CommentAdapter(commentRealm.where<Comment>().sort("date").findAll(), user!!)
//                adapter.setOnClickListener(object: CommentAdapter.onClickListener {
//                    override fun setComment(content: TextView, position: Int) {
//                        var username = profile?.username.toString()
//                        var str = username + " " + adapter.getItem(position)?.comment.toString()
//                        val spannableString = SpannableString(str)
//                        spannableString.setSpan(
//                            object : ClickableSpan() {
//                                override fun onClick(widget: View) {
//                                    Toast.makeText(context, "user profile", Toast.LENGTH_LONG).show()
//                                }
//
//                                override fun updateDrawState(ds: TextPaint) {
//                                    super.updateDrawState(ds)
//                                    ds.color = resources.getColor(R.color.black)
//                                    ds.isUnderlineText = false
//                                    ds.typeface = Typeface.DEFAULT_BOLD
//                                }
//                            }, 0, username.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE
//                        )
//                        content.text = spannableString
//                    }
//                })
                rvComment.adapter = adapter
                val dividerItemDecoration = DividerItemDecoration(requireActivity().applicationContext, RecyclerView.VERTICAL)
                ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
                    ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
                rvComment.addItemDecoration(dividerItemDecoration)
            }
        })

        commentLayout = view.findViewById(R.id.commentLayout)
        commentLayout.setEndIconOnClickListener{
            val comment = Comment(txtComment.text.toString(), profile!!.username, user!!.id)

            commentRealm.executeTransactionAsync { transactionRealm: Realm ->
                transactionRealm.insert(comment) }

//            postRealm.executeTransactionAsync { transactionRealm: Realm ->
//                val postData = transactionRealm.where(Post::class.java).findFirst()
//                postData?.comments?.add(comment)
//                transactionRealm.insertOrUpdate(postData)
//            }

            adapter.notifyDataSetChanged()
            Toast.makeText(activity, "Comment Posted", Toast.LENGTH_SHORT).show()
            txtComment.text?.clear()
        }

        return view
    }


    override fun onStop() {
        super.onStop()
        user.run {
            postRealm.close()
            commentRealm.close()
            profileRealm.close()
        }
    }
}

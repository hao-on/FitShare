package com.example.fitshare.Feeds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.MainActivity
import com.example.fitshare.Profile.Profile
import com.example.fitshare.R
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import com.example.fitshare.Recipe.RecipeDetailsFragment
import com.example.fitshare.User.User
import com.example.fitshare.fitApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_feeds.*
import kotlinx.android.synthetic.main.fragment_recipe.*
import xyz.hanks.library.bang.SmallBangView

class FeedsFragment : Fragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var feedsRealm: Realm
    private lateinit var userRealm: Realm
    private lateinit var partition: String
    private lateinit var adapter: PostAdapter
    private var bottomAppBarVisibility = View.VISIBLE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
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
        val view: View =  inflater.inflate(R.layout.fragment_feeds, container, false)
        user = fitApp.currentUser()
        partition = "Feeds"

        val config = SyncConfiguration.Builder(user!!, partition).build()

        val user_config = SyncConfiguration.Builder(user!!, "user=${user!!.id}").build()

        Realm.getInstanceAsync(user_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                this@FeedsFragment.userRealm = realm
            }
        })

        //Profile Realm sync config
        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm) {
                this@FeedsFragment.feedsRealm = realm
                rvFeeds.layoutManager =
                    LinearLayoutManager(requireActivity().applicationContext)
                rvFeeds.setHasFixedSize(true)
                adapter = PostAdapter(feedsRealm.where<Post>().sort("date").findAll(), user!!, partition)
                adapter.setOnClickListener(object: PostAdapter.onClickListener{
//                    override fun isLikeButtonSelected(button: SmallBangView, position: Int) {
//                        val likeList = adapter.getItem(position)?.likesList
//                        if (likeList != null) {
//                            button.isSelected = likeList.contains(userData)
//                        }
//                    }

                    override fun onLikeButtonClick(button: SmallBangView, position: Int) {
                        val userData = userRealm.where(User::class.java).findFirst()
                        if (button.isSelected) {
                            button.isSelected = false
//                            feedsRealm.executeTransactionAsync { transactionRealm: Realm ->
//                                val postData = transactionRealm.where(Post::class.java)
//                                    .equalTo("_id", adapter.getItem(position)?.id)
//                                    .findFirst()
//                                postData?.likesList?.remove(userData)
//                                transactionRealm.insertOrUpdate(postData)
//                                adapter.notifyDataSetChanged()
//                            }
                        } else {
                            button.isSelected = true
//                            feedsRealm.executeTransactionAsync { transactionRealm: Realm ->
//                                val postData = transactionRealm.where(Post::class.java)
//                                    .equalTo("_id", adapter.getItem(position)?.id)
//                                    .findFirst()
//                                postData?.likesList?.add(userData)
//                                transactionRealm.insertOrUpdate(postData)
//                                adapter.notifyDataSetChanged()
//                            }
                            button.likeAnimation()
                        }
                    }

                    override fun onCommentButtonClick(button: ImageButton, position: Int) {
                        var commentFragment: Fragment = CommentFragment()
                        val bundle = Bundle()
                        bundle.putString("postID", adapter.getItem(position)?.id.toString())
                        commentFragment.arguments = bundle
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, commentFragment, "Comment")
                            .addToBackStack("Comment")
                            .commit()
                    }
                })
                rvFeeds.adapter = adapter
            }
        })

        return view
    }

    override fun onStop() {
        super.onStop()
        user.run {
            feedsRealm.close()
//            userRealm.close()
        }
    }

}
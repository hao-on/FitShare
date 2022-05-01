package com.example.fitshare.MessageForum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.MainActivity
import com.example.fitshare.Profile.Profile
import com.example.fitshare.Profile.ProfileEditButton
import com.example.fitshare.R
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import com.example.fitshare.Recipe.RecipeDetailsFragment
import com.example.fitshare.fitApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Case
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.forum_fragment.*
import kotlinx.android.synthetic.main.fragment_recipe.*

class ForumPostFragment : Fragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var forumRealm: Realm
    private lateinit var partition: String
    private lateinit var addPost: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var forumAdapter: ForumPostAdapter
    private lateinit var searchView: SearchView
    private var removeNavBar = View.GONE

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        if (activity is MainActivity){
            var mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(removeNavBar)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ): View?{
        val view: View = inflater.inflate(R.layout.forum_fragment, container, false)
        user = fitApp.currentUser()
        partition = arguments?.getString("userID").toString()
        recyclerView = view.findViewById(R.id.rvPost)

        val config = SyncConfiguration.Builder(user!!, partition).build()

        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ForumPostFragment.forumRealm = realm

                forumAdapter = ForumPostAdapter(realm.where<ForumPost>().sort("title").findAll(), user!!, partition)
                rvPost.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
                rvPost.adapter = forumAdapter

                //On-click action for posts
                forumAdapter.setOnItemClickListener(object : ForumPostAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        var commentFragment: Fragment = ForumCommentFragment()
                        val bundle = Bundle()

                        bundle.putString("postID", forumAdapter.getItem(position)?.id.toString())
                        bundle.putString("title", forumAdapter.getItem(position)?.title.toString())
                        bundle.putString("content", forumAdapter.getItem(position)?.content.toString())
                        bundle.putString("creator", forumAdapter.getItem(position)?.creator.toString())

                        commentFragment.arguments = bundle
                        requireActivity().supportFragmentManager.beginTransaction().
                        replace(R.id.frameLayout, commentFragment).addToBackStack(null).commit()
                    }

                })
                rvPost.setHasFixedSize(true)
            }
        })

        addPost = view.findViewById(R.id.addPostBtn)
        addPost.setOnClickListener{
            val addForumPost : ForumPostBtnDialog = ForumPostBtnDialog.newInstance()
            addForumPost.show(parentFragmentManager, null)
        }

//        searchView = view.findViewById(R.id.searchView)
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(text: String): Boolean {
//                recyclerSearch(forumRealm, user, partition, text)
//                return false
//            }
//            override fun onQueryTextChange(text: String): Boolean {
//                recyclerSearch(forumRealm, user, partition, text)
//                return false
//            }
//        })

        return view
    }


    private fun recyclerSearch(realm: Realm, user: io.realm.mongodb.User?, partition: String, text: String){
        forumAdapter = ForumPostAdapter(realm.where<ForumPost>().sort("title").findAll(), user!!, partition)
        rvPost.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        rvPost.adapter = forumAdapter
        forumAdapter.setOnItemClickListener(object : ForumPostAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
//                var detailsFragment: Fragment = RecipeDetailsFragment()
//                val bundle = Bundle()
//                bundle.putString("recipeID", forumAdapter.getItem(position)?.id.toString())
//                bundle.putString("recipeName", forumAdapter.getItem(position)?.recipeName)
//                detailsFragment.arguments = bundle
//                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frameLayout,
//                    detailsFragment).commit()
            }

        })
    }


    override fun onDestroy(){
        super.onDestroy()
        forumRealm.close()
    }
}
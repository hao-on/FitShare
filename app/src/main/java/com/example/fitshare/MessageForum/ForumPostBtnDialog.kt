package com.example.fitshare.MessageForum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.fitshare.Profile.Profile
import com.example.fitshare.Profile.ProfileEditButton
import com.example.fitshare.R
import com.example.fitshare.fitApp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration
import org.bson.types.ObjectId
import java.text.SimpleDateFormat
import java.util.*
import javax.annotation.Nullable

class ForumPostBtnDialog : BottomSheetDialogFragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var partition: String
    private lateinit var forumRealm: Realm
    private lateinit var profileRealm: Realm
    private lateinit var title: TextInputEditText
    private lateinit var content: TextInputEditText
    private lateinit var btnCreatePost: Button
    private lateinit var username: String

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState : Bundle?
    ): View?{
        val view: View = inflater.inflate(R.layout.forum_post_dialog, container, false)
        user = fitApp.currentUser()
        partition = "Forum"

        val config = SyncConfiguration.Builder(user!!, partition).build()

        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ForumPostBtnDialog.forumRealm = realm
            }
        })

        partition = "Profile"
        val config_prof = SyncConfiguration.Builder(user!!, partition).build()

        Realm.getInstanceAsync(config_prof, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ForumPostBtnDialog.profileRealm = realm
                val profile = profileRealm.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()
                username = profile!!.username
            }
        })

        title = view.findViewById(R.id.postTitle)
        content = view.findViewById(R.id.postContent)
        btnCreatePost = view.findViewById(R.id.btnCreatePost)

        btnCreatePost.setOnClickListener{
            val sdf = SimpleDateFormat("M/dd/yyyy")
            val date = sdf.format(Date())

            //this is not allowed FIX ME
//            val profile = profileRealm.where(Profile::class.java).
//            equalTo("userid", user?.id.toString()).findFirst()


            forumRealm.executeTransactionAsync{
                val post = ForumPost(ObjectId() ,title.text.toString(), content.text.toString(),
                    username, date.toString())

                it.insert(post)
            }
            dialog?.dismiss()

            var postFragment : Fragment = ForumPostFragment()
            val bundle = Bundle()
            postFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frameLayout, postFragment).commit()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        profileRealm.close()
        forumRealm.close()
    }

    companion object{
        fun newInstance(): ForumPostBtnDialog {
            return ForumPostBtnDialog()
        }
    }
}
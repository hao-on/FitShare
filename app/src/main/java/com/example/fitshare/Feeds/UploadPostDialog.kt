package com.example.fitshare.Feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Nullable
import com.example.fitshare.Profile.Profile
import com.example.fitshare.R
import com.example.fitshare.User.User
import com.example.fitshare.fitApp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.layout_add_recipe.*
import kotlinx.android.synthetic.main.layout_upload_post.*

class UploadPostDialog: BottomSheetDialogFragment() {
    private lateinit var feedsRealm: Realm
    private lateinit var userRealm: Realm
    private lateinit var profileRealm: Realm
    private lateinit var username: String
    private var user: io.realm.mongodb.User? = null
    private lateinit var btnPost: Button
    private lateinit var partition: String

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.layout_upload_post, container, false)
        user = fitApp.currentUser()
        partition = "Feeds"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@UploadPostDialog.feedsRealm = realm
            }
        })

        val user_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()

        Realm.getInstanceAsync(user_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@UploadPostDialog.userRealm = realm
            }
        })

        val profile_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "Profile")
                .build()

        Realm.getInstanceAsync(profile_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@UploadPostDialog.profileRealm = realm
                val profile = profileRealm.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()
                username = profile?.username.toString()
            }
        })

        btnPost = view.findViewById(R.id.btnUploadPost)
        btnPost.setOnClickListener {
            val post = Post(txtPostContent.text.toString(), 0, null, username)

            userRealm.executeTransactionAsync { transactionRealm: Realm ->
                val userData = transactionRealm.where(User::class.java).findFirst()
                userData?.posts?.add(post)
                transactionRealm.insertOrUpdate(userData)
            }

            feedsRealm.executeTransactionAsync { transactionRealm: Realm ->
                transactionRealm.insert(post) }

            dialog?.dismiss()
        }

        return view
    }

    override fun onStop() {
        super.onStop()
        user.run {
            feedsRealm.close()
            userRealm.close()
            profileRealm.close()
        }
    }

    companion object {
        fun newInstance(): UploadPostDialog {
            return UploadPostDialog()
        }
    }
}
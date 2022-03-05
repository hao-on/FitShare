package com.example.fitshare.Profile

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.BottomDialog
import com.example.fitshare.R
import com.example.fitshare.Profile.Profile
import com.example.fitshare.Profile.ProfileAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import com.example.fitshare.User.User
import com.example.fitshare.fitApp
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.edit_profile_button.*

class ProfileEditButton : BottomSheetDialogFragment() {
    private lateinit var profileRealm: Realm
    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var submitButton: Button
    private  lateinit var partition: String

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.edit_profile_button, container, false)

        user = fitApp.currentUser()
        partition = "profile"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ProfileEditButton.profileRealm = realm
            }
        })

        val user_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()
        Realm.getInstanceAsync(user_config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ProfileEditButton.userRealm = realm
            }
        })
        submitButton = view.findViewById(R.id.btnSubmitProfile)

        submitButton.setOnClickListener{
            val profile = Profile(editProfile_first.text.toString(),
                editProfile_last.text.toString(), editProfile_bio.text.toString(),
                editProfile_address.text.toString(), editProfile_zip.text.toString(),
                editProfile_phone.text.toString(), editProfile_username.text.toString())
            profileRealm.executeTransactionAsync{ realm -> realm.insert(profile)}

            userRealm.executeTransactionAsync{transactionRealm: Realm ->
                val userData = transactionRealm.where(User::class.java).findFirst()
                userData?.profile = profile
            }
            dialog?.dismiss()
        }
        return view
    }
    companion object{
        fun newInstance(): ProfileEditButton{
            return ProfileEditButton()
        }
    }
}
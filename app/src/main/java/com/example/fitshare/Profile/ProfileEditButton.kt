package com.example.fitshare.Profile

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
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
import com.google.android.material.textfield.TextInputEditText
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.edit_profile_button.*

class ProfileEditButton : BottomSheetDialogFragment() {
    private lateinit var profileRealm: Realm
    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var submitButton: Button
    private  lateinit var partition: String

    private lateinit var firstName :TextInputEditText
    private lateinit var lastName :TextInputEditText
    private lateinit var username :TextInputEditText
    private lateinit var phone :TextInputEditText
    private lateinit var address :TextInputEditText
    private lateinit var zipcode :TextInputEditText
    private lateinit var bio :TextInputEditText



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

//                firstName = view.findViewById(R.id.editProfile_first)
//                lastName = view.findViewById(R.id.editProfile_last)
//                username = view.findViewById(R.id.editProfile_username)
//                phone = view.findViewById(R.id.editProfile_phone)
//                address = view.findViewById(R.id.editProfile_address)
//                zipcode = view.findViewById(R.id.editProfile_zip)
//                bio = view.findViewById(R.id.editProfile_bio)
//
//                val userData = profileRealm.where<Profile>().findFirst()
//                firstName.setText(userData?.firstName)
//                lastName.setText(userData?.lastName)
//                username.setText(userData?.username)
//                phone.setText(userData?.phoneNumber)
//                address.setText(userData?.address)
//                zipcode.setText(userData?.zipcode)
//                bio.setText(userData?.bio)
            }
        })

        val user_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()
        Realm.getInstanceAsync(user_config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ProfileEditButton.userRealm = realm
//                firstName = view.findViewById(R.id.editProfile_first)
//                lastName = view.findViewById(R.id.editProfile_last)
//                username = view.findViewById(R.id.editProfile_username)
//                phone = view.findViewById(R.id.editProfile_phone)
//                address = view.findViewById(R.id.editProfile_address)
//                zipcode = view.findViewById(R.id.editProfile_zip)
//                bio = view.findViewById(R.id.editProfile_bio)
//
//                val userData = userRealm.where<User>().findFirst()
//                firstName.setText(userData?.profile?.firstName)
//                lastName.setText(userData?.profile?.lastName)
//                username.setText(userData?.profile?.username)
//                phone.setText(userData?.profile?.phoneNumber)
//                address.setText(userData?.profile?.address)
//                zipcode.setText(userData?.profile?.zipcode)
//                bio.setText(userData?.profile?.bio)

            }
        })


        submitButton = view.findViewById(R.id.btnSubmitProfile)

        submitButton.setOnClickListener{
            val profile = Profile(editProfile_first.text.toString(),
                editProfile_last.text.toString(), editProfile_bio.text.toString(),
                editProfile_address.text.toString(), editProfile_zip.text.toString(),
                editProfile_phone.text.toString(), editProfile_username.text.toString())

            userRealm.executeTransactionAsync{transactionRealm: Realm ->
                val userData = transactionRealm.where(User::class.java).findFirst()

                val oldProf = transactionRealm.where(Profile::class.java).
                            equalTo("_id", userData?.profile?.id).findFirst()
                userData?.profile?.deleteFromRealm()

                //Modify with new profile data
                userData?.profile = transactionRealm.copyToRealm(profile)
            }
            profileRealm.executeTransactionAsync{realm ->
                realm.insertOrUpdate(profile)
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
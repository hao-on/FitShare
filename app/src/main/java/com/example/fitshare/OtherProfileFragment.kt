package com.example.fitshare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.fitshare.Profile.Profile
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration

class OtherProfileFragment: Fragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var profileRealm: Realm
    private lateinit var userRealm: Realm
    private lateinit var partition: String
    private lateinit var messaging: AppCompatButton
    private lateinit var meetUp: CheckBox



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

//        user = fitApp.currentUser()
//        partition = "Profile"
//        val config = SyncConfiguration.Builder(user!!, partition).build()
//
//        Realm.getInstanceAsync(config, object: Realm.Callback(){
//            override fun onSuccess(realm: Realm) {
//                this@OtherProfileFragment.profileRealm = realm
//                val oldProf = profileRealm.where(Profile::class.java).
//                equalTo("userid", user?.id.toString()).findFirst()
//                if(oldProf?.meetUp == true){
//                    meetUp.isChecked = true
//                }else{meetUp.isChecked = false}
//            }
//
//        })
//
//        val user_config: SyncConfiguration =
//            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
//                .build()
//        Realm.getInstanceAsync(user_config, object: Realm.Callback(){
//            override fun onSuccess(realm: Realm) {
//                this@OtherProfileFragment.userRealm = realm
//            }
//        })
        return view
    }

    override fun onDestroy() {
        super.onDestroy()

        userRealm.close()
        profileRealm.close()
    }

}
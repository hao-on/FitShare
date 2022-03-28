package com.example.fitshare

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.fitshare.Profile.Profile
import com.example.fitshare.Profile.ProfileEditButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
        val view: View = inflater.inflate(R.layout.fragment_other_profile, container, false)
        user = fitApp.currentUser()
        partition = "Profile"
        val config = SyncConfiguration.Builder(user!!, partition).build()

        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm) {
                this@OtherProfileFragment.profileRealm = realm
                val oldProf = profileRealm.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()
                if(oldProf?.meetUp == true){
                    meetUp.isChecked = true
                }else{meetUp.isChecked = false}
            }

        })

        val user_config: SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()
        Realm.getInstanceAsync(user_config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm) {
                this@OtherProfileFragment.userRealm = realm
            }
        })
        return view
    }

    override fun onDestroy() {
        super.onDestroy()

        userRealm.close()
        profileRealm.close()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
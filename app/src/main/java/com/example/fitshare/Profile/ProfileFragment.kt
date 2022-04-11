package com.example.fitshare.Profile


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.fitshare.R
import com.example.fitshare.Recipe.BottomDialog
import com.example.fitshare.fitApp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var profileRealm: Realm
    private lateinit var userRealm: Realm
    private lateinit var fab: FloatingActionButton
    private lateinit var partition: String
    private lateinit var otherProfileButton: Button
    private lateinit var editProfile: ImageButton
    var def: ColorStateList? = null
    var item1: TextView? = null
    var item2: TextView? = null
    var item3: TextView? = null
    var select: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        onClick(view)

//        user = fitApp.currentUser()
//        partition = "Profile"
//        val config = SyncConfiguration.Builder(user!!, partition).build()
//
//        //Profile Realm sync config
//        Realm.getInstanceAsync(config, object: Realm.Callback(){
//            override fun onSuccess(realm: Realm) {
//                this@ProfileFragment.profileRealm = realm
//                    val oldProf = profileRealm.where(Profile::class.java).
//                    equalTo("userid", user?.id.toString()).findFirst()
//                    if(oldProf?.meetUp == true){
//                        meetUp.isChecked = true
//                    }else{meetUp.isChecked = false}
//                }
//
//        })
//
//        //User Realm sync config
//        val user_config: SyncConfiguration =
//            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
//                .build()
//        Realm.getInstanceAsync(user_config, object: Realm.Callback(){
//            override fun onSuccess(realm: Realm) {
//                this@ProfileFragment.userRealm = realm
//            }
//        })
//

//        //Meet-up status functionality
//        meetUp = view.findViewById(R.id.meetUp)
//        meetUp.setOnClickListener{
//
//            //Check box functionality
//            profileRealm.executeTransactionAsync{
//                val oldProf = it.where(Profile::class.java).
//                equalTo("userid", user?.id.toString()).findFirst()
//                if(meetUp.isChecked()){
//                        oldProf?.meetUp = true
//                    }
//                else if(!meetUp.isChecked()){
//                        oldProf?.meetUp = false
//                    }
//            }
//        }
//
//        //Button for adding/editing a profile
//        fab = view.findViewById(R.id.btnEditProfile)
//        fab.setOnClickListener{
//            val editProfileButton : ProfileEditButton = ProfileEditButton.newInstance()
//            editProfileButton.show(parentFragmentManager, null)
//        }
//
//        //Test Viewing Other Profile Activity **DELETE LATER**
//        otherProfileButton = view.findViewById(R.id.other_profile_button)
//        otherProfileButton.setOnClickListener{
//            //openFragment(OtherProfileFragment)
//        }

        return view
    }


    private fun onClick(view: View) {
        item1 = view.findViewById(R.id.item1)
        item2 = view.findViewById(R.id.item2)
        item3 = view.findViewById(R.id.item3)
        item1!!.setOnClickListener{
            select!!.animate().x(0f).duration = 100
            item1?.setTextColor(Color.WHITE)
            item2!!.setTextColor(def)
            item3!!.setTextColor(def)
        }
        item2!!.setOnClickListener{
            item1!!.setTextColor(def)
            item2?.setTextColor(Color.WHITE)
            item3!!.setTextColor(def)
            val size = item2!!.width
            select!!.animate().x(size.toFloat()).duration = 100
        }
        item3!!.setOnClickListener {
            item1!!.setTextColor(def)
            item3?.setTextColor(Color.WHITE)
            item2!!.setTextColor(def)
            val size = item2!!.width * 2
            select!!.animate().x(size.toFloat()).duration = 100
        }
        select = view.findViewById(R.id.select)
        def = item2!!.textColors
    }

    override fun onDestroy() {
        super.onDestroy()
        userRealm.close()
        profileRealm.close()
    }
}
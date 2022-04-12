package com.example.fitshare.Profile


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.widget.SwitchCompat
import com.example.fitshare.R
import com.example.fitshare.fitApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration


class ProfileFragment : Fragment() {

    private var user: io.realm.mongodb.User? = null
    private lateinit var profileRealm: Realm
    private lateinit var userRealm: Realm
    private lateinit var fab: FloatingActionButton
    private lateinit var partition: String
    private lateinit var otherProfileButton: Button
    private lateinit var meetUp: SwitchCompat


    var def: ColorStateList? = null
    var item1: TextView? = null
    var item2: TextView? = null
    var item3: TextView? = null
    var select: TextView? = null

    private lateinit var fullName : TextView
    private lateinit var username : TextView
    private lateinit var phone : TextView
    private lateinit var address : TextView
    private lateinit var bio : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        onClick(view)
        user = fitApp.currentUser()
        partition = "Profile"
        val config = SyncConfiguration.Builder(user!!, partition).build()

        //Profile Realm sync config
        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm) {
                this@ProfileFragment.profileRealm = realm
                    val oldProf = profileRealm.where(Profile::class.java).
                    equalTo("userid", user?.id.toString()).findFirst()
                    if(oldProf?.meetUp == true){
                        meetUp.isChecked = true
                    }else{meetUp.isChecked = false}

                username = view.findViewById(R.id.tvUsername)
                fullName = view.findViewById(R.id.txtFullName)
                phone = view.findViewById(R.id.txtPhone)
                address = view.findViewById(R.id.txtAddress)
                bio = view.findViewById(R.id.txtBio)

                username.setText(oldProf?.username.toString())
                fullName.setText(oldProf?.firstName.toString() + ", " + oldProf?.lastName.toString())
                phone.setText(oldProf?.phoneNumber.toString())
                address.setText(oldProf?.address.toString() + ", " + oldProf?.city.toString()
                        +", "+ oldProf?.state.toString() +", " + oldProf?.zipcode.toString())
                bio.setText(oldProf?.bio.toString())
                }
        })


        //Meet-up status functionality
        meetUp = view.findViewById(R.id.switchMeetUp)
        meetUp.setOnClickListener{

            //Check box functionality
            profileRealm.executeTransactionAsync{
                val oldProf = it.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()
                if(meetUp.isChecked()){
                        oldProf?.meetUp = true
                    }
                else if(!meetUp.isChecked()){
                        oldProf?.meetUp = false
                    }
                Log.i("profile", oldProf?.meetUp.toString())
            }
        }


//        //Button for adding/editing a profile
//        fab = view.findViewById(R.id.btnEditProfile)
//        fab.setOnClickListener{
//            val editProfileButton : ProfileEditButton = ProfileEditButton.newInstance()
//            editProfileButton.show(parentFragmentManager, null)
//        }

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
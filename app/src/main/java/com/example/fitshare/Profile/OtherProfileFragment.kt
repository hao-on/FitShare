package com.example.fitshare.Profile

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.fitshare.MainActivity
import com.example.fitshare.MessageForum.ForumPostFragment
import com.example.fitshare.R
import com.example.fitshare.fitApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration
import org.bson.types.ObjectId

class OtherProfileFragment : Fragment(){
    private var user: io.realm.mongodb.User? = null
    private lateinit var profileRealm: Realm
    private lateinit var userRealm: Realm
    private lateinit var fab: FloatingActionButton
    private lateinit var partition: String
    private lateinit var meetUp: SwitchCompat
    private lateinit var messageBtn: ImageButton
    private lateinit var btnLocation: ImageButton
    private lateinit var profileDetails : TextView

    private lateinit var fullName : TextView
    private lateinit var username : TextView
    private lateinit var phone : TextView
    private lateinit var address : TextView
    private lateinit var bio : TextView
    private var removeNavBar = View.GONE

    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        if (activity is MainActivity){
            var mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(removeNavBar)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        var profileID = arguments?.getString("profileID")


        //***SET USER EQUAL TO PASSED USER***
        user = fitApp.currentUser()
        partition = "Profile"
        val config = SyncConfiguration.Builder(user!!, partition).build()

        //Profile Realm sync config
        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm) {
                this@OtherProfileFragment.profileRealm = realm
//                val oldProf = profileRealm.where(Profile::class.java).
//                equalTo("userid", user?.id.toString()).findFirst()
                val oldProf = profileRealm.where(Profile::class.java).
                equalTo("_id", ObjectId(profileID)).findFirst()

                userID = oldProf?.userid.toString()
                Log.i("Profile", oldProf?.username.toString())

                btnLocation.isVisible = false
                meetUp.isClickable = false

                if(oldProf?.meetUp == true){
                    meetUp.isChecked = true
                }else{meetUp.isChecked = false}

                username = view.findViewById(R.id.tvUsername)
                fullName = view.findViewById(R.id.txtFullName)
                phone = view.findViewById(R.id.txtPhone)
                address = view.findViewById(R.id.txtAddress)
                bio = view.findViewById(R.id.txtBio)

                if(oldProf == null){
                    username.setText("Username")
                    fullName.setText("First Name, Last Name")
                    phone.setText("Phone Number")
                    address.setText("Address, City, State, Zipcode")
                    bio.setText("My Bio")
                }else{
                    username.setText(oldProf?.username.toString())
                    fullName.setText(oldProf?.firstName.toString() + ", " + oldProf?.lastName.toString())
//                    phone.setText("(***)***-" + oldProf?.phoneNumber?.get(6)?.toString() + oldProf?.phoneNumber?.get(7)?.toString() +
//                            oldProf?.phoneNumber?.get(8)?.toString() + oldProf?.phoneNumber?.get(9)?.toString())
                    phone.setText(oldProf?.phoneNumber)
                    address.setText(oldProf?.city.toString() +", "+ oldProf?.state.toString() )
                    bio.setText(oldProf?.bio.toString())
                }
            }
        })

        btnLocation = view.findViewById(R.id.btnLocation)

        //Meet-up status init
        meetUp = view.findViewById(R.id.switchMeetUp)

        messageBtn = view.findViewById(R.id.btnChat)
        messageBtn.setOnClickListener{
            var forumFragment : Fragment = ForumPostFragment()
            val bundle = Bundle()
            bundle.putString("userID", userID)
            forumFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, forumFragment, "forum")
                .addToBackStack("forum")
                .commit()
        }

        //Profile Details Fragment
//        profileDetails = view.findViewById(R.id.linkProfileDetails)
//        profileDetails.setOnClickListener {
//            var profileDetailsFragment: Fragment = ProfileDetailsFragment()
//            val bundle = Bundle()
//            bundle.putString("postID", adapter.getItem(position)?.id.toString())
//            commentFragment.arguments = bundle
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.frameLayout, profileDetailsFragment, "profileDetails")
//                .addToBackStack("profileDetails")
//                .commit()
//        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        profileRealm.close()
    }
    companion object{
        fun newInstance(): OtherProfileFragment{
            return OtherProfileFragment()
        }
    }
}
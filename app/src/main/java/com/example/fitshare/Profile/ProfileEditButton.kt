package com.example.fitshare.Profile

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.fitshare.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.realm.Realm
import com.example.fitshare.fitApp
import com.google.android.material.textfield.TextInputEditText
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.edit_profile_button.*

class ProfileEditButton : BottomSheetDialogFragment() {
    private lateinit var profileRealm: Realm
    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var submitButton: Button
    private  lateinit var partition: String

    private lateinit var firstName : TextInputEditText
    private lateinit var lastName :TextInputEditText
    private lateinit var username :TextInputEditText
    private lateinit var phone :TextInputEditText
    private lateinit var address :TextInputEditText
    private lateinit var zipcode :TextInputEditText
    private lateinit var city: TextInputEditText
    private lateinit var bio :TextInputEditText
    private lateinit var stateSpinner: Spinner


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.edit_profile_button, container, false)

        user = fitApp.currentUser()
        partition = "Profile"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ProfileEditButton.profileRealm = realm
                //Find this current user's profile
                val oldProf = profileRealm.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()

                //Spinner Adapter
                stateSpinner = view.findViewById(R.id.editProfile_state)
                ArrayAdapter.createFromResource(requireContext() , R.array.profile_states, android.R.layout.simple_spinner_item)
                    .also{ adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        stateSpinner.adapter = adapter
                        val spinnerLocation = adapter.getPosition(oldProf?.state.toString())
                        stateSpinner.setSelection(spinnerLocation)
                    }

                if(oldProf != null) {
                    firstName.setText(oldProf?.firstName)
                    lastName.setText(oldProf?.lastName)
                    username.setText(oldProf?.username)
                    phone.setText(oldProf?.phoneNumber)
                    address.setText(oldProf?.address)
                    zipcode.setText(oldProf?.zipcode)
                    city.setText(oldProf?.city)
                    bio.setText(oldProf?.bio)
                }
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
        firstName = view.findViewById(R.id.editProfile_first)
        lastName = view.findViewById(R.id.editProfile_last)
        username = view.findViewById(R.id.editProfile_username)
        phone = view.findViewById(R.id.editProfile_phone)
        address = view.findViewById(R.id.editProfile_address)
        zipcode = view.findViewById(R.id.editProfile_zip)
        city = view.findViewById(R.id.editProfile_city)
        bio = view.findViewById(R.id.editProfileBio)



        //Submit profile changes
        submitButton = view.findViewById(R.id.btnSubmitProfile)
        submitButton.setOnClickListener{

            profileRealm.executeTransactionAsync{
                Log.i("checkerProf", user?.id.toString())

                val oldProf = it.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()

                Log.i("checkerProf", oldProf?.id.toString())

                if(oldProf == null){
                    val profile =  Profile(firstName.text.toString(),
                        lastName.text.toString(), bio.text.toString(),
                        address.text.toString(), zipcode.text.toString(), city.text.toString(),
                        stateSpinner.selectedItem.toString(), phone.text.toString(),
                        username.text.toString(), false, user?.id.toString())

                    it.insertOrUpdate(profile)
                }else {
                    oldProf?.firstName = firstName.text.toString()
                    oldProf?.lastName = lastName.text.toString()
                    oldProf?.bio = bio.text.toString()
                    oldProf?.address = address.text.toString()
                    oldProf?.zipcode = zipcode.text.toString()
                    oldProf?.city = city.text.toString()
                    oldProf?.state = stateSpinner.selectedItem.toString()
                    oldProf?.phoneNumber = phone.text.toString()
                    oldProf?.username = username.text.toString()
                    oldProf?.userid = user?.id.toString()
                    Log.i("checkerProf", oldProf?.id.toString())
                    it.insertOrUpdate(oldProf)
                }
            }
            dialog?.dismiss()
            //Restart the fragment to update the recycler view
            var profileFragment : Fragment = ProfileFragment()
            val bundle = Bundle()
            profileFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frameLayout, profileFragment).commit()
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()

        userRealm.close()
        profileRealm.close()
    }

    companion object{
        fun newInstance(): ProfileEditButton{
            return ProfileEditButton()
        }
    }
}
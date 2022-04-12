package com.example.fitshare.Profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import com.example.fitshare.LoginActivity
import com.example.fitshare.R
import com.example.fitshare.fitApp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class ProfileMenu: BottomSheetDialogFragment() {
    private lateinit var editProfile: LinearLayout
    private lateinit var logout: LinearLayout
    private var user: io.realm.mongodb.User? = null

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.layout_profile_menu, container, false)
//        BottomSheetBehavior.from(view).peekHeight = 100
        user = fitApp.currentUser()
        editProfile = view.findViewById(R.id.editProfile)
        editProfile.setOnClickListener {
            val editProfileButton : ProfileEditButton = ProfileEditButton.newInstance()
            editProfileButton.show(parentFragmentManager, null)
        }

        logout = view.findViewById(R.id.logout)
        logout.setOnClickListener {
            Toast.makeText(requireActivity().applicationContext, "Log Out", Toast.LENGTH_SHORT).show()

            user?.logOutAsync {
                if (it.isSuccess) {
                    user = null
                    Log.v("logout", "user logged out")
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                } else {
                    Log.e("logout", "log out failed! Error: ${it.error}")
                }
            }
        }

        return view
    }

    override fun getTheme(): Int {
        return R.style.RoundDialogTheme
    }

    companion object {
        fun newInstance(): ProfileMenu {
            return ProfileMenu()
        }
    }
}

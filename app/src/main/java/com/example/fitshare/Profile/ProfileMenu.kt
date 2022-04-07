package com.example.fitshare.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import com.example.fitshare.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class ProfileMenu: BottomSheetDialogFragment() {
    private lateinit var editProfile: LinearLayout
    private lateinit var logout: LinearLayout

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.layout_profile_menu, container, false)
//        BottomSheetBehavior.from(view).peekHeight = 100

        editProfile = view.findViewById(R.id.editProfile)
        editProfile.setOnClickListener {
            Toast.makeText(requireActivity().applicationContext, "Edit Profile", Toast.LENGTH_SHORT).show()
        }

        logout = view.findViewById(R.id.logout)
        logout.setOnClickListener {
            Toast.makeText(requireActivity().applicationContext, "Log Out", Toast.LENGTH_SHORT).show()
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

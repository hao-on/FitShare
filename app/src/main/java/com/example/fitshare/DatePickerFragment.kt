package com.example.fitshare

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val calendar= Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val year= calendar.get(Calendar.YEAR)
        val month= calendar.get(Calendar.MONTH)
        val day= calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
     calendar.set(Calendar.YEAR,year)
     calendar.set(Calendar.MONTH,month)
     calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

    }
    companion object {
        fun newInstance(): DatePickerFragment {
            return DatePickerFragment()
        }
    }
}

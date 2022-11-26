package com.example.delegadosapp.vista.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.delegadosapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class DelegadoMenu : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.menu_delega, container, false)
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}

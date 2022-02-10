package com.example.gitapitestapp.presentation.fragments

import androidx.fragment.app.Fragment

interface OnFragmentInteractionsListener {

    fun onChangeFragment(fragment: Fragment)

    fun onAddBackStack(name: String, fragment: Fragment)

    fun onPopBackStack()
}
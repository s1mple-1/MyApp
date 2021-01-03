package ru.s1mple.myapp

import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}
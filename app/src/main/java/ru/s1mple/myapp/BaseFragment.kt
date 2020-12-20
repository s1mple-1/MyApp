package ru.s1mple.myapp

import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    internal var dataProvider: IDataProvider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appContext = context.applicationContext
        if (appContext is IDataProvider) {
            dataProvider = appContext
        }
    }

    override fun onDetach() {
        dataProvider = null
        super.onDetach()
    }
}
package com.example.bookkeeper.base_classes

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment<T: BaseViewModel>: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun showToast(message: String){
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
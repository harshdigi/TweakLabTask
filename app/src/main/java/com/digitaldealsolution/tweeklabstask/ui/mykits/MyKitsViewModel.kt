package com.digitaldealsolution.tweeklabstask.ui.mykits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyKitsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _text = MutableLiveData<String>().apply {
        value = "This is My Kits Fragment"
    }
    val text: LiveData<String> = _text
}
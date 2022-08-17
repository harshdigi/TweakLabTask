package com.digitaldealsolution.tweeklabstask.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _text = MutableLiveData<String>().apply {
        value = "This is News Fragment"
    }
    val text: LiveData<String> = _text
}
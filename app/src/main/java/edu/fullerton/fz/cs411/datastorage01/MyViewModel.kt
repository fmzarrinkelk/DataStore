package edu.fullerton.fz.cs411.datastorage01

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {
    private val prefs = MyDataStoreRepository.get()
    fun saveInput(s: String, index: Int) {
        viewModelScope.launch {
            prefs.saveInput(s, index)
            Log.v(Log_Tag, "Saved input $index ")
        }
    }
    fun loadInputs(act: MainActivity) {
        viewModelScope.launch {
            prefs.firstName.collectLatest {
                act.fistName.text = it.toString()
                Log.v(Log_Tag, "loaded first name")
            }
        }
        viewModelScope.launch {
            prefs.lastName.collectLatest {
                act.lastName.text = it.toString()
                Log.v(Log_Tag, "loaded last name")
            }
        }
    }
}
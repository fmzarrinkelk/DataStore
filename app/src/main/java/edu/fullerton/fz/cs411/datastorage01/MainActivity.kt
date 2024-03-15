package edu.fullerton.fz.cs411.datastorage01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val Log_Tag = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val myViewModel: MyViewModel by lazy {
        MyDataStoreRepository.initialize(this)
        MyViewModel()
    }

    lateinit var fistName: TextView
    lateinit var lastName: TextView
    lateinit var save: Button
    lateinit var clear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectWidgets()
        setActions()
        loadData()
    }
    private fun connectWidgets() {
        fistName = findViewById(R.id.firstName)
        lastName = findViewById(R.id.lastName)
        save = findViewById(R.id.save)
        clear = findViewById(R.id.clear)
    }
    private fun setActions() {
        save.setOnClickListener { saveData() }
        clear.setOnClickListener { fistName.text = "" ; lastName.text = "" }
    }
    private fun loadData() {
        myViewModel.loadInputs(this)
    }
    private fun saveData() {
        myViewModel.saveInput(fistName.text.toString(), 1)
        myViewModel.saveInput(lastName.text.toString(), 2)
    }
}
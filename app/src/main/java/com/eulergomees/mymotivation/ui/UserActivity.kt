package com.eulergomees.mymotivation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eulergomees.mymotivation.helper.MotivationConstants
import com.eulergomees.mymotivation.R
import com.eulergomees.mymotivation.databinding.ActivityUserBinding
import com.eulergomees.mymotivation.repository.SecurityPreferences

class UserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserBinding
    private lateinit var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserBinding.inflate(layoutInflater)

        securityPreferences = SecurityPreferences(this)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
        verifyUserName()

    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            handleSave()
        }
    }

    private fun verifyUserName() {
        val name = securityPreferences.getString(MotivationConstants.KEY.PERSON_NAME)
        if (name.isNotEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun handleSave() {
        val name = binding.edittextName.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(this, "Informe seu nome!", Toast.LENGTH_SHORT).show()
        } else {
            securityPreferences.storeString(MotivationConstants.KEY.PERSON_NAME, name)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setListeners() {
        binding.buttonSave.setOnClickListener(this)
    }

}
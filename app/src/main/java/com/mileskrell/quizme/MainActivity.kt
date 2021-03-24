package com.mileskrell.quizme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.mileskrell.quizme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(
            navController,
            AppBarConfiguration(setOf(R.id.quizFragment, R.id.scoreFragment))
        )
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}

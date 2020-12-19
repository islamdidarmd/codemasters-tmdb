package com.codemasters.tmdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.codemasters.tmdb.R
import com.codemasters.tmdb.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityHomeBinding

    private var currentNavDestinationId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        setUpBottomNavView()
    }

    private fun setUpBottomNavView() {
        _binding.apply {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            bottomNavigationView.setupWithNavController(navHostFragment.navController)

            bottomNavigationView.setOnNavigationItemReselectedListener {
                //our goal here is to navigating to the top destination of current tab when an Item
                //is reselected. But we will not allow navigating when we are already on top.

                if (it.itemId == currentNavDestinationId) return@setOnNavigationItemReselectedListener

                val navOptions = NavOptions.Builder()
                    .setPopUpTo(it.itemId, true)
                    .build()
                navController.navigate(it.itemId, null, navOptions)
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                currentNavDestinationId = destination.id
            }
        }
    }
}
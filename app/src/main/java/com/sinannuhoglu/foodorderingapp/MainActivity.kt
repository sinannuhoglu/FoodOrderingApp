package com.sinannuhoglu.foodorderingapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.sinannuhoglu.foodorderingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as? NavHostFragment
        val navController = navHostFragment?.navController

        navController?.let { controller ->
            binding.bottomNavigation.apply {
                setupWithNavController(controller)
                labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED
            }

            controller.addOnDestinationChangedListener { _, destination, _ ->
                binding.bottomNavigation.visibility =
                    if (destination.id == R.id.splashFragment) View.GONE
                    else View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = (supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

package com.example.utsanmp160421137.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.utsanmp160421137.R
import com.example.utsanmp160421137.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this,navController,binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment) {
                binding.navView.visibility = View.GONE
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
                binding.bottomNav.visibility = View.VISIBLE
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    override fun onBackPressed() {
//        // Cek apakah user sedang di ProfileFragment
//        if (navController.currentDestination?.id == R.id.profileFragment) {
//            // Navigasi langsung ke NovelListFragment menggunakan ID fragment-nya
//            navController.navigate(R.id.novelListFragment)
//        } else {
//            super.onBackPressed()
//        }
//    }

}
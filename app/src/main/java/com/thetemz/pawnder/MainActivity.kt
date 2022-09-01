package com.thetemz.pawnder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mymftcustomer.preference.PreferenceHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thetemz.pawnder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private var count: Int = 1
    var android_id: String? = null
    private var helper: PreferenceHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        helper = PreferenceHelper.getPreference(this)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container_view) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.bottomNavigation,
            navHostFragment.navController
        )

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        navController = navHostFragment.navController

        navController?.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id) {
                R.id.nav_graph_home -> showBottomBar(true)
                R.id.nav_graph_petlist -> showBottomBar(true)
                R.id.nav_graph_account -> showBottomBar(true)
                else -> showBottomBar(false)
            }

        }
    }


    private fun showBottomBar(show: Boolean) {
        binding.bottomNavigation.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectNavigationItem(item);
        return NavigationUI.onNavDestinationSelected(item, navController!!);
    }

    private fun selectNavigationItem(itemId: MenuItem) {
        //navController!!.navigate(R.id.nav_graph_home)
        if (itemId.toString().equals("Home")) {
            navController!!.navigate(R.id.nav_graph_home)
        } else if (itemId.toString().equals("Pet Information")) {
            Toast.makeText(this,
                itemId.toString(),
                Toast.LENGTH_SHORT
            ).show()
           // navController!!.navigate(R.id.nav_graph_petlist)
        }
    }

}



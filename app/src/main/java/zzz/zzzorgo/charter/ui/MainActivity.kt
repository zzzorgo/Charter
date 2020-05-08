package zzz.zzzorgo.charter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

import zzz.zzzorgo.charter.R

val bottomNavBarItemIdToNavAction = mapOf(
    R.id.destination_account_list to R.id.action_to_account_list,
    R.id.destination_record_list to R.id.action_to_record_list
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
        setupBottomNavBar()
    }

    private fun setupBottomNavBar() {
        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNavBar.setupWithNavController(navController)

        bottomNavBar.setOnNavigationItemSelectedListener {
            val itemId = it.itemId
            val actionId = bottomNavBarItemIdToNavAction[itemId]

            actionId?.let {
                navController.popBackStack()
                navController.navigate(actionId)
            }

            true
        }

        bottomNavBar.setOnNavigationItemReselectedListener { }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

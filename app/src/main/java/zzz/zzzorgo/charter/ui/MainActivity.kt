package zzz.zzzorgo.charter.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import zzz.zzzorgo.charter.CharterApplication
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.data.repo.CurrencyRepository
import zzz.zzzorgo.charter.ui.misc.CategoryManagerFragment
import zzz.zzzorgo.charter.ui.record.RecordViewModel
import zzz.zzzorgo.charter.ui.statistic.StatisticViewModel
import javax.inject.Inject

val bottomNavBarItemIdToNavAction = mapOf(
    R.id.destination_account_list to R.id.action_to_account_list,
    R.id.destination_record_list to R.id.action_to_record_list,
    R.id.destination_statistic to R.id.action_to_statistic,
    R.id.destination_misc to R.id.action_to_misc
)

class MainActivity : AppCompatActivity(),
    CategoryManagerFragment.OnListFragmentInteractionListener
{
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val recordViewModel by viewModels<RecordViewModel> { viewModelFactory }
    private val statisticViewModel by viewModels<StatisticViewModel> { viewModelFactory }

    @Inject
    lateinit var currencyRepository: CurrencyRepository

    var data: Map<String?, String?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as CharterApplication).appComponent.inject(this)


        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
        setupBottomNavBar()
        preFetchAllData()
    }

    private fun preFetchAllData() {
        recordViewModel.allRecords.observe(this, Observer {  })
        statisticViewModel.updateCurrencyValues()
        statisticViewModel.balanceEntries.observe(this, Observer {  })
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

    override fun onListFragmentInteraction(item: View) {
        println("123")
    }
}

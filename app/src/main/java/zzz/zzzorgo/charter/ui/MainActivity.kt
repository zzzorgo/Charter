package zzz.zzzorgo.charter.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.volley.Response
import com.google.android.material.bottomnavigation.BottomNavigationView
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.data.model.cbr.CbrCurrencyList
import zzz.zzzorgo.charter.ui.misc.CategoryManagerFragment
import zzz.zzzorgo.charter.utils.ApplicationGraph
import zzz.zzzorgo.charter.utils.DaggerApplicationGraph
import zzz.zzzorgo.charter.utils.Network
import zzz.zzzorgo.charter.utils.XmlRequest

val bottomNavBarItemIdToNavAction = mapOf(
    R.id.destination_account_list to R.id.action_to_account_list,
    R.id.destination_record_list to R.id.action_to_record_list,
    R.id.destination_misc to R.id.action_to_misc
)

class MainActivity : AppCompatActivity(),
    CategoryManagerFragment.OnListFragmentInteractionListener
{
    var data: Map<String?, String?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
        setupBottomNavBar()
        val applicationGraph: ApplicationGraph = DaggerApplicationGraph.create()
        getDataFromNetwork(this.applicationContext)
    }

    fun getDataFromNetwork(context: Context) {
        val url = "https://www.cbr.ru/scripts/XML_valFull.asp"

        val jsonObjectRequest = XmlRequest(
            url, CbrCurrencyList::class.java, null,
            Response.Listener { response ->
                val mapIsoToCbrCurrency = response.Valuta?.Item?.map {
                    it.ISO_Char_Code to it.ParentCode?.trim()
                }?.toMap()
                println(mapIsoToCbrCurrency)
                data = mapIsoToCbrCurrency
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                println(error)
            }
        )

// Access the RequestQueue through your singleton class.
        Network.getInstance(context).addToRequestQueue(jsonObjectRequest)
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

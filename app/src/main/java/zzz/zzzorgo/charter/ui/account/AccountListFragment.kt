package zzz.zzzorgo.charter.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_account_list.*
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.utils.showFragmentDialog

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AccountListFragment : Fragment() {
    private lateinit var accountListAdapter: AccountListAdapter
    private val accountViewModel by viewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModel.accountsWithTotal.observe(viewLifecycleOwner, Observer { accounts ->
            accountListAdapter.setAccounts(accounts)
        })

        return inflater.inflate(R.layout.fragment_account_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountListAdapter = AccountListAdapter(this.requireContext())
        account_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = accountListAdapter
        }

        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {
            showDialog()
        }
    }

    fun showDialog() {
        val newFragment = AccountEditFragment()
        showFragmentDialog(requireActivity(), newFragment)
    }
}

package zzz.zzzorgo.charter.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_account_list.*
import zzz.zzzorgo.charter.R

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
        // Inflate the layout for this fragment

        accountViewModel.allAccounts.observe(this.viewLifecycleOwner, Observer { accounts ->
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
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}

package zzz.zzzorgo.charter.ui.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import zzz.zzzorgo.charter.R

class AccountListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<AccountListAdapter.AccountViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var accounts = emptyList<AccountWithTotal>() // Cached copy of words

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val accountItemView: ConstraintLayout = itemView.findViewById(R.id.account_item_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val itemView = inflater.inflate(R.layout.item_account, parent, false)
        return AccountViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val current = accounts[position]
        val accountNameView = holder.accountItemView.findViewById<TextView>(R.id.account_name)
        val accountTotalView = holder.accountItemView.findViewById<TextView>(R.id.account_total)
        accountNameView.text = current.account.name
        accountTotalView.text = String.format("%s", current.total)
    }

    internal fun setAccounts(accounts: List<AccountWithTotal>) {
        this.accounts = accounts
        notifyDataSetChanged()
    }

    override fun getItemCount() = accounts.size
}
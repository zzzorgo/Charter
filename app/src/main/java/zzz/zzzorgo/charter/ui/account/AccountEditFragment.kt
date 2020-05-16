package zzz.zzzorgo.charter.ui.account

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.utils.hideKeyboard
import java.math.BigDecimal
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AccountEditFragment : DialogFragment() {

    val accountViewModel by viewModels<AccountViewModel>()
    private lateinit var editAccountNameField: EditText
    private lateinit var editAccountTotalField: EditText
    private lateinit var currentCurrency: Currency


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        accountViewModel.currency.observe(viewLifecycleOwner, Observer {
            currentCurrency = it
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editAccountNameField = view.findViewById(R.id.edit_account_name_field)
        editAccountTotalField = view.findViewById(R.id.edit_account_total_field)
        val saveButton = view.findViewById<Button>(R.id.save_account_button)

        saveButton.setOnClickListener {
            if (!TextUtils.isEmpty(editAccountNameField.text)) {
                val name = editAccountNameField.text.toString()
                val total = editAccountTotalField.text.toString()
                val newAccount = Account(name).apply {
                    initialValue = BigDecimal(total)
                    currency = currentCurrency
                }
                accountViewModel.insert(newAccount)
                hideKeyboard(requireActivity())
                dismiss()
            }
        }
    }
}

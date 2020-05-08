package zzz.zzzorgo.charter.ui.account

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.data.model.Account
import java.math.BigDecimal

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AccountEditFragment : Fragment() {

    val accountViewModel by viewModels<AccountViewModel>()
    private lateinit var editAccountNameField: EditText
    private lateinit var editAccountTotalField: EditText


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
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
                val newAccount = Account(name)
                newAccount.total = BigDecimal(total)
                accountViewModel.insert(newAccount)
                findNavController().navigateUp()
            }

        }
//        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }
}

package zzz.zzzorgo.charter.ui.record

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import zzz.zzzorgo.charter.CharterApplication
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.model.Category
import zzz.zzzorgo.charter.data.model.Record
import zzz.zzzorgo.charter.utils.hideKeyboard
import java.math.BigDecimal
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RecordEditFragment : DialogFragment() {

    private lateinit var editRecordValueToField: EditText
    private lateinit var editRecordValueFromField: EditText
    private lateinit var editCategoryField: Spinner
    private lateinit var categoriesAdapter: ArrayAdapter<Category>
    private lateinit var editRecordAccountToField: Spinner
    private lateinit var editRecordAccountFromField: Spinner
    private lateinit var accountsAdapter: ArrayAdapter<Account>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<RecordViewModel> { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as CharterApplication).appComponent.inject(this)
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editCategoryField = view.findViewById(R.id.edit_record_category)
        editRecordAccountFromField = view.findViewById(R.id.edit_record_account_from)
        editRecordAccountToField = view.findViewById(R.id.edit_record_account_to)

        categoriesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, mutableListOf())
        accountsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, mutableListOf())
        editCategoryField.adapter = categoriesAdapter
        editRecordAccountToField.adapter = accountsAdapter
        editRecordAccountFromField.adapter = accountsAdapter

        viewModel.allCategories.observe(viewLifecycleOwner, Observer {
            categoriesAdapter.clear()
            categoriesAdapter.addAll(it)
        })

        viewModel.allAccounts.observe(viewLifecycleOwner, Observer {
            accountsAdapter.clear()
            accountsAdapter.add(Account(""))
            accountsAdapter.addAll(it)
        })

        editRecordValueToField = view.findViewById(R.id.edit_record_value_to)
        editRecordValueFromField = view.findViewById(R.id.edit_record_value_from)
        val saveButton = view.findViewById<Button>(R.id.save_record_button)

        saveButton.setOnClickListener {
            val categoryId = (editCategoryField.selectedItem as Category).id
            val accountFrom = editRecordAccountFromField.selectedItem as Account
            val accountTo = editRecordAccountToField.selectedItem as Account
            val record = Record(categoryId).apply {
                this.accountFrom = accountFrom.id
                this.accountTo = accountTo.id
                valueFrom = BigDecimal(editRecordValueFromField.text.toString())
                valueTo = BigDecimal(editRecordValueToField.text.toString())
                currencyFrom = if (accountFrom.name != "") accountFrom.currency else null
                currencyTo = if (accountTo.name != "") accountTo.currency else null
            }

            viewModel.insert(record)
            hideKeyboard(requireActivity())
            dismiss()
        }
    }
}

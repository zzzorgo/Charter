package zzz.zzzorgo.charter.ui.misc

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.data.model.Category
import zzz.zzzorgo.charter.utils.MyApplication
import zzz.zzzorgo.charter.utils.hideKeyboard
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CategoryManagerEditFragment : DialogFragment() {
    private lateinit var editCategoryNameField: EditText
    private lateinit var editCategoryIncomeField: Switch
    private lateinit var editCategoryOutcomeField: Switch

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<CategoryManagerViewModel> { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_manager_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editCategoryNameField = view.findViewById(R.id.edit_category_name_field)
        editCategoryIncomeField = view.findViewById(R.id.edit_category_income_field)
        editCategoryOutcomeField = view.findViewById(R.id.edit_category_outcome_field)
        val saveButton = view.findViewById<Button>(R.id.save_category_button)

        saveButton.setOnClickListener {
            if (!TextUtils.isEmpty(editCategoryNameField.text)) {
                val name = editCategoryNameField.text.toString()

                val newCategory = Category(name).apply {
                    income = editCategoryIncomeField.isChecked
                    outcome = editCategoryOutcomeField.isChecked
                }

                viewModel.insert(newCategory)
                hideKeyboard(requireActivity())
                dismiss()
            }
        }
    }
}

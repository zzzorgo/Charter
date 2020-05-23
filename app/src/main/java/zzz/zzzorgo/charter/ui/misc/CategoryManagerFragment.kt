package zzz.zzzorgo.charter.ui.misc

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.utils.showFragmentDialog

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CategoryManagerFragment.OnListFragmentInteractionListener] interface.
 */
class CategoryManagerFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    private val viewModel by viewModels<CategoryManagerViewModel>()
    private lateinit var listAdapter: CategoryManagerRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category_manager_list, container, false)

        viewModel.allCategories.observe(viewLifecycleOwner, Observer {
            listAdapter.setCategories(it)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = CategoryManagerRecyclerViewAdapter(listener)

        view.findViewById<RecyclerView>(R.id.category_list).apply {
            layoutManager = GridLayoutManager(context, columnCount)
            adapter = listAdapter
        }

        view.findViewById<FloatingActionButton>(R.id.add_category_button).setOnClickListener {
            val fragment = CategoryManagerEditFragment()
            showFragmentDialog(requireActivity(), fragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: View)
    }

}

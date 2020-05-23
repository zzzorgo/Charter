package zzz.zzzorgo.charter.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_record_list.*
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.utils.showFragmentDialog

class RecordListFragment : Fragment() {
    private lateinit var recordListAdapter: RecordListAdapter
    private val accountViewModel by viewModels<RecordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModel.allRecords.observe(this.viewLifecycleOwner, Observer {
            recordListAdapter.setRecords(it)
        })

        return inflater.inflate(R.layout.fragment_record_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recordListAdapter = RecordListAdapter(requireContext())

        record_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recordListAdapter
        }

        view.findViewById<FloatingActionButton>(R.id.add_record_button).setOnClickListener {
            showFragmentDialog(requireActivity(), RecordEditFragment())
        }
    }
}
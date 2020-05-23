package zzz.zzzorgo.charter.ui.misc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import zzz.zzzorgo.charter.R

/**
 * A simple [Fragment] subclass.
 * Use the [MiscFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MiscFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_misc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        view.findViewById<Button>(R.id.open_category_manager).setOnClickListener {
            navController.navigate(R.id.action_destination_misc_to_categoryManagerFragment)
        }
    }
}

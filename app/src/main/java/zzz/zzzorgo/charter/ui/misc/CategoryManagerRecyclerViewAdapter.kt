package zzz.zzzorgo.charter.ui.misc


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category_manager.view.*
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.data.model.Category
import zzz.zzzorgo.charter.ui.misc.CategoryManagerFragment.OnListFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class CategoryManagerRecyclerViewAdapter(
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<CategoryManagerRecyclerViewAdapter.ViewHolder>() {

    private var mValues: List<Category> = emptyList()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(v)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_manager, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.id.toString()
        holder.mContentView.text = item.name

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    fun setCategories(categories: List<Category>) {
        mValues = categories
        notifyDataSetChanged()
    }
}

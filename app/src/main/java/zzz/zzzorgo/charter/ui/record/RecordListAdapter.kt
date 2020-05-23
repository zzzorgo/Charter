package zzz.zzzorgo.charter.ui.record

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.data.model.Record

class RecordListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<RecordListAdapter.RecordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var records = emptyList<Record>() // Cached copy of words

    inner class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recordItemView: ConstraintLayout = itemView.findViewById(R.id.record_item_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val itemView = inflater.inflate(R.layout.item_record, parent, false)
        return RecordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val current = records[position]
        val recordCategoryView = holder.recordItemView.findViewById<TextView>(R.id.record_category)
        val recordTotalView = holder.recordItemView.findViewById<TextView>(R.id.record_total)
        val recordDateView = holder.recordItemView.findViewById<TextView>(R.id.record_date)
        recordCategoryView.text =  String.format("%s", current.valueFrom)
        recordTotalView.text = String.format("%s", current.valueTo)
        recordDateView.text = current.date.toString()
    }

    internal fun setRecords(records: List<Record>) {
        this.records = records
        notifyDataSetChanged()
    }

    override fun getItemCount() = records.size
}
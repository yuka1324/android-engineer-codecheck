package jp.co.yumemi.android.code_check.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.SearchResultContents

class SearchAdapter : ListAdapter<SearchResultContents, SearchAdapter.ViewHolder>(diffUtil) {

    private lateinit var listener: OnItemClickListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(item: SearchResultContents)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            item.full_name

        holder.itemView.setOnClickListener {
            listener.itemClick(item)
        }
    }

}

val diffUtil = object : DiffUtil.ItemCallback<SearchResultContents>() {
    override fun areItemsTheSame(
        oldItem: SearchResultContents,
        newItem: SearchResultContents
    ): Boolean {
        return oldItem.full_name == newItem.full_name
    }

    override fun areContentsTheSame(
        oldItem: SearchResultContents,
        newItem: SearchResultContents
    ): Boolean {
        return oldItem == newItem
    }

}
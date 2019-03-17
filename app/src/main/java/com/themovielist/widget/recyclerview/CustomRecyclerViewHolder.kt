package com.themovielist.widget.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView


open class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val context: Context
        get() = itemView.context
}

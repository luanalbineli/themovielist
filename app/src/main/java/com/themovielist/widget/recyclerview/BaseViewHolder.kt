package com.themovielist.widget.recyclerview

import android.content.Context
import android.view.View

open class BaseViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    val context: Context = itemView.context
}
package com.example.cryptoadvisor3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EducationAdapter(private val topicList: List<EducationTopic>) :
    RecyclerView.Adapter<EducationAdapter.EducationViewHolder>() {

    class EducationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.textViewTitle)
        val descriptionTextView: TextView = view.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_education, parent, false)
        return EducationViewHolder(view)
    }

    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
        val topic = topicList[position]
        holder.titleTextView.text = topic.title
        holder.descriptionTextView.text = topic.description
    }

    override fun getItemCount() = topicList.size
}

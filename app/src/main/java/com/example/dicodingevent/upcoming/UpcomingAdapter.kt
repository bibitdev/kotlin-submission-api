package com.example.dicodingevent.upcoming

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.EventDetailActivity
import com.example.dicodingevent.R
import com.example.dicodingevent.model.ListEventsItem

class UpcomingAdapter(private var events: List<ListEventsItem?>?) : RecyclerView.Adapter<UpcomingAdapter.EventViewHolder>() {


    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.text_event_title)
        val description: TextView = view.findViewById(R.id.text_event_summary)
        val image: ImageView = view.findViewById(R.id.image_event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events?.get(position) ?: return
        holder.title.text = event.name
        holder.description.text = event.summary


        Glide.with(holder.itemView.context)
            .load(event.mediaCover ?: event.imageLogo)
            .into(holder.image)


        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EventDetailActivity::class.java).apply {
                putExtra("EXTRA_IMAGE", event.imageLogo)
                putExtra("EXTRA_TITLE", event.name)
                putExtra("EXTRA_OWNER_NAME", event.ownerName)
                putExtra("EXTRA_BEGIN_TIME", event.beginTime)
                putExtra("EXTRA_QUOTA", event.quota ?: 0)
                putExtra("EXTRA_REGISTRANT", event.registrants ?: 0)
                putExtra("EXTRA_DESCRIPTION", event.description)
                putExtra("EXTRA_LINK", event.link)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = events?.size ?: 0

    fun updateData(newEvents: List<ListEventsItem?>?) {
        events = newEvents
        notifyDataSetChanged()
    }
}

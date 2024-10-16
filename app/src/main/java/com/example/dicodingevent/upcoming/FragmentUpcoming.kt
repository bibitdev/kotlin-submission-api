package com.example.dicodingevent.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dicodingevent.R
import com.example.dicodingevent.api.RetrofitClient
import com.example.dicodingevent.model.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse

class FragmentUpcoming : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UpcomingAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upcoming, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_upcoming)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        adapter = UpcomingAdapter(emptyList())
        recyclerView.adapter = adapter

        fetchEvents()

        return view
    }

    private fun fetchEvents() {
        progressBar.visibility = View.VISIBLE

        RetrofitClient.instance.getEvents(active = 1).enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: RetrofitResponse<Response>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents
                    events?.let {
                        adapter.updateData(it)
                    }
                } else {
                    showToast("Failed to load events")
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                progressBar.visibility = View.GONE
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

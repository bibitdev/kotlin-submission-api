package com.example.dicodingevent.finished

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

class FragmentFinished : Fragment() {

        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: FinishedAdapter
        private lateinit var progressBar: ProgressBar

        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                val view = inflater.inflate(R.layout.fragment_finished, container, false)

                recyclerView = view.findViewById(R.id.recycler_view_finished)
                progressBar = view.findViewById(R.id.progressBar)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())

                adapter = FinishedAdapter(emptyList())
                recyclerView.adapter = adapter

                fetchFinishedEvents()
                return view
        }

        private fun fetchFinishedEvents() {
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                RetrofitClient.instance.getEvents(active = 0).enqueue(object : Callback<Response> {
                        override fun onResponse(call: Call<Response>, response: RetrofitResponse<Response>) {
                                progressBar.visibility = View.GONE
                                if (response.isSuccessful) {
                                        val events = response.body()?.listEvents
                                        events?.let {
                                                adapter.updateData(it)
                                                recyclerView.visibility = View.VISIBLE
                                        }
                                } else {
                                        showToast("Failed to load finished events")
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

package com.example.dicodingevent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.util.Linkify
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dicodingevent.databinding.ActivityEventDetailBinding

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val eventImage = intent.getStringExtra("EXTRA_IMAGE")
        val eventTitle = intent.getStringExtra("EXTRA_TITLE")
        val eventOwnerName = intent.getStringExtra("EXTRA_OWNER_NAME")
        val eventBeginTime = intent.getStringExtra("EXTRA_BEGIN_TIME")
        val eventQuota = intent.getIntExtra("EXTRA_QUOTA", 0)
        val eventRegistrant = intent.getIntExtra("EXTRA_REGISTRANT", 0)
        val eventDescription = intent.getStringExtra("EXTRA_DESCRIPTION")
        val eventLink = intent.getStringExtra("EXTRA_LINK")


        binding.textTitleDetail.text = eventTitle
        binding.textOwnerNameDetail.text = "$eventOwnerName"
        binding.textTimeDetail.text = "$eventBeginTime"
        binding.textQuotaDetail.text = "Kuota : ${eventQuota - eventRegistrant}"


        if (eventDescription != null) {
            binding.textDescriptionDetail.text = Html.fromHtml(eventDescription, Html.FROM_HTML_MODE_LEGACY)
        }


        Linkify.addLinks(binding.textDescriptionDetail, Linkify.WEB_URLS)
        binding.textDescriptionDetail.movementMethod = android.text.method.LinkMovementMethod.getInstance()


        Glide.with(this)
            .load(eventImage)
            .into(binding.imageEventDetail)


        binding.buttonRegister.setOnClickListener {
            eventLink?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
    }
}

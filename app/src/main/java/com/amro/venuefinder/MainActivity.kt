package com.amro.venuefinder

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.amro.venuefinder.ui.list.VenueListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container_view, VenueListFragment())
                .commit()
        }
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun enableBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun disableBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
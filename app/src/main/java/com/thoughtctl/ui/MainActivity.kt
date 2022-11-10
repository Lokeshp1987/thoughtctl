package com.thoughtctl.ui

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thoughtctl.R
import com.thoughtctl.adapter.ImagesAdapter
import com.thoughtctl.model.GaleryArray
import com.thoughtctl.repository.AppRepository
import com.thoughtctl.util.Resource
import com.thoughtctl.util.errorSnack
import com.thoughtctl.viewmodel.MainViewModel
import com.thoughtctl.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var imagesAdapter: ImagesAdapter
    var gridVisible: Boolean = true
    lateinit var galeryArray: GaleryArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        rvPics.setHasFixedSize(true)
        rvPics.layoutManager = LinearLayoutManager(this)
        imagesAdapter = ImagesAdapter()
        rvPics.setLayoutManager(GridLayoutManager(this, 2))
        rvPics.adapter = imagesAdapter
        tvChangeView.setOnClickListener {
            if(this::galeryArray.isInitialized)
                changeLayout(galeryArray)
            else
                Toast.makeText(this, resources.getString(R.string.notthing_to_show), Toast.LENGTH_SHORT).show()
        }
        setupViewModel()
        getPictures()
        idSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //it will return callback when user click on search icon in keyboard
                getPics(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.

                return false
            }
        })
    }

    private fun setupViewModel() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

    }

    private fun getPictures() {
        viewModel.issuesData.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { picsResponse ->
                        galeryArray = picsResponse.data!!
                        imagesAdapter.differ.submitList(picsResponse.data!!)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        rootLayout.errorSnack(message, Snackbar.LENGTH_LONG)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun getPics(query: String) {
        if (query.isNullOrEmpty()) {

            Toast.makeText(this, resources.getString(R.string.write_something), Toast.LENGTH_SHORT)
                .show()
            return
        }
        viewModel.setSearchValue(query)
        viewModel.getIssues()
    }

    private fun changeLayout(galeryArray: GaleryArray) {
        if (galeryArray.isNullOrEmpty()) {
            Toast.makeText(this, resources.getString(R.string.notthing_to_show), Toast.LENGTH_SHORT)
                .show()
            return

        } else {
            if (gridVisible) {
                gridVisible = false
                rvPics.setLayoutManager(GridLayoutManager(this, 1))
            } else {
                gridVisible = true
                rvPics.setLayoutManager(GridLayoutManager(this, 2))
            }
        }
    }

    private fun hideProgressBar() {
        progress.visibility = View.GONE
    }

    private fun showProgressBar() {
        progress.visibility = View.VISIBLE
    }


    fun onProgressClick(view: View) {
        //Preventing Click during loading
    }
}
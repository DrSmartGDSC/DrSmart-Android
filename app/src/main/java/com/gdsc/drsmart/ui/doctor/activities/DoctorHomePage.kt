package com.gdsc.drsmart.ui.doctor.activities

import PaginationScrollListener
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.network.RetrofitService
import com.gdsc.drsmart.tools.storage.AppReferences
import com.gdsc.drsmart.ui.doctor.adapter.QuestionAdapter
import com.gdsc.drsmart.ui.doctor.repo.PostRepository
import com.gdsc.drsmart.ui.doctor.viewModels.PostsViewModel
import com.gdsc.drsmart.ui.doctor.viewModels.factory.PostsViewModelFactory
import com.gdsc.drsmart.ui.register.activities.ChooseRegistrationActivity
import kotlinx.android.synthetic.main.activity_doctor_home_page.*
import kotlinx.android.synthetic.main.base_toolbar.*


lateinit var viewModel: PostsViewModel
lateinit var postsAdapter: QuestionAdapter
private val retrofitService = RetrofitService.getInstance()
var pageNum = 1
var isLastPage: Boolean = false
var isLoading: Boolean = false

class DoctorHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_home_page)

        viewModel = ViewModelProvider(
            this, PostsViewModelFactory(
                PostRepository(
                    retrofitService
                )
            )
        )[PostsViewModel::class.java]

        initToolbar()
        initAdapter()
        getResponse()
    }


    private fun initToolbar() {
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.questions)
    }

    override fun onPause() {
        super.onPause()
        pageNum = 1
        isLastPage = false
        isLoading = false
    }

    override fun onResume() {
        super.onResume()
        pageNum = 1
        isLastPage = false
        isLoading = false
        getPosts()
    }

    private fun initAdapter() {
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.addOnScrollListener(object :
            PaginationScrollListener(recycleView.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                ++pageNum
                getPosts()
            }
        })
    }

    private fun getPosts() {
        viewModel.getPosts(
            this, AppReferences.getToken(this),
            pageNum, 10, loading
        )
    }

    private fun getResponse() {
        viewModel.postsResponse.observe(this) {
            if (it.data.posts.isNotEmpty()) {
                recycleView.visibility = View.VISIBLE
                if (pageNum == 1) {
                    postsAdapter = QuestionAdapter(this, it.data.posts, false)
                    recycleView.adapter = postsAdapter
                } else {
                    isLoading = false
                    postsAdapter.addData(it.data.posts)
                }
                noPostsView.visibility = View.GONE
            } else if (it.data.posts.isEmpty() && pageNum == 1) {
                recycleView.visibility = View.GONE
                noPostsView.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                val i = Intent(this, ChooseRegistrationActivity::class.java)
                i.putExtra("login_state", true)
                startActivity(i)
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
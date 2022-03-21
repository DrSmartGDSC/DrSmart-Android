package com.gdsc.drsmart.ui.doctor.activities

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
        getPosts()
        getResponse()
    }


    private fun initToolbar() {
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.questions)
    }

    override fun onResume() {
        super.onResume()
        getPosts()
    }

    private fun initAdapter() {
        recycleView.layoutManager = LinearLayoutManager(this)
    }

    private fun getPosts() {
        viewModel.getPosts(
            this, AppReferences.getToken(this),
            1, 10000, loading
        )
        //TODO(Add pagination)
    }

    private fun getResponse() {
        viewModel.postsResponse.observe(this) {
            if (it.data.posts.isNotEmpty()) {
                postsAdapter =
                    QuestionAdapter(this, it, false)
                recycleView.adapter = postsAdapter
                noPostsView.visibility = View.GONE

            } else {
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
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
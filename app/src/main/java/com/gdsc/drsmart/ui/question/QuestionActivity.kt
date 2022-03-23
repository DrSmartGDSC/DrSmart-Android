package com.gdsc.drsmart.ui.question

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.network.RetrofitService
import com.gdsc.drsmart.tools.storage.AppReferences
import com.gdsc.drsmart.tools.utils.Base64Utils
import com.gdsc.drsmart.ui.doctor.models.posts.Post
import com.gdsc.drsmart.ui.question.adapter.CommentsAdapter
import com.gdsc.drsmart.ui.question.repo.CommentRepository
import com.gdsc.drsmart.ui.question.viewModel.CommentsViewModel
import com.gdsc.drsmart.ui.question.viewModel.factory.CommentViewModelFactory
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.base_toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

var selectedImagePath: String = "null"
var isUploadImage: Boolean = false
var isUser: Boolean = false

class QuestionActivity : AppCompatActivity() {
    lateinit var viewModel: CommentsViewModel
    private lateinit var commentsAdapter: CommentsAdapter
    private val retrofitService = RetrofitService.getInstance()
    lateinit var questionData: Post
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        initView()
        initViewModel()
        initAdapter()
        getComments()
        getResponse()
        createComment()
        chooseImage()
        endPost()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, CommentViewModelFactory(
                CommentRepository(
                    retrofitService
                )
            )
        )[CommentsViewModel::class.java]
    }

    private fun initView() {
        questionData = intent.getSerializableExtra("question") as Post
        isUser = intent.getBooleanExtra("isUser", false)
        Log.e("isUser", "${isUser} || is answered ${questionData.answered}")
        if (isUser) {
            if (questionData.answered) {
                endPost.visibility = View.VISIBLE
            } else {
                endPost.visibility = View.GONE
            }
            endPost.isEnabled = false
        }
        Log.d("QuestionID", questionData.post_id.toString())
        initToolbar()
        descTxtView.text = questionData.desc
        fieldTxt.text = questionData.field
        userNameTxt.text = questionData.user_name
        if (questionData.img != null) {
            postImg.visibility = View.VISIBLE
            postImg.setImageBitmap(Base64Utils.decodeToBitmap(questionData.img))
        }
    }

    private fun chooseImage() {
        chooseImage.setOnClickListener {
            cropImage.launch(
                options {
                    setGuidelines(CropImageView.Guidelines.ON)
                }
            )
        }
    }


    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(this)
            selectedImagePath = uriFilePath.toString()
            Log.e("image_path_uri", selectedImagePath)
            chooseImage.setImageURI(uriContent)
            chooseImage.isEnabled = false
            isUploadImage = true
            deleteImage.visibility = View.VISIBLE
            deleteImage.setOnClickListener {
                initUploadImage()
            }
        } else {
            result.error
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(true);
        supportActionBar!!.title = questionData.desc
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
            supportActionBar?.setDisplayShowHomeEnabled(true);
        }
    }

    private fun endPostDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.solved_question))
        builder.setMessage(getString(R.string.solved_question_desc))
        builder.setPositiveButton(
            getString(R.string.yes)
        ) { _, _ ->
            viewModel.endPost(
                this,
                AppReferences.getToken(this), questionData.post_id, progress
            )
        }

        builder.setNegativeButton(
            getString(R.string.no)
        ) { dialog, _ ->
            dialog.dismiss()
        }

        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun endPost() {
        endPost.setOnClickListener {
            endPostDialog()
        }
        viewModel.endPostResponse.observe(this) {
            if (it.status) {
                Toast.makeText(this, "Thanks dr !", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, R.string.server_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAdapter() {
        commentsRecycleView.layoutManager = LinearLayoutManager(this)
    }

    private fun getComments() {
        viewModel.getComments(
            this, AppReferences.getToken(this),
            questionData.post_id, progress
        )
    }

    private fun getResponse() {
        viewModel.commentResponse.observe(this) {
            if (it.data.comments.isNotEmpty()) {
                commentsAdapter =
                    CommentsAdapter(this, it.data)
                commentsRecycleView.adapter = commentsAdapter
                noCommentView.visibility = View.GONE
            } else {
                noCommentView.visibility = View.VISIBLE
            }

        }
    }

    private fun createComment() {
        sendComment.setOnClickListener {
            Log.e("image_path", selectedImagePath)
            if (commentEditText.text.toString().trim().isNotEmpty()) {
                val text = RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    commentEditText.text.toString().trim()
                )
                if (!isUploadImage) {
                    viewModel.createComment(
                        this, AppReferences.getToken(this),
                        questionData.post_id, commentEditText.text.toString().trim(), progress
                    )
                } else {
                    val file = File(selectedImagePath)
                    val requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file)
                    val img = MultipartBody.Part.createFormData("img", file.name, requestFile)
                    viewModel.createCommentWithPhoto(
                        this, AppReferences.getToken(this),
                        questionData.post_id, img, text, progress
                    )
                }
            }
        }
        viewModel.createCommentResponse.observe(this) {
            if (it.status) {
                commentEditText.text.clear()
                getComments()
                initUploadImage()
            }
        }
    }

    private fun initUploadImage() {
        selectedImagePath = "null"
        isUploadImage = false
        deleteImage.visibility = View.GONE
        chooseImage.setImageResource(R.drawable.ic_upload_image)
        chooseImage.isEnabled = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
package com.gdsc.drsmart.ui.home.fragments

import PaginationScrollListener
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.network.RetrofitService
import com.gdsc.drsmart.tools.storage.AppReferences
import com.gdsc.drsmart.tools.utils.CircularTextView
import com.gdsc.drsmart.ui.doctor.adapter.QuestionAdapter
import com.gdsc.drsmart.ui.doctor.repo.PostRepository
import com.gdsc.drsmart.ui.doctor.viewModels.PostsViewModel
import com.gdsc.drsmart.ui.doctor.viewModels.factory.PostsViewModelFactory
import com.gdsc.drsmart.ui.register.repo.RegisterRepository
import com.gdsc.drsmart.ui.register.viewModels.SignUpViewModel
import com.gdsc.drsmart.ui.register.viewModels.factory.SignUpViewModelFactory
import kotlinx.android.synthetic.main.activity_sign_up.fieldsSpinner
import kotlinx.android.synthetic.main.ask_question_dialog.*
import kotlinx.android.synthetic.main.fragment_questions.*
import kotlinx.android.synthetic.main.fragment_questions.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


var field_id: Int = 0
var imagePath: String = "null"
var isUploadImage: Boolean = false
lateinit var myView: View
var pageNum = 1
var isLastPage: Boolean = false
var isLoading: Boolean = false

class UserQuestionsFragment : Fragment() {
    private lateinit var dialog: Dialog
    lateinit var viewModel: PostsViewModel
    lateinit var signUpViewModel: SignUpViewModel
    lateinit var postsAdapter: QuestionAdapter
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View? = inflater.inflate(R.layout.fragment_questions, container, false)
        myView = view!!

        viewModel = ViewModelProvider(
            this, PostsViewModelFactory(
                PostRepository(
                    retrofitService
                )
            )
        )[PostsViewModel::class.java]

        signUpViewModel = ViewModelProvider(
            this, SignUpViewModelFactory(
                RegisterRepository(
                    retrofitService
                )
            )
        )[SignUpViewModel::class.java]

        initView(view)
        initAdapter()
        getPosts(view)
        getResponse(view)
        handleScroll()
        return view
    }

    override fun onDetach() {
        super.onDetach()
        initDataLoading()
    }

    override fun onResume() {
        super.onResume()
        initDataLoading()
        myView.noQuestionsView.visibility = View.GONE
    }

    private fun initDataLoading() {
        pageNum = 1
        isLastPage = false
        isLoading = false
    }


    private fun initView(view: View) {
        view.askQuestion.setOnClickListener {
            showDialog(context!!)
        }
    }

    private fun handleScroll() {
        myView.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) myView.askQuestion.hide() else if (dy < 0) myView.askQuestion.show()
            }
        })

    }

    private fun getPosts(view: View) {
        myView.noQuestionsView.visibility = View.GONE
        viewModel.getPosts(
            context!!, AppReferences.getToken(activity),
            pageNum, 10, view.progress
        )
    }

    private fun initAdapter() {
        myView.recycleView.layoutManager = LinearLayoutManager(context)
        myView.recycleView.addOnScrollListener(object :
            PaginationScrollListener(myView.recycleView.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                ++pageNum
                getPosts(myView)
            }
        })
    }

    private fun getResponse(view: View) {
        viewModel.postsResponse.observe(this) {
            if (it.data.posts.isNotEmpty()) {
                if (pageNum == 1) {
                    postsAdapter = QuestionAdapter(context!!, it.data.posts, true)
                    view.recycleView.adapter = postsAdapter
                } else {
                    isLoading = false
                    postsAdapter.addData(it.data.posts)
                }
                view.noQuestionsView.visibility = View.GONE
            } else if (it.data.posts.isEmpty() && pageNum == 1) {
                view.noQuestionsView.visibility = View.VISIBLE
            }
        }
    }

    private fun initSpinner(view: Dialog) {
        signUpViewModel.getFields(context!!, progress)
        val fields: ArrayList<String> = ArrayList()
        fields.add("Select doctor field")
        view.fieldsSpinner.setSelection(0)
        var adapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_dropdown_item, fields
        )
        view.fieldsSpinner.adapter = adapter
        signUpViewModel.fields.observe(this, {
            if (it.status) {
                for (i in it.data.fields) {
                    fields.add(i.name)
                }
                adapter = ArrayAdapter(
                    context!!,
                    android.R.layout.simple_spinner_dropdown_item, fields
                )
                view.fieldsSpinner.adapter = adapter
            }
        })
        view.fieldsSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (position != 0) {
                    field_id = position
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

    }


    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(context!!)
            imagePath = uriFilePath.toString()
            dialog.postImageView.visibility = View.VISIBLE
            dialog.postImageView.setImageURI(uriContent)
            isUploadImage = true
            dialog.cancelSelect.visibility = View.VISIBLE
            dialog.cancelSelect.setOnClickListener {
                initUploadImage()
            }
        } else {
            result.error
        }
    }

    private fun initUploadImage() {
        imagePath = "null"
        isUploadImage = false
        dialog.cancelSelect.visibility = View.GONE
        dialog.postImageView.visibility = View.GONE
    }

    private fun showDialog(context: Context) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val window: Window = dialog.window!!
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.ask_question_dialog)
        initSpinner(dialog)
        dialog.selectImage.setOnClickListener {
            cropImage.launch(
                options {
                    setGuidelines(CropImageView.Guidelines.ON)
                }
            )
        }
        dialog.cancelSelect.setOnClickListener { initUploadImage() }

        dialog.sendPost.setOnClickListener {
            if (dialog.descEditTxt.text.toString().isNotEmpty() && field_id != 0) {
                if (!isUploadImage) {
                    viewModel.addPost(
                        context,
                        AppReferences.getToken(activity!!),
                        dialog.descEditTxt.text.toString().trim(),
                        field_id, progress
                    )
                } else {
                    val file = File(imagePath)
                    val requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file)
                    val img = MultipartBody.Part.createFormData("img", file.name, requestFile)
                    val desc = RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        dialog.descEditTxt.text.toString().trim()
                    )
                    val fieldId =
                        RequestBody.create(
                            MediaType.parse("multipart/form-data"),
                            field_id.toString()
                        )
                    viewModel.addPostWithPhoto(
                        context,
                        AppReferences.getToken(activity!!), img, desc, fieldId, progress
                    )
                }
                dialog.sendPost.isEnabled = false
                getAddPostResponse()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.please_fill_all_fields),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        initProfileImage()
        dialog.show()
    }

    private fun initProfileImage() {
        val rand = (CircularTextView.colors.indices).random()
        dialog.profileImage.setStrokeWidth(0)
        dialog.profileImage.setSolidColor(CircularTextView.colors[rand])
        dialog.profileImage.setStrokeColor("#000000")
        dialog.profileImage.text = "Ask"
    }

    private fun getAddPostResponse() {
        viewModel.addPostResponse.observe(this) {
            if (it.status) {
                dialog.hide()
                getPosts(myView)
            } else {
                Toast.makeText(context!!, getString(R.string.server_error), Toast.LENGTH_SHORT)
                    .show()
            }
            dialog.sendPost.isEnabled = true
        }
    }


}
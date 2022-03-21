package com.gdsc.drsmart.ui.doctor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.utils.Base64Utils
import com.gdsc.drsmart.ui.doctor.models.posts.PostsResponse
import com.gdsc.drsmart.ui.question.QuestionActivity


class QuestionAdapter(var context: Context, var data: PostsResponse, private val is_user: Boolean) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.userNameTxt)
        var desc: TextView = itemView.findViewById(R.id.descTxtView)
        var field: TextView = itemView.findViewById(R.id.fieldTxt)
        var image: ImageView = itemView.findViewById(R.id.postImg)
        var typeAnswer: TextView = itemView.findViewById(R.id.typeAnswer_editText)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(holder, position)
    }

    @SuppressLint("SetTextI18n")
    private fun bind(holder: ViewHolder, position: Int) {
        holder.desc.text = data.data.posts[position].desc
        holder.field.text = data.data.posts[position].field
        holder.name.text = data.data.posts[position].user_name

        //convert base64 to image
        if (data.data.posts[position].img != null) {
            holder.image.setImageBitmap(Base64Utils.decodeToBitmap(data.data.posts[position].img))
        } else {
            holder.image.visibility = View.GONE
        }

        holder.typeAnswer.setOnClickListener {
            val i = Intent(context, QuestionActivity::class.java)
            i.putExtra("question", data.data.posts[position])
            i.putExtra("isUser", is_user)
            context.startActivity(i)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = data.data.posts.size
}

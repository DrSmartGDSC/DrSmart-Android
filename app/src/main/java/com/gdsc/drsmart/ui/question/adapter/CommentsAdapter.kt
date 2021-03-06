package com.gdsc.drsmart.ui.question.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.storage.AppReferences
import com.gdsc.drsmart.tools.utils.AppTools
import com.gdsc.drsmart.tools.utils.CircularTextView
import com.gdsc.drsmart.ui.doctor.models.comment.Data


class CommentsAdapter(
    var context: Context,
    var data: Data,
    var userId: String,
    var isUser: Boolean
) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.userName)
        var desc: TextView = itemView.findViewById(R.id.commentTxt)
        var image: ImageView = itemView.findViewById(R.id.commentPhoto)
        var profileImage: CircularTextView = itemView.findViewById(R.id.profileImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(holder, position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    private fun bind(holder: ViewHolder, position: Int) {
        holder.desc.text = data.comments[position].text
        holder.name.text = data.comments[position].user_name

        if (data.comments[position].img != null) {
            holder.image.visibility = View.VISIBLE
            AppTools.loadImage(context, holder.image, data.comments[position].img)
        } else {
            holder.image.visibility = View.GONE
        }
        initProfileImage(holder, position)
    }

    private fun initProfileImage(holder: ViewHolder, position: Int) {
//        val rand = (CircularTextView.colors.indices).random()
        holder.profileImage.setStrokeWidth(0)
        // 0 for user 1 for doctor
        if (isUser) {
            if (AppReferences.getUserId(context) == data.comments[position].user_id.toString()) {
                holder.profileImage.setSolidColor(CircularTextView.colors[0])
            } else {
                holder.profileImage.setSolidColor(CircularTextView.colors[1])
            }
        } else {
            if (data.comments[position].user_id.toString() == userId) {
                holder.profileImage.setSolidColor(CircularTextView.colors[0])
            } else {
                holder.profileImage.setSolidColor(CircularTextView.colors[1])
            }
        }
        holder.profileImage.setStrokeColor("#000000")
        holder.profileImage.text = data.comments[position].user_name[0].toString()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = data.comments.size
}

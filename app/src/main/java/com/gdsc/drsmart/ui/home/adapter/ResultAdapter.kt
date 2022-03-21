package com.gdsc.drsmart.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.gdsc.drsmart.R
import com.gdsc.drsmart.ui.home.models.PredictResponse
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlin.math.roundToInt


class ResultAdapter(
    var context: Context,
    var data: PredictResponse, val btnlistener: BtnClickListener
) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var value: TextView = itemView.findViewById(R.id.value)
        var subItem: ConstraintLayout = itemView.findViewById(R.id.subItem)
        var fullDesc: TextView = itemView.findViewById(R.id.fullDetails)
        var progress: CircularProgressBar = itemView.findViewById(R.id.circularProgressBar)
        var moreInfo: Button = itemView.findViewById(R.id.moreInfoBtn)
    }

    companion object {
        var mClickListener: BtnClickListener? = null
    }

    interface BtnClickListener {
        fun onBtnClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_diagnosed, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mClickListener = btnlistener

        bind(holder, position)
        holder.itemView.setOnClickListener {
            val expanded: Boolean = data.data.result[position].isExpanded
            data.data.result[position].isExpanded = (!expanded)
            notifyItemChanged(position)
        }

        holder.moreInfo.setOnClickListener {
            if (mClickListener != null)
                mClickListener?.onBtnClick(data.data.result[position].id)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bind(holder: ViewHolder, position: Int) {
        val expanded: Boolean = data.data.result[position].isExpanded
        holder.subItem.visibility = if (expanded) View.VISIBLE else View.GONE
        holder.name.text = data.data.result[position].name
        holder.fullDesc.text = data.data.result[position].name
        holder.value.text =
            "${data.data.result[position].confidence.roundToInt()} %"
        holder.progress.apply {
            // Set Progress
            progress = data.data.result[position].confidence.toFloat()
        }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = data.data.result.size
}

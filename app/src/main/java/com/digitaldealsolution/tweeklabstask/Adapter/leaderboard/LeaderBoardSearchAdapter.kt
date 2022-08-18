package com.digitaldealsolution.tweeklabstask.Adapter.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.digitaldealsolution.tweeklabstask.R
import com.digitaldealsolution.tweeklabstask.model.AthleteModel
import com.google.android.material.card.MaterialCardView

class LeaderBoardSearchAdapter(
    public var athletesList: ArrayList<AthleteModel>,
    var sortType: String
) : RecyclerView.Adapter<LeaderBoardSearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderBoardSearchAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_leaderboard_search, parent, false)
        return ViewHolder(v)
    }

    var imgsList: ArrayList<Int> = arrayListOf(
        R.drawable.img,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_4,
        R.drawable.img_5,
        R.drawable.img_6,
        R.drawable.img_7,
        R.drawable.img_8,
        R.drawable.img_9
    )


    override fun onBindViewHolder(holder: LeaderBoardSearchAdapter.ViewHolder, position: Int) {
        val athleteModel = athletesList.get(position)
        if (sortType == "score") {
            holder.userScore.text = athleteModel.score.toString()
        } else if (sortType == "run-up") {
            holder.userScore.text = athleteModel.runup.toString()
        } else if (sortType == "jump") {
            holder.userScore.text = athleteModel.jump.toString()
        } else if (sortType == "bfc") {
            holder.userScore.text = athleteModel.bfc.toString()
        } else if (sortType == "ffc") {
            holder.userScore.text = athleteModel.ffc.toString()
        } else if (sortType == "release") {
            holder.userScore.text = athleteModel.release.toString()
        }

        holder.userName.text = athleteModel.name
        holder.profileImg.setImageResource(imgsList.get(athleteModel.id - 1))
    }

    override fun getItemCount(): Int {
        return athletesList.size
    }

    fun update(modelList: ArrayList<AthleteModel>, sortT: String) {
        athletesList = modelList
        sortType = sortT
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var constraintLayout: ConstraintLayout
        var cardView: MaterialCardView
        var profileImg: ImageView
        var userName: TextView
        var userScore: TextView

        init {
            constraintLayout = itemview.findViewById(R.id.card_constraint)
            cardView = itemview.findViewById(R.id.recyclerviewsearch_card)
            profileImg = itemview.findViewById(R.id.profile_image)
            userName = itemview.findViewById(R.id.user_name)
            userScore = itemview.findViewById(R.id.score_leaderboard)
        }
    }
}
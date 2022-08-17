package com.digitaldealsolution.tweeklabstask.Adapter.leaderboard

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.digitaldealsolution.tweeklabstask.R
import com.digitaldealsolution.tweeklabstask.model.AthleteModel
import com.digitaldealsolution.tweeklabstask.ui.leaderboard.LeaderBoardCardViewModel
import com.google.android.material.card.MaterialCardView
import java.util.*
import kotlin.collections.ArrayList

class LeaderboardAdapter(public var athletesList: ArrayList<AthleteModel>, var sortType :String, val userId:Int): RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderboardAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_leaderboard_list, parent, false)
        return ViewHolder(v)
    }
    var userPosition :Int = 0
    var imgsList: ArrayList<Int> = arrayListOf(R.drawable.img,R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,R.drawable.img_4,R.drawable.img_5,R.drawable.img_6,R.drawable.img_7, R.drawable.img_8, R.drawable.img_9)
    override fun onBindViewHolder(holder: LeaderboardAdapter.ViewHolder, position: Int) {
        val athleteModel = athletesList.get(position)
        holder.cardView.strokeWidth = 0
        holder.rankText.setTextColor(Color.BLACK)
        if(position == 0){
            holder.imgViewRank.visibility = View.VISIBLE
            holder.imgViewRank.setImageResource(R.drawable.gold_circle)
            holder.rankText.setTextColor(Color.parseColor("#F5FFEB3B"))
        }
        else if(position == 1){
            holder.imgViewRank.visibility = View.VISIBLE
            holder.imgViewRank.setImageResource(R.drawable.silver_circle)
            holder.rankText.setTextColor(Color.parseColor("#DADCDF"))
        }
        else if(position == 2){
            holder.imgViewRank.visibility = View.VISIBLE
            holder.rankText.setTextColor(Color.parseColor("#F6B290"))
            holder.imgViewRank.setImageResource(R.drawable.bronze_circle)
        }
        else{
            holder.imgViewRank.visibility = View.INVISIBLE
        }
        holder.rankText.setText((position+1).toString())
        if(sortType == "score"){
        holder.userScore.text = athleteModel.score.toString()}
        else if (sortType == "run-up"){
            holder.userScore.text = athleteModel.runup.toString()}
        else if (sortType == "jump"){
            holder.userScore.text = athleteModel.jump.toString()
        }
        else if (sortType == "bfc"){
            holder.userScore.text = athleteModel.bfc.toString()
        }
        else if (sortType == "ffc"){
            holder.userScore.text = athleteModel.ffc.toString()
        }
        else if (sortType == "release"){
            holder.userScore.text = athleteModel.release.toString()
        }
        if(athletesList.get(position).id == userId){
            userPosition = position
            holder.cardView.strokeWidth = 4
            holder.cardView.setStrokeColor(Color.parseColor("#F6644A"))
        }
        holder.userName.text =athleteModel.name
        holder.profileImg.setImageResource(imgsList.get(position%10))
    }

    override fun getItemCount(): Int {
        return athletesList.size
    }
    @JvmName("getUserPosition1")
    fun getUserPosition(): Int {
        return userPosition
    }
    fun update(modelList:ArrayList<AthleteModel>, sortT:String){
        athletesList = modelList
        sortType =sortT
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        var constraintLayout :ConstraintLayout
        var cardView: MaterialCardView
        var imgViewRank: ImageView
        var rankText :TextView
        var profileImg : ImageView
        var userName : TextView
        var userScore :TextView
        init {
            constraintLayout = itemview.findViewById(R.id.card_constraint)
            cardView = itemview.findViewById(R.id.recyclerview_card)
            imgViewRank = itemview.findViewById(R.id.imageView)
            rankText = itemview.findViewById(R.id.leaderboard_rank_txt)
            profileImg = itemview.findViewById(R.id.profile_image)
            userName = itemview.findViewById(R.id.user_name)
            userScore =itemview.findViewById(R.id.score_leaderboard)
        }
    }
}
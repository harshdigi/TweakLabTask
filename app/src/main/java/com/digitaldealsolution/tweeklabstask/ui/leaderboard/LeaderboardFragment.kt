package com.digitaldealsolution.tweeklabstask.ui.leaderboard

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitaldealsolution.tweeklabstask.Adapter.leaderboard.LeaderBoardSearchAdapter
import com.digitaldealsolution.tweeklabstask.Adapter.leaderboard.LeaderboardAdapter
import com.digitaldealsolution.tweeklabstask.R
import com.digitaldealsolution.tweeklabstask.model.AthleteModel
import com.digitaldealsolution.tweeklabstask.model.EventModel
import com.google.android.material.card.MaterialCardView
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*


class LeaderboardFragment : Fragment() {

    companion object {
        fun newInstance() = LeaderboardFragment()
    }


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewOfLayout = inflater.inflate(R.layout.fragment_leaderboard, container, false)
        val recyclerView = viewOfLayout.findViewById<RecyclerView>(R.id.recyclerview)
        val jsonData =
            "{\"organisation\":\"Fast Bowling- ABCD Workshop\",\"athletes\":[{\"id\":\"001\",\"name\":\"Nitik Jain\",\"score\":89,\"runup\":88,\"jump\":90,\"bfc\":85,\"ffc\":91,\"release\":91},{\"id\":\"002\",\"name\":\"Anant Sharma\",\"score\":81,\"runup\":89,\"jump\":76,\"bfc\":83,\"ffc\":77,\"release\":80},{\"id\":\"003\",\"name\":\"Anuj Singh\",\"score\":71,\"runup\":78,\"jump\":68,\"bfc\":69,\"ffc\":69,\"release\":71},{\"id\":\"004\",\"name\":\"Ayush Kushwaha\",\"score\":84,\"runup\":85,\"jump\":81,\"bfc\":87,\"ffc\":84,\"release\":83},{\"id\":\"005\",\"name\":\"Ananya Singh\",\"score\":72,\"runup\":70,\"jump\":78,\"bfc\":70,\"ffc\":73,\"release\":69},{\"id\":\"006\",\"name\":\"Ayush Kushwaha\",\"score\":67,\"runup\":67,\"jump\":67,\"bfc\":67,\"ffc\":67,\"release\":67},{\"id\":\"007\",\"name\":\"Soumyadip Ghorai\",\"score\":77,\"runup\":75,\"jump\":79,\"bfc\":77,\"ffc\":78,\"release\":76},{\"id\":\"008\",\"name\":\"Shwetank Shrey\",\"score\":82,\"runup\":82,\"jump\":82,\"bfc\":81,\"ffc\":85,\"release\":80},{\"id\":\"009\",\"name\":\"Joseph Hermis\",\"score\":68,\"runup\":69,\"jump\":68,\"bfc\":68,\"ffc\":69,\"release\":66},{\"id\":\"010\",\"name\":\"Yash Bhargava\",\"score\":63,\"runup\":60,\"jump\":60,\"bfc\":70,\"ffc\":62,\"release\":63}]}"
        val jsonObject = JSONTokener(jsonData).nextValue() as JSONObject
        val organisation = jsonObject.getString("organisation")
        val athleteArray = jsonObject.getJSONArray("athletes")
        val sortType = "score"
        val userId = 9
        val athleteModelList: ArrayList<AthleteModel> = arrayListOf()
        if (athleteModelList.size <= 10) {
            for (i in 0 until athleteArray.length()) {
                val id = athleteArray.getJSONObject(i).getString("id").toInt()
                val name = athleteArray.getJSONObject(i).getString("name")
                val score = athleteArray.getJSONObject(i).getString("score").toInt()
                val runup = athleteArray.getJSONObject(i).getString("runup").toInt()
                val jump = athleteArray.getJSONObject(i).getString("jump").toInt()
                val bfc = athleteArray.getJSONObject(i).getString("bfc").toInt()
                val ffc = athleteArray.getJSONObject(i).getString("ffc").toInt()
                val release = athleteArray.getJSONObject(i).getString("release").toInt()
                val athleteModel = AthleteModel(id, name, score, runup, jump, bfc, ffc, release)

                athleteModelList.add(athleteModel)

            }
        }

        val EventModel = EventModel(organisation, athleteModelList)
        val eventName = viewOfLayout.findViewById<TextView>(R.id.leaderboard_event_name)
        eventName.text = organisation
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = LeaderboardAdapter(athleteModelList, sortType, userId)

        recyclerView.adapter = adapter
        val sortBtn = viewOfLayout.findViewById<ConstraintLayout>(R.id.sort_card)
        val view = layoutInflater.inflate(R.layout.leaderboard_bottomsheet, null)
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        sortBtn.setOnClickListener(View.OnClickListener {
            dialog.setContentView(view)
            dialog.show()

            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setGravity(Gravity.BOTTOM)


        })

        val radioGroup = view.findViewById<RadioGroup>(R.id.sort_radioGroup)
        radioGroup.check(R.id.score)
        val controller: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layoutanimation)
        recyclerView.layoutAnimation = controller
        recyclerView.scheduleLayoutAnimation()

        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            if (R.id.score == checkedId) {

                athleteModelList.sortByDescending { it.score }
                adapter.update(athleteModelList, "score")
                recyclerView.scheduleLayoutAnimation();
                val sorttypetext = viewOfLayout.findViewById<TextView>(R.id.sort_type)
                sorttypetext.text = "Score"
                dialog.dismiss()
            }
            if (R.id.runup == checkedId) {
                athleteModelList.sortByDescending { it.runup }
                adapter.update(athleteModelList, "run-up")
                recyclerView.scheduleLayoutAnimation();
                val sorttypetext = viewOfLayout.findViewById<TextView>(R.id.sort_type)
                sorttypetext.text = "Runup"
                dialog.dismiss()
            }
            if (R.id.jump == checkedId) {
                athleteModelList.sortByDescending { it.jump }
                adapter.update(athleteModelList, "jump")
                recyclerView.scheduleLayoutAnimation();
                val sorttypetext = viewOfLayout.findViewById<TextView>(R.id.sort_type)
                sorttypetext.text = "Jump"
                dialog.dismiss()
            }
            if (R.id.bfc == checkedId) {
                athleteModelList.sortByDescending { it.bfc }
                adapter.update(athleteModelList, "bfc")
                recyclerView.scheduleLayoutAnimation();
                val sorttypetext = viewOfLayout.findViewById<TextView>(R.id.sort_type)
                sorttypetext.text = "BFC"
                dialog.dismiss()
            }
            if (R.id.ffc == checkedId) {
                athleteModelList.sortByDescending { it.ffc }
                adapter.update(athleteModelList, "ffc")
                recyclerView.scheduleLayoutAnimation();
                val sorttypetext = viewOfLayout.findViewById<TextView>(R.id.sort_type)
                sorttypetext.text = "FFC"
                dialog.dismiss()
            }
            if (R.id.release == checkedId) {
                athleteModelList.sortByDescending { it.release }
                adapter.update(athleteModelList, "release")
                recyclerView.scheduleLayoutAnimation();
                val sorttypetext = viewOfLayout.findViewById<TextView>(R.id.sort_type)
                sorttypetext.text = "Release"
                dialog.dismiss()
            }

        }

        val ctaButton = viewOfLayout.findViewById<MaterialCardView>(R.id.cta_btn)
        ctaButton.setOnClickListener(View.OnClickListener {
            val userPosition = adapter.getUserPosition()
            recyclerView.scrollToPosition(userPosition)
        })
        var searchTxt = viewOfLayout.findViewById<EditText>(R.id.editText)
        val searchBtn = viewOfLayout.findViewById<ImageView>(R.id.search)
        val filterList: ArrayList<AthleteModel> = arrayListOf()
        searchBtn.setOnClickListener(View.OnClickListener {
            val dim = viewOfLayout.findViewById<View>(R.id.dim)
            val searchOverlay = viewOfLayout.findViewById<ConstraintLayout>(R.id.search_overlay)
            val searchCloseBtn = viewOfLayout.findViewById<CircleImageView>(R.id.search_close_btn)


            searchOverlay.animate()
                .alpha(1.0f)
                .setDuration(0)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        searchOverlay.visibility = View.VISIBLE
                    }
                })
            searchCloseBtn.animate()
                .alpha(1.0f)
                .setDuration(0)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        searchCloseBtn.visibility = View.VISIBLE
                    }
                })
            dim.animate()
                .alpha(0.7f)
                .setDuration(0)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        dim.visibility = View.VISIBLE
                    }
                })
            searchCloseBtn.setOnClickListener(View.OnClickListener {
                hideKeyboard()
                searchTxt.text.clear()
                filterList.clear()
                searchOverlay.animate()
                    .alpha(0.0f)
                    .setDuration(600)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            searchOverlay.visibility = View.GONE
                        }
                    })
                searchCloseBtn.animate()
                    .alpha(0.0f)
                    .setDuration(600)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            searchCloseBtn.visibility = View.GONE
                        }
                    })
                dim.animate()
                    .alpha(0.0f)
                    .setDuration(600)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            dim.visibility = View.GONE
                        }
                    })

            })
        })


        val searchAdapter = LeaderBoardSearchAdapter(filterList, "score")
        var searchRecyclerView = viewOfLayout.findViewById<RecyclerView>(R.id.search_recyclerview)
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchRecyclerView.adapter = searchAdapter
        searchTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filterList.clear()
                for (i in athleteModelList) {
                    if (i.name.lowercase(Locale.getDefault()).contains(
                            s.toString()
                                .lowercase(Locale.getDefault())
                        ) && s.toString() != ""
                    ) {
                        filterList.add(i)
                    }
                }
                searchAdapter.update(filterList, "score")

            }

        })




        return viewOfLayout
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}
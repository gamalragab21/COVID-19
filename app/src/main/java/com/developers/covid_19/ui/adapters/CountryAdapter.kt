package com.developers.covid_19.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.developers.covid_19.R
import javax.inject.Inject
import com.developers.covid_19.databinding.ItemCountryBinding
import com.developers.covid_19.entities.CovidModelItem
import com.developers.covid_19.utils.Constants
import kotlinx.android.synthetic.main.item_country.view.*


class CountryAdapter @Inject constructor(
    private val glide: RequestManager,
    private val context: Context,
) : RecyclerView.Adapter<CountryAdapter.SavedViewHolder>() {


//    var countries: List<CovidModelItem> =ArrayList<CovidModelItem>()

    //
    var countries: List<CovidModelItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    private val diffCallback = object : DiffUtil.ItemCallback<CovidModelItem>() {
        override fun areContentsTheSame(oldItem: CovidModelItem, newItem: CovidModelItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        //
        override fun areItemsTheSame(oldItem: CovidModelItem, newItem: CovidModelItem): Boolean {
            return oldItem.countryInfo._id == newItem.countryInfo._id
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    inner class SavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryFlag = itemView.countryFlag
        val countryName = itemView.countryName
        val arrow = itemView.arrow
        val totalConfirmed = itemView.totalConfirmed
        val totalDeath = itemView.totalDeath
        val totalRecovered = itemView.totalRecovered
        val newCases = itemView.newCases
        val ln_header = itemView.ln_header
        val deatils = itemView.deatils

        fun bindData(item: CovidModelItem) {
            glide.load(item.countryInfo.flag).into(countryFlag)
            countryName.text = item.country
            updateData(item)
        }

        private fun updateData(item: CovidModelItem) {
            totalConfirmed.text = Constants.format(item.cases.toLong())
            totalDeath.text = Constants.format(item.todayDeaths.toLong())
            totalRecovered.text = Constants.format(item.todayRecovered.toLong())
            newCases.text = Constants.format(item.todayCases.toLong())
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        return SavedViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_country,
                parent,
                false
            )
        )
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {

        val country = countries[position]


        holder.apply {


            bindData(country)


            if (position == 0) {
                arrow.tag = "up"
                arrow.setImageResource(R.drawable.arrow_up)
                deatils.isVisible = true
            }
//
            arrow.setOnClickListener {
                Log.i("GAMALRAGAB", "onBindViewHolder: ${arrow.tag}")
                if (arrow.tag == "down") {
                    arrow.tag = "up"
                    arrow.setImageResource(R.drawable.arrow_up)
                    deatils.isVisible = true
                } else {
                    arrow.tag = "down"
                    arrow.setImageResource(R.drawable.arrow_down)
                    deatils.isVisible = false
                }
            }




        }
    }


    override fun getItemCount(): Int = countries.size

    private var onItemClickListener: ((CovidModelItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (CovidModelItem) -> Unit) {
        onItemClickListener = listener
    }


}
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



class CountryAdapter @Inject constructor(
    private val glide: RequestManager,
    private val context: Context,
) : RecyclerView.Adapter<CountryAdapter.SavedViewHolder>() {


    private lateinit var bindingAdapter: ItemCountryBinding
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


        fun bindData(item: CovidModelItem) {
            glide.load(item.countryInfo.flag).into(bindingAdapter.countryFlag)
            bindingAdapter.countryName.text = item.country
            updateData(item)
        }

    }

    private fun updateData(item: CovidModelItem) {
        bindingAdapter.totalConfirmed.text = Constants.format(item.cases.toLong())
        bindingAdapter.totalDeath.text = Constants.format(item.todayDeaths.toLong())
        bindingAdapter.totalRecovered.text = Constants.format(item.todayRecovered.toLong())
        bindingAdapter.newCases.text = Constants.format(item.todayCases.toLong())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        bindingAdapter =
            ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedViewHolder(
            bindingAdapter.root
        )
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {

        val country = countries[position]


        holder.apply {


            bindData(country)


//            if (position==0){
//                bindingAdapter.arrow.tag="up"
//                bindingAdapter.arrow.setImageResource(R.drawable.arrow_up)
//                bindingAdapter.deatils.isVisible=true
//            }
//
//            bindingAdapter.arrow.setOnClickListener {
//                Log.i("GAMALRAGAB", "onBindViewHolder: ${bindingAdapter.arrow.tag}")
//               if (bindingAdapter.arrow.tag=="down"){
//                   bindingAdapter.arrow.tag="up"
//                   bindingAdapter.arrow.setImageResource(R.drawable.arrow_up)
//                   bindingAdapter.deatils.isVisible=true
//               }else{
//                   bindingAdapter.arrow.tag="down"
//                   bindingAdapter.arrow.setImageResource(R.drawable.arrow_down)
//                   bindingAdapter.deatils.isVisible=false
//               }
//            }


//            bindingAdapter.markSaved.setOnClickListener {
//                onItemMarkerClickListener?.let { click ->
//                    click(job, position,bindingAdapter.markSaved)
//                }
//            }

        }
    }


    override fun getItemCount(): Int = countries.size

    private var onItemClickListener: ((CovidModelItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (CovidModelItem) -> Unit) {
        onItemClickListener = listener
    }


}
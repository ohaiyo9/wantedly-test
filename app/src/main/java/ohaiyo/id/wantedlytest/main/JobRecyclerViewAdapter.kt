package ohaiyo.id.wantedlytest.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_job_item.view.*
import ohaiyo.id.wantedlytest.R
import ohaiyo.id.wantedlytest.main.MainFragment.JobItemListener
import ohaiyo.id.wantedlytest.model.Job

class JobRecyclerViewAdapter(
        private val mValues: MutableList<Job>,
        private val mListener: JobItemListener?)
    : RecyclerView.Adapter<JobRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Job
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onJobClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_job_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mCompanyTV.text = item.company.name
        holder.mJobTitleTV.text = item.looking_for
        holder.mLocationTV.text = item.location

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun setJobList(jobList: List<Job>) {
        mValues.clear()
        mValues.addAll(jobList)
    }

    fun addJobList(jobList: List<Job>) {
        mValues.addAll(jobList)
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mCompanyTV: TextView = mView.tv_company_name
        val mJobTitleTV: TextView = mView.tv_job_title
        val mLocationTV: TextView = mView.tv_location

        override fun toString(): String {
            return super.toString() + " '" + mCompanyTV.text + " - " + mJobTitleTV.text +"' "
        }
    }
}

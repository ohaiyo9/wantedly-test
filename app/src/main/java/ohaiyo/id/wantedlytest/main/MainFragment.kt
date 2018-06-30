package ohaiyo.id.wantedlytest.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*
import ohaiyo.id.wantedlytest.R
import ohaiyo.id.wantedlytest.model.Job
import ohaiyo.id.wantedlytest.model.Response

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [MainFragment.JobItemListener] interface.
 */
class MainFragment : Fragment(), MainContract.View {

    override lateinit var presenter: MainContract.Presenter

    private var itemListener: JobItemListener = object : JobItemListener {
        override fun onJobClick(clickedJob: Job) {
            presenter.openJobDetails(clickedJob)
        }
    }

    private val listAdapter = JobRecyclerViewAdapter(ArrayList(0), itemListener)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        with(root) {
            findViewById<Button>(R.id.btn_search).also {
                it.setOnClickListener {
                    showProgress()
                    presenter.getJobListing(et_query.text.toString(), 1)
                }
            }

            findViewById<RecyclerView>(R.id.list).also {
                it.layoutManager = LinearLayoutManager(context)
                it.adapter = listAdapter
            }
        }

        return root
    }

    override fun showError(stringResId: Int) {
        Toast.makeText(context, stringResId, Toast.LENGTH_LONG).show()
        hideProgress()
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        hideProgress()
    }

    override fun showMessage(srtResId: Int) {
        hideProgress()
        Toast.makeText(context, srtResId, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        hideProgress()
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showJobList(jobList:Response) {
        hideProgress()
        listAdapter.setJobList(jobList.data)
        listAdapter.notifyDataSetChanged()
    }

    override fun showJobDetails(requestedJob: Job) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface JobItemListener {
        fun onJobClick(clickedJob: Job)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}

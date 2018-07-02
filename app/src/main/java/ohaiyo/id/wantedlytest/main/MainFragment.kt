package ohaiyo.id.wantedlytest.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.fragment_main.*
import ohaiyo.id.wantedlytest.R
import ohaiyo.id.wantedlytest.jobdetail.JobDetailActivity
import ohaiyo.id.wantedlytest.model.Job
import ohaiyo.id.wantedlytest.model.Response

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [MainFragment.JobItemListener] interface.
 */
class MainFragment : Fragment(), MainContract.View {

    override lateinit var presenter: MainContract.Presenter

    private var mQuery: String = ""
    private var mPage = 1
    private var lastResponse: Response? = null

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
            findViewById<EditText>(R.id.et_query).apply {
                setOnEditorActionListener { v, actionId, event ->
                    var handled = false
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        btn_search.performClick()
                        handled = true
                    }
                    return@setOnEditorActionListener handled
                }
            }

            findViewById<Button>(R.id.btn_search).also {
                it.setOnClickListener {
                    showProgress()
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(windowToken, 0)

                    if (!TextUtils.equals(mQuery, et_query.text.toString())) {
                        mPage = 1
                    }
                    mQuery = et_query.text.toString()
                    presenter.getJobListing(mQuery, mPage)
                }
            }

            findViewById<RecyclerView>(R.id.list).also {
                it.layoutManager = LinearLayoutManager(context)
                it.adapter = listAdapter

                val dividerItemDecoration = DividerItemDecoration(it.context, LinearLayoutManager.VERTICAL)
                it.addItemDecoration(dividerItemDecoration)
            }

            findViewById<ImageView>(R.id.btn_first).also {
                it.visibility = View.GONE
                it.setOnClickListener {
                    showProgress()
                    mPage = 1
                    presenter.getJobListing(mQuery, mPage)
                }
            }

            findViewById<ImageView>(R.id.btn_prev).also {
                it.visibility = View.GONE
                it.setOnClickListener {
                    if (mPage > 1)
                    showProgress()
                    presenter.getJobListing(mQuery, --mPage)
                }
            }

            findViewById<ImageView>(R.id.btn_next).also {
                it.visibility = View.GONE
                it.setOnClickListener {
                    val totalPage = lastResponse?._metadata?.total_pages ?: 0
                    if (mPage < totalPage) {
                        showProgress()
                        presenter.getJobListing(mQuery, ++mPage)
                    }
                }
            }

            findViewById<ImageView>(R.id.btn_last).also {
                it.visibility = View.GONE
                it.setOnClickListener {
                    val totalPage = lastResponse?._metadata?.total_pages ?: 0
                    showProgress()
                    mPage = totalPage
                    presenter.getJobListing(mQuery, totalPage)
                }
            }

            findViewById<TextView>(R.id.tv_page_counter).visibility = View.GONE
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
        listAdapter.setJobList(jobList.data)
        listAdapter.notifyDataSetChanged()
        list.scrollToPosition(0)

        if (mPage == 1) {
            btn_first.visibility = View.GONE
            btn_prev.visibility = View.GONE
        } else {
            btn_first.visibility = View.VISIBLE
            btn_prev.visibility = View.VISIBLE
        }

        if (mPage == jobList._metadata.total_pages) {
            btn_next.visibility = View.GONE
            btn_last.visibility = View.GONE
        } else {
            btn_next.visibility = View.VISIBLE
            btn_last.visibility = View.VISIBLE
        }

        tv_page_counter.visibility = View.VISIBLE
        tv_page_counter.text = getString(R.string.page_counter, mPage, jobList._metadata.total_pages)

        lastResponse = jobList

        hideProgress()
    }

    override fun showJobDetails(requestedJob: Job) {
        startActivity(JobDetailActivity.newIntent(context, requestedJob))
        activity?.overridePendingTransition(R.anim.rtl_enter, R.anim.rtl_exit)
    }

    private fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    interface JobItemListener {
        fun onJobClick(clickedJob: Job)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}

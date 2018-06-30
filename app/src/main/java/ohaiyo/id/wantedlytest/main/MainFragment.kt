package ohaiyo.id.wantedlytest.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*
import ohaiyo.id.wantedlytest.R
import ohaiyo.id.wantedlytest.main.dummy.DummyContent
import ohaiyo.id.wantedlytest.main.dummy.DummyContent.DummyItem
import ohaiyo.id.wantedlytest.model.Response

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [MainFragment.OnListFragmentInteractionListener] interface.
 */
class MainFragment : Fragment(), MainContract.View {

    override lateinit var presenter: MainContract.Presenter


    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = JobRecyclerViewAdapter(DummyContent.ITEMS, listener)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_search.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            presenter.getJobListing(et_query.text.toString(), 1)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun showError(stringResId: Int) {
        Toast.makeText(context, stringResId, Toast.LENGTH_LONG).show()
        progress_bar.visibility = View.GONE
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        progress_bar.visibility = View.GONE
    }

    override fun showMessage(srtResId: Int) {
        progress_bar.visibility = View.GONE
        Toast.makeText(context, srtResId, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showJobList(jobList:Response) {
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
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}

package ohaiyo.id.wantedlytest.main

import ohaiyo.id.wantedlytest.BasePresenter
import ohaiyo.id.wantedlytest.BaseView
import ohaiyo.id.wantedlytest.model.Response

interface MainContract {

    interface View : BaseView<Presenter> {
        fun showJobList(jobList: Response)
    }

    interface Presenter : BasePresenter {
        fun getJobListing(query: String, page: Int)
    }
}
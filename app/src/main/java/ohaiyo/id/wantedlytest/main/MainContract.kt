package ohaiyo.id.wantedlytest.main

import ohaiyo.id.wantedlytest.BasePresenter
import ohaiyo.id.wantedlytest.BaseView
import ohaiyo.id.wantedlytest.model.Job
import ohaiyo.id.wantedlytest.model.Response

interface MainContract {

    interface View : BaseView<Presenter> {
        fun showJobList(jobList: Response)

        fun showJobDetails(requestedJob: Job)
    }

    interface Presenter : BasePresenter {
        fun getJobListing(query: String, page: Int)

        fun openJobDetails(requestedJob: Job)
    }
}
package ohaiyo.id.wantedlytest.jobdetail

import ohaiyo.id.wantedlytest.BasePresenter
import ohaiyo.id.wantedlytest.BaseView

interface JobDetailContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {
        fun apply(jobId: Long)
    }
}
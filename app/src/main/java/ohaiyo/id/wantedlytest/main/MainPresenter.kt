package ohaiyo.id.wantedlytest.main

import ohaiyo.id.wantedlytest.api.ApiManager
import ohaiyo.id.wantedlytest.api.ErrorHandler
import ohaiyo.id.wantedlytest.model.Job
import rx.functions.Action1

class MainPresenter(val mainView: MainContract.View) : MainContract.Presenter {

    init {
        mainView.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getJobListing(query: String, page: Int) {
        ApiManager.getJobListing(query, page)
                .doOnError { mainView.showMessage(it.toString()) }
                .subscribe(Action1 { mainView.showJobList(it) },
                        ErrorHandler(mainView, true, { throwable, errorBody, isNetwork -> mainView.showError(throwable.message) }))
    }

    override fun openJobDetails(requestedJob: Job) {
        mainView.showJobDetails(requestedJob)
    }
}
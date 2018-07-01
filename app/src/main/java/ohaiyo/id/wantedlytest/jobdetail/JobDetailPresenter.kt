package ohaiyo.id.wantedlytest.jobdetail

class JobDetailPresenter(val detailView: JobDetailContract.View): JobDetailContract.Presenter {

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
        detailView.presenter = this
    }

    override fun apply(jobId: Long) {
        // Apply job implementation
    }
}
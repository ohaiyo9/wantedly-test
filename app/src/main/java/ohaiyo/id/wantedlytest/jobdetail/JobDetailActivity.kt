package ohaiyo.id.wantedlytest.jobdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_job_detail.*
import ohaiyo.id.wantedlytest.R
import ohaiyo.id.wantedlytest.model.Job

class JobDetailActivity : AppCompatActivity(), JobDetailContract.View {

    override lateinit var presenter: JobDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        presenter = JobDetailPresenter(this)

        tv_job_title.text = intent.getStringExtra(TITLE)
        tv_looking_for.text = intent.getStringExtra(LOOKING_FOR)
        tv_company_name.text = intent.getStringExtra(COMPANY_NAME)
        tv_location.text = intent.getStringExtra(LOCATION)
        tv_description.text = intent.getStringExtra(DESCRIPTION)

        val imgUrl = intent.getStringExtra(IMG_URL)
        Picasso.get().load(imgUrl).into(iv_job)

        btn_apply.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this).create().apply {
                title = getString(R.string.apply)
                setMessage(getString(R.string.apply_confirmation))
                setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), { _, _ ->
                    dismiss()
                })
                setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.apply), {_, _ ->
                    presenter.apply(intent.getLongExtra(JOB_ID, 0))
                    dismiss()
                })
            }
            alertDialog.show()

        }
    }

    override fun showError(stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show()
    }

    override fun showError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(srtResId: Int) {
        Toast.makeText(this, srtResId, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val JOB_ID = "job_id"
        private const val TITLE = "title"
        private const val COMPANY_NAME = "company_name"
        private const val LOCATION = "location"
        private const val LOOKING_FOR = "looking_for"
        private const val DESCRIPTION = "description"
        private const val IMG_URL = "img_url"

        fun newIntent(context: Context?, job: Job): Intent =
                Intent(context, JobDetailActivity::class.java).apply {
                    putExtra(JOB_ID, job.id)
                    putExtra(TITLE, job.title)
                    putExtra(COMPANY_NAME, job.company.name)
                    putExtra(LOCATION, job.location)
                    putExtra(LOOKING_FOR, job.looking_for)
                    putExtra(DESCRIPTION, job.description)
                    putExtra(IMG_URL, job.image.i_320_131)
                }
    }
}

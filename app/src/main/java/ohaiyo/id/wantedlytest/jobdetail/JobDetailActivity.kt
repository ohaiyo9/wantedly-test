package ohaiyo.id.wantedlytest.jobdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_job_detail.*
import ohaiyo.id.wantedlytest.R
import ohaiyo.id.wantedlytest.model.Job

class JobDetailActivity : AppCompatActivity(), JobDetailContract.View {

    override lateinit var presenter: JobDetailContract.Presenter

    private lateinit var mJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = JobDetailPresenter(this)

        mJob = intent.getParcelableExtra(JOB)

        title = mJob.looking_for

        setupViews()
    }

    private fun setupViews() {
        tv_job_title.text = mJob.title
        tv_looking_for.text = mJob.looking_for
        tv_company_name.text = mJob.company.name
        tv_url.text = mJob.company.url
        tv_location.text = getString(R.string.concat_strings, mJob.location, mJob.location_suffix)
        tv_description.text = mJob.description

        val imgUrl = mJob.image.i_320_131
        Picasso.get().load(imgUrl).into(iv_job)

        btn_apply.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this).create().apply {
                title = getString(R.string.apply)
                setMessage(getString(R.string.apply_confirmation))
                setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), { _, _ ->
                    dismiss()
                })
                setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.apply), {_, _ ->
                    presenter.apply(mJob.id)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.ltr_enter, R.anim.ltr_exit)
    }

    companion object {
        private const val JOB = "job"

        fun newIntent(context: Context?, job: Job): Intent =
                Intent(context, JobDetailActivity::class.java).apply {
                    putExtra(JOB, job)
                }
    }
}

package ohaiyo.id.wantedlytest.jobdetail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_job_detail.*
import ohaiyo.id.wantedlytest.R
import ohaiyo.id.wantedlytest.model.Job

class JobDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        tv_job_title.text = intent.getStringExtra(TITLE)
        tv_looking_for.text = intent.getStringExtra(LOOKING_FOR)
        tv_company_name.text = intent.getStringExtra(COMPANY_NAME)
        tv_location.text = intent.getStringExtra(LOCATION)
        tv_description.text = intent.getStringExtra(DESCRIPTION)
        val imgUrl = intent.getStringExtra(IMG_URL)

        Picasso.get().load(imgUrl).into(iv_job)
    }

    companion object {
        private const val TITLE = "title"
        private const val COMPANY_NAME = "company_name"
        private const val LOCATION = "location"
        private const val LOOKING_FOR = "looking_for"
        private const val DESCRIPTION = "description"
        private const val IMG_URL = "img_url"

        fun newIntent(context: Context?, job: Job): Intent =
                Intent(context, JobDetailActivity::class.java).apply {
                    putExtra(TITLE, job.title)
                    putExtra(COMPANY_NAME, job.company.name)
                    putExtra(LOCATION, job.location)
                    putExtra(LOOKING_FOR, job.looking_for)
                    putExtra(DESCRIPTION, job.description)
                    putExtra(IMG_URL, job.image.i_320_131)
                }
    }
}

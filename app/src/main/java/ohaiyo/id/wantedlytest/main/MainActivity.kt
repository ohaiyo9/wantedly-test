package ohaiyo.id.wantedlytest.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ohaiyo.id.wantedlytest.R

class MainActivity : AppCompatActivity() {

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as MainFragment? ?: MainFragment.newInstance().also {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.contentFrame, it)
            }.commit()
        }

        mainPresenter = MainPresenter(mainFragment)
    }
}

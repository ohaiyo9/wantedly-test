package ohaiyo.id.wantedlytest

import android.support.annotation.StringRes

interface BaseView<T> {

    var presenter: T

    fun showError(@StringRes stringResId: Int)

    fun showError(message: String?)

    fun showMessage(@StringRes srtResId: Int)

    fun showMessage(message: String)
}
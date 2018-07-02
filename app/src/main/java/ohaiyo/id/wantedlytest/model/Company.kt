package ohaiyo.id.wantedlytest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(val id: Long,
                   val name: String,
                   val url: String) : Parcelable
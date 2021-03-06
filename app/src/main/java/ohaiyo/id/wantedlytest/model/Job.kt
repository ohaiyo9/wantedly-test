package ohaiyo.id.wantedlytest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Job(val id: Long,
               val title: String,
               val location: String,
               val location_suffix: String,
               val description: String,
               val looking_for: String,
               val image: Image,
               val company: Company) : Parcelable
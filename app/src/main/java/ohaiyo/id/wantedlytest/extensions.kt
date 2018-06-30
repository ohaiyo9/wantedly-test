package ohaiyo.id.wantedlytest

import com.squareup.moshi.Moshi

inline fun <reified T> Moshi.fromJson(json: String): T = this.adapter(T::class.java).fromJson(json)
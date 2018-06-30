package ohaiyo.id.wantedlytest.api

import ohaiyo.id.wantedlytest.model.Job
import ohaiyo.id.wantedlytest.model.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface WantedlyService {

    @GET("projects")
    fun getJobListing(@Query("q") query: String,
                      @Query("p") page: Int): Observable<Response>
}
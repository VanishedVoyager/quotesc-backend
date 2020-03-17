package com.silverwyrm.statistics.boundary

import com.silverwyrm.statistics.control.StatisticsService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

//@Path("statistics")
@Produces(MediaType.APPLICATION_JSON)
open class StatisticsEndpoint {

    @Inject
    open lateinit var statisticsService: StatisticsService

    @GET
    @Path("/toppeople")
    open fun mostQuotedPeople(): Response{
        val list = statisticsService.getTopPeople()
        return Response.ok(list).build();
    }
}
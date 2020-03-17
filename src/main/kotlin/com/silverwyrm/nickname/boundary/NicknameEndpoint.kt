package com.silverwyrm.nickname.boundary

import com.silverwyrm.nickname.control.NicknameDao
import com.silverwyrm.nickname.entity.Nickname
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

//@Path("nickname")
//@Produces(MediaType.APPLICATION_JSON)
//class NicknameEndpoint {
//
//    @Inject
//    lateinit var nicknameDao: NicknameDao
//
//    @GET
//    open fun getByUser(@QueryParam("user") userId: Long?): Response {
//        if(userId == null){
//            val list = nicknameDao.findAll().list<Nickname>()
//            return Response.ok(list).build()
//        }
//        val list = nicknameDao.findByPersonId(userId);
//        return Response.ok(list).build()
//    }
//}
package com.github.sawied.boxer.api.rest

import com.github.sawied.boxer.api.rest.domain.ApplicationInfo
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.InetAddress

/**
 * code from oneport chinasoft team.
 */
const val APPLICATION_RESOURCE_URI = "/api/v1/boxer"

@RestController
@RequestMapping(APPLICATION_RESOURCE_URI)
class ApplicationApi {

    @RequestMapping
    fun getApplicationRuntime(): ApplicationInfo {
        val inetAddress = InetAddress.getLocalHost();
        return ApplicationInfo("boxer",inetAddress.hostName + "/" +inetAddress.hostAddress);
    }
}
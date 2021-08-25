package com.shaadi.smatch.model

data class ResponseSBody(
    val info: Info?,
    val results: List<PeopleBody?>?
) {
    data class Info(
        val page: Int?,
        val results: Int?,
        val seed: String?,
        val version: String?
    )
}
package com.example.skysky.data

/**
 * Состояние экрана
 */
@Suppress("DataClassPrivateConstructor")
data class ScreenState private constructor(
    val status: Status,
    val message: String? = null
) {

    enum class Status {
        RUNNING,
        SUCCESS_LOADED,
        NOT_SEARCH,
        EMPTY_SEARCH,
        FAILED
    }

    companion object {

        val LOADED = ScreenState(Status.SUCCESS_LOADED)
        val LOADING = ScreenState(Status.RUNNING)
        val NOT_SEARCH = ScreenState(Status.NOT_SEARCH)
        val EMPTY_SEARCH = ScreenState(Status.EMPTY_SEARCH)
        fun error(msg: String?) = ScreenState(Status.FAILED, msg)
    }
}
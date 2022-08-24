package jp.co.yumemi.android.code_check.common

enum class State {
    LOADING,
    SUCCESS,
    ERROR
}

class Resource<out T>(val state: State, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?) = Resource(State.SUCCESS, data, null)
        fun <T> error(msg: String?, data: T?) = Resource(State.ERROR, data, msg)
        fun loading() = Resource(State.LOADING, null, null)
    }
}
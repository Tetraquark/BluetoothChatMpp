package ru.tetraquark.mpp.bluetooth

sealed class GattResult<out T> {

    data class Success<out T> internal constructor(val data: T): GattResult<T>()
    data class Failure internal constructor(val status: Int): GattResult<Nothing>()

    companion object {
        fun <T> success(value: T) = Success(value)

        fun failure(status: Int) = Failure(status)
    }

}

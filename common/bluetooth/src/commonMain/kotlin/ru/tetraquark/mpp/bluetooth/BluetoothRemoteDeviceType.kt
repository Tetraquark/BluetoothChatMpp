package ru.tetraquark.mpp.bluetooth

enum class BluetoothRemoteDeviceType(val code: Int) {
    UNKNOWN(0),
    CLASSIC(1),
    LE(2),
    DUAL(3);

    companion object {

        fun getByCode(code: Int): BluetoothRemoteDeviceType = when(code) {
            1 -> CLASSIC
            2 -> LE
            3 -> DUAL
            else -> UNKNOWN
        }

    }

}

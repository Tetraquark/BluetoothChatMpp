package ru.tetraquark.mpp.bluetooth

import kotlinx.coroutines.flow.Flow

expect class BLEGattConnection {

    val remoteDevice: BluetoothRemoteDevice

    var isConnected: Boolean
        private set

    var isClosed: Boolean
        private set

    suspend fun connect(autoConnect: Boolean, timeoutMillis: Long): Flow<BLEConnectionState>

    //suspend fun discoverService(uuid: UUID): BLEGattService

    suspend fun discoverServices(): List<BLEGattService>

    suspend fun send(data: ByteArray)

    suspend fun disconnect()

    suspend fun readRssi(): Int

    fun close()

}

package ru.tetraquark.mpp.bluetooth

import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

actual class BluetoothConnection(
    actual val remoteDevice: BluetoothRemoteDevice,
    private val bluetoothSocket: BluetoothSocket,
    private val byteBufferLength: Int = 1024,
    private val connectionExecutor: ExecutorService = Executors.newSingleThreadExecutor()
) {

    private val inputStream = bluetoothSocket.inputStream
    private val outputStream = bluetoothSocket.outputStream

    private val isRunning = AtomicBoolean(false)
    private val listenersMap = ConcurrentHashMap<Int, ConnectionListener>()

    init {
        runSocketObserver()
    }

    actual fun isConnected(): Boolean = isRunning.get()

    actual fun send(data: ByteArray) {
        outputStream.write(data)
    }

    actual fun close() {
        try {
            isRunning.set(false)
            connectionExecutor.shutdown()
            bluetoothSocket.close()

            notifyObservers {
                onClose()
            }
        } catch (ioException: IOException) { }
    }

    actual fun addConnectionListener(listener: ConnectionListener) {
        listenersMap[listener.hashCode()] = listener
    }

    actual fun removeConnectionListener(listener: ConnectionListener) {
        listenersMap.remove(listener.hashCode())
    }

    private fun runSocketObserver() {
        isRunning.set(true)

        connectionExecutor.execute {
            while(isRunning.get()) {
                try {
                    val buffer = ByteArray(byteBufferLength)
                    inputStream.read(buffer)

                    notifyObservers {
                        onReceived(buffer)
                    }
                } catch (ioException: IOException) {
                    notifyObservers {
                        onError(ioException)
                    }
                }
            }
        }
    }

    private inline fun notifyObservers(block: ConnectionListener.() -> Unit) {
        listenersMap.forEach {
            block(it.value)
        }
    }

}

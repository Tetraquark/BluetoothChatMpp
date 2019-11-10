package ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery

import dev.icerock.moko.units.UnitItem

interface DeviceDiscoveryUnitFactory {

    fun getBluetoothPeerUnit(
        bluetoothPeer: BluetoothPeer,
        onClickAction: (Int) -> Unit
    ): UnitItem

}

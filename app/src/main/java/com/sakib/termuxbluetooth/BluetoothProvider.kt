package com.sakib.termuxbluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import org.json.JSONArray
import org.json.JSONObject

class BluetoothProvider : ContentProvider() {

        private lateinit var adapter: BluetoothAdapter

            override fun onCreate(): Boolean {

                        val manager =
                                    context?.getSystemService(
                                                        BluetoothManager::class.java
                                    )

                                            adapter = manager?.adapter
                                                        ?: return false

                                                                return true
            }

                override fun call(
                            method: String,
                                    arg: String?,
                                            extras: Bundle?
                ): Bundle {

                            val result = Bundle()

                                    // Only allow Termux
                                            val callingUid = Binder.getCallingUid()

                                                    val packages =
                                                                context?.packageManager
                                                                                ?.getPackagesForUid(callingUid)

                                                                                        if (packages?.contains("com.termux") != true) {

                                                                                                        result.putString(
                                                                                                                            "error",
                                                                                                                                            "Access denied"
                                                                                                        )

                                                                                                                    return result
                                                                                        }

                                                                                                when (method) {

                                                                                                                "status" -> {

                                                                                                                                    result.putString(
                                                                                                                                                            "result",
                                                                                                                                                                                getStatus()
                                                                                                                                    )
                                                                                                                }

                                                                                                                            "devices" -> {

                                                                                                                                                result.putString(
                                                                                                                                                                        "result",
                                                                                                                                                                                            getPairedDevices()
                                                                                                                                                )
                                                                                                                            }

                                                                                                                                        "scan" -> {

                                                                                                                                                            result.putString(
                                                                                                                                                                                    "result",
                                                                                                                                                                                                        startScan()
                                                                                                                                                            )
                                                                                                                                        }

                                                                                                                                                    else -> {

                                                                                                                                                                        result.putString(
                                                                                                                                                                                                "error",
                                                                                                                                                                                                                    "Unknown method: $method"
                                                                                                                                                                        )
                                                                                                                                                    }
                                                                                                }

                                                                                                        return result
                }

                    private fun getStatus(): String {

                                val json = JSONObject()

                                        json.put(
                                                        "available",
                                                                    adapter.isEnabled
                                        )

                                                json.put(
                                                                "enabled",
                                                                            adapter.isEnabled
                                                )

                                                        return json.toString()
                    }

                        private fun getPairedDevices(): String {

                                    val array = JSONArray()

                                            for (device: BluetoothDevice in adapter.bondedDevices) {

                                                            val obj = JSONObject()

                                                                        obj.put(
                                                                                            "name",
                                                                                                            device.name ?: "Unknown"
                                                                        )

                                                                                    obj.put(
                                                                                                        "address",
                                                                                                                        device.address
                                                                                    )

                                                                                                obj.put(
                                                                                                                    "bondState",
                                                                                                                                    device.bondState
                                                                                                )

                                                                                                            array.put(obj)
                                            }

                                                    return array.toString()
                        }

                            private fun startScan(): String {

                                        if (adapter.isDiscovering) {
                                                        adapter.cancelDiscovery()
                                        }

                                                val started = adapter.startDiscovery()

                                                        val json = JSONObject()

                                                                json.put(
                                                                                "started",
                                                                                            started
                                                                )

                                                                        json.put(
                                                                                        "message",
                                                                                                    if (started)
                                                                                                                    "Bluetooth discovery started"
                                                                                                                                else
                                                                                                                                                "Bluetooth discovery failed"
                                                                        )

                                                                                return json.toString()
                            }

                                override fun query(
                                            uri: Uri,
                                                    projection: Array<out String>?,
                                                            selection: String?,
                                                                    selectionArgs: Array<out String>?,
                                                                            sortOrder: String?
                                ): Cursor? = null

                                    override fun getType(uri: Uri): String? = null

                                        override fun insert(
                                                    uri: Uri,
                                                            values: ContentValues?
                                        ): Uri? = null

                                            override fun delete(
                                                        uri: Uri,
                                                                selection: String?,
                                                                        selectionArgs: Array<out String>?
                                            ): Int = 0

                                                override fun update(
                                                            uri: Uri,
                                                                    values: ContentValues?,
                                                                            selection: String?,
                                                                                    selectionArgs: Array<out String>?
                                                ): Int = 0
}
                                                                                                                }
                                                                                        }
                                    )
            }
}
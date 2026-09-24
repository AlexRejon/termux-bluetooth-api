package com.sakib.termuxbluetooth

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle

class MainActivity : Activity() {

        companion object {
                    private const val REQUEST_BLUETOOTH = 100
        }

            override fun onCreate(savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)

                                requestBluetoothPermissions()
            }

                private fun requestBluetoothPermissions() {

                            val permissions = mutableListOf<String>()

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

                                                    permissions.add(
                                                                        Manifest.permission.BLUETOOTH_SCAN
                                                    )

                                                                permissions.add(
                                                                                    Manifest.permission.BLUETOOTH_CONNECT
                                                                )

                                    } else {

                                                    permissions.add(
                                                                        Manifest.permission.ACCESS_FINE_LOCATION
                                                    )
                                    }

                                            val missing = permissions.filter {
                                                            checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
                                            }

                                                    if (missing.isNotEmpty()) {

                                                                    requestPermissions(
                                                                                        missing.toTypedArray(),
                                                                                                        REQUEST_BLUETOOTH
                                                                    )

                                                    } else {

                                                                    openBluetoothSettingsIfNeeded()
                                                    }
                }

                    private fun openBluetoothSettingsIfNeeded() {

                                val manager =
                                            getSystemService(BLUETOOTH_SERVICE) as BluetoothManager

                                                    val adapter: BluetoothAdapter? =
                                                                manager.adapter

                                                                        if (adapter != null && !adapter.isEnabled) {

                                                                                        val intent =
                                                                                                        Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

                                                                                                                    startActivity(intent)
                                                                        }
                    }
}
                                                    )
                                    }
        }
}
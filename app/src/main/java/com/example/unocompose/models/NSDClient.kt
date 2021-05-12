package com.example.unocompose.models

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import java.net.InetAddress
import java.net.Socket

class NSDClient(
    context: Context,
    private val onUpdate : (ScanResult) -> Unit
) {

    var nsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager

    inner class NsdResolveListener : NsdManager.ResolveListener {
        override fun onResolveFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
            if (errorCode == NsdManager.FAILURE_ALREADY_ACTIVE) return
            Log.e(TAG, "failed $serviceInfo $errorCode")
        }
        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Log.e(TAG, "Resolve Succeeded. $serviceInfo")
            onUpdate(ScanResult(serviceInfo.host, serviceInfo.serviceName))

            val port: Int = serviceInfo.port
            val host: InetAddress = serviceInfo.host
            Log.d(TAG, "Creatinng socket")
            val socket = Socket(host, port)
            Log.d(TAG, "Created socket $socket")
            socket.getOutputStream().bufferedWriter().use {
                it.append("Hello")
                it.newLine()
            }
        }
//            override fun onServiceResolved(serviceInfo: NsdServiceInfo?) {
//                if (serviceInfo == null) return
//                val host = serviceInfo.host
//                if (host !is Inet4Address) return
//                onUpdate(NSD.ScanResult(host, serviceInfo.serviceName))
//
//            }
    }

    inner class NsdListener : NsdManager.DiscoveryListener {
        override fun onServiceFound(serviceInfo: NsdServiceInfo?) {
            Log.d(TAG, "service found")
            nsdManager.resolveService(serviceInfo, NsdResolveListener())
        }

        override fun onStopDiscoveryFailed(serviceType: String?, errorCode: Int) {
            Log.e(TAG, "discovery stop failed $serviceType $errorCode")
        }

        override fun onStartDiscoveryFailed(serviceType: String?, errorCode: Int) {
            Log.e(TAG, "discovery start failed $serviceType $errorCode")
        }

        override fun onDiscoveryStarted(serviceType: String?) {
            Log.d(TAG, "discovery started")
        }

        override fun onDiscoveryStopped(serviceType: String?) {
            Log.i(TAG, "Discovery stopped: $serviceType")
        }

        override fun onServiceLost(serviceInfo: NsdServiceInfo?) {
            Log.d(TAG, "service lost")
        }

    }
}

data class ScanResult(val ipAddress: InetAddress, val name: String)
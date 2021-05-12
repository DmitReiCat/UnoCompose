package com.example.unocompose.models

import android.app.Application
import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

class NSDHost(
    application: Application
) {

    init {
        initializeServerSocket()
    }

    lateinit var serverSocket: ServerSocket
    var mLocalPort = 0
    var mServiceName = ""
    var nsdManager = application.applicationContext.getSystemService(Context.NSD_SERVICE) as NsdManager
    val stopWord = AtomicBoolean(true)

    val registrationListener = object : NsdManager.RegistrationListener {
        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
            // Save the service name. Android may have changed it in order to
            // resolve a conflict, so update the name you initially requested
            // with the name Android actually used.
            mServiceName = NsdServiceInfo.serviceName
            Log.d(TAGNSD, "Service registered!, ${NsdServiceInfo.serviceName}, ${NsdServiceInfo.serviceType}")

            GlobalScope.launch {
                while (stopWord.get()) {
                    Log.d(TAG, "Strat listening on $serverSocket")
                    val clientSocket = serverSocket.accept()
                    GlobalScope.launch {
                        clientSocket.getInputStream().bufferedReader().use {
                            val message = it.readLine()
                            Log.d(TAGNSD, message)

                        }
                    }
                }
            }
        }

        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Registration failed! Put debugging code here to determine why.
            Log.d(TAGNSD, "Registration failed!")
        }
        override fun onServiceUnregistered(arg0: NsdServiceInfo) {
            // Service has been unregistered. This only happens when you call
            // NsdManager.unregisterService() and pass in this listener.
            Log.d(TAGNSD, "Registration successful!")
        }
        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Unregistration failed. Put debugging code here to determine why.
            Log.d(TAGNSD, "Unegistration failed!")
        }
    }

    fun initializeServerSocket() {
        // Initialize a server socket on the next available port.
        serverSocket = ServerSocket(0).also { socket ->
            // Store the chosen port.
            mLocalPort = socket.localPort
        }
    }

    fun registerService(port: Int) {
        // Create the NsdServiceInfo object, and populate it.
        val serviceInfo = NsdServiceInfo().apply {
            // The name is subject to change based on conflicts
            // with other services advertised on the same network.
            serviceName = "NsdChat1"
            serviceType = "_nsdchat._tcp"
            setPort(port)
            Log.d(TAGNSD, "info created")
        }
        nsdManager.apply {
            registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
        }
        Log.d(TAGNSD, "Begining registration")
    }



    val serviceType = "_nsdchat._tcp"





//    btnRegister.setOnClickListener {
//        initializeServerSocket()
//        Log.d(TAG, "Strating registration")
//        Log.d(TAGNSD, "port is $mLocalPort")
//        registerService(mLocalPort)
//    }
//    btnDiscover.setOnClickListener {
//        Log.d(TAG, "Strating discovery")
//        GlobalScope.launch{
//            Log.d(TAG, "Strating discovery coroutine")
//            nsdManager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, NsdListener())
//        }
//    }


}
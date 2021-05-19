package com.example.unocompose.models

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.ServerSocket

class NSDHost(
    private var nsdManager: NsdManager
) {

    init {
        initializeServerSocket()
    }

    lateinit var serverSocket: ServerSocket
    var localPort = 0
    var serviceName = ""


    private val registrationListener = object : NsdManager.RegistrationListener {
        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
            // Save the service name. Android may have changed it in order to
            // resolve a conflict, so update the name you initially requested
            // with the name Android actually used.
            serviceName = NsdServiceInfo.serviceName
            Log.d(TAGNSD, "Service registered!, ${NsdServiceInfo.serviceName}, ${NsdServiceInfo.serviceType}")
            GlobalScope.launch {
                Log.d(TAG,"launching coroutine")
                ServerConnection().createChannels()
            }




//            GlobalScope.launch {
//                while (stopWord.get()) {
//                    Log.d(TAG, "Strat listening on $serverSocket")
//                    val clientSocket = serverSocket.accept()
//                    GlobalScope.launch {
//                        clientSocket.getInputStream().bufferedReader().use {
//                            val message = it.readLine()
//                            Log.d(TAGNSD, message)
//
//                        }
//                    }
//                }
//            }
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
            // Unregister failed. Put debugging code here to determine why.
            Log.d(TAGNSD, "Unregister failed!")
        }
    }

    private fun initializeServerSocket() {
        // Initialize a server socket on the next available port.
        serverSocket = ServerSocket(0).also { socket ->
            // Store the chosen port.
            localPort = socket.localPort
        }
    }

    suspend fun registerService() {
        // Create the NsdServiceInfo object, and populate it.
        val serviceInfo = NsdServiceInfo().apply {
            // The name is subject to change based on conflicts
            // with other services advertised on the same network.
            serviceName = "NsdChat"
            serviceType = "_nsdchat._tcp"
            port = localPort
            Log.d(TAGNSD, "info created")
        }
        nsdManager.apply {
            Log.d(TAGNSD, "Beginning registration")
            registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
        }




    }



//    val serviceType = "_nsdchat._tcp"





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
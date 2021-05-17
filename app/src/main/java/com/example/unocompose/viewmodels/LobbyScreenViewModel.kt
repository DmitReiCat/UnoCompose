package com.example.unocompose.viewmodels

import android.content.Context
import android.net.nsd.NsdManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.unocompose.models.NSDHost
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

class LobbyScreenViewModel(nsdManager: NsdManager): ViewModel() {

    val mNsdManager = nsdManager
    fun openOnNetwork (){
        NSDHost(mNsdManager).registerService()
    }

}
package com.example.unocompose.di

import android.app.Application
import android.content.Context
import android.net.nsd.NsdManager
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

//    @Provides
//    fun provideNsdManager(
//        @ApplicationContext context: Context
//    ) = context.getSystemService(Context.NSD_SERVICE) as NsdManager

    @Singleton
    @Provides
    fun provideString() = "dsjd"


    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

}

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelMovieModule {

    @Provides
    @ViewModelScoped
    fun provideNsdManager(
        @ApplicationContext context: Context
    ) = context.getSystemService(Context.NSD_SERVICE) as NsdManager
}

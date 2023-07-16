package com.example.sneakership.di

import android.content.Context
import com.example.sneakership.MyApplication
import com.example.sneakership.api.ErrorHandler
import com.example.sneakership.api.SneakersDataSource
import com.example.sneakership.api.SneakersRepository
import com.example.sneakership.api.SneakersService
import com.example.sneakership.repository.CartRepo
import com.example.sneakership.viewModels.CartDetailViewModel
import com.example.sneakership.viewModels.HomeViewModel
import com.example.sneakership.viewModels.SneakerDetailViewModel
import com.example.sneakership.viewModels.SneakersListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideErrorHandler(): ErrorHandler {
        return ErrorHandler()
    }

    @Provides
    fun provideSneakersService(): SneakersService {
        return RetrofitClient.sneakersService
    }

    @Provides
    fun provideSneakersRepository(dataSource: SneakersDataSource): SneakersRepository {
        return SneakersRepository(dataSource)
    }

    @Provides
    fun provideCartRepository(context: Context): CartRepo {
        return CartRepo(context)
    }

    @Provides
    fun provideContext(): Context = MyApplication.getContext()

    @Provides
    fun provideCartDetailViewModel(
        cartRepo: CartRepo
    ): CartDetailViewModel {
        return CartDetailViewModel(cartRepo)
    }

    @Provides
    fun provideSneakersListViewModel(
        cartRepo: CartRepo, provider: SneakersRepository
    ): SneakersListViewModel {
        return SneakersListViewModel(provider, cartRepo)
    }

    @Provides
    fun provideSneakersDataSource(
        apiService: SneakersService, errorHandler: ErrorHandler
    ): SneakersDataSource {
        return SneakersDataSource(apiService, errorHandler)
    }

    @Provides
    fun provideHomeViewModel(
    ): HomeViewModel {
        return HomeViewModel()
    }

    @Provides
    fun provideSneakerDetailViewModel(
        cartRepo: CartRepo
    ): SneakerDetailViewModel {
        return SneakerDetailViewModel(cartRepo)
    }
}




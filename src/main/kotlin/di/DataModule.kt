package di

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.logging.LogLevel
import dagger.Module
import dagger.Provides
import data.api.payment.PaymentApiClient
import data.api.payment.RetrofitPaymentClient
import data.api.vpn.IHidifyApiClient
import data.api.vpn.NetworkClient
import data.api.vpn.RetrofitHidifyClient
import data.repositiries.ConfigRepositoryImpl
import data.repositiries.payment.PaymentRepositoryImpl
import data.repositiries.UserRepositoryImpl
import domain.repositories.ConfigRepository
import domain.repositories.PaymentRepository
import domain.repositories.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class DataModule {

    /**
    Hididy
     */
    @Provides
    @Singleton
    @Named("vpnRetrofit")
    fun provideRetrofit(): Retrofit {
        val baseUrl = System.getenv("HIDDIFY_API_URL") ?: throw IllegalStateException("HIDDIFY_API_URL отсутствует")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("vpnApiClient")
    fun provideApiClient(
        @Named("vpnRetrofit") retrofit: Retrofit
    ): IHidifyApiClient {
        return retrofit.create(IHidifyApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkClient(
        @Named("vpnApiClient") apiClient: IHidifyApiClient
    ): NetworkClient {
        return RetrofitHidifyClient(apiClient)
    }


    @Provides
    @Singleton
    fun provideUserRepository(
         apiClient: RetrofitHidifyClient
    ): UserRepository {
        return UserRepositoryImpl(apiClient)
    }

    @Provides
    @Singleton
    fun provideConfigRepository(
       apiClient: RetrofitHidifyClient
    ): ConfigRepository {
        return ConfigRepositoryImpl(apiClient)
    }

    /**
    Ю-касса
     */
    @Provides
    @Singleton
    @Named("paymentRetrofit")
    fun provideYooKassaRetrofit(): Retrofit {
        val baseUrl = System.getenv("YOO_KASSA_API_URL") ?: throw IllegalStateException("YOO_KASSA_API_URL отсутствует")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("paymentApiClient")
    fun provideYooKassaApiClient(
        @Named("paymentRetrofit") retrofit: Retrofit
    ): PaymentApiClient {
        return retrofit.create(PaymentApiClient::class.java)
    }

    @Provides
    @Singleton
    fun providePaymentClient(
        @Named("paymentApiClient") apiClient: PaymentApiClient
    ): RetrofitPaymentClient {
        return RetrofitPaymentClient(apiClient)
    }


    @Provides
    @Singleton
    fun providePaymentRepository(
        paymentApiClient: RetrofitPaymentClient
    ): PaymentRepository {
        return PaymentRepositoryImpl(paymentApiClient)
    }

    /**
    телеграм
     */
    @Provides
    @Singleton
    fun provideTelegramBot(): Bot {
        return bot {
            token = System.getenv("TELEGRAM_BOT_TOKEN")
                ?: throw IllegalStateException("TELEGRAM_BOT_TOKEN отсутствует")
            logLevel = LogLevel.Network.Body
        }
    }


}
package di

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.logging.LogLevel
import dagger.Module
import dagger.Provides
import data.api.payment.PaymentApiClient
import data.api.vpn.IHidifyApiClient
import data.api.vpn.NetworkClient
import data.api.vpn.RetrofitNetworkClient
import data.database.AppDatabase
import data.repositiries.ConfigRepositoryImpl
import data.repositiries.PaymentRepositoryImpl
import data.repositiries.UserRepositoryImpl
import domain.repositories.ConfigRepository
import domain.repositories.PaymentRepository
import domain.repositories.UserRepository
import io.ktor.client.HttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class DataModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val baseUrl = System.getenv("HIDDIFY_API_URL") ?: throw IllegalStateException("HIDDIFY_API_URL отсутствует")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): IHidifyApiClient {
        return retrofit.create(IHidifyApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkClient(apiClient: IHidifyApiClient): NetworkClient {
        return RetrofitNetworkClient(apiClient)
    }
    @Provides
    @Singleton
    fun provideUserRepository(apiClient: IHidifyApiClient): UserRepository {
        return UserRepositoryImpl(apiClient)
    }
    @Provides
    @Singleton
    fun provideConfigRepository(apiClient: IHidifyApiClient): ConfigRepository {
        return ConfigRepositoryImpl(apiClient)
    }


    @Provides
    @Singleton
    fun providePaymentRepository(paymentApiClient: PaymentApiClient): PaymentRepository {
        return PaymentRepositoryImpl(paymentApiClient)
    }

    @Provides
    @Singleton
    fun provideTelegramBot(): Bot {
        return bot {
            token = System.getenv("TELEGRAM_BOT_TOKEN")
                ?: throw IllegalStateException("TELEGRAM_BOT_TOKEN отсутствует")
            logLevel = LogLevel.Network.Body
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase {
        return AppDatabase.also {
            it.connect()
        }
    }
}
package presentation.routes

import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Application.launchPeriodicCacheCleanup() {
    CoroutineScope(this.coroutineContext).launch {
        while (true) {
            delay(60 * 60 * 1000L) // каждый час (можно менять)
            PaymentUserCache.cleanupExpired()
        }
    }
}
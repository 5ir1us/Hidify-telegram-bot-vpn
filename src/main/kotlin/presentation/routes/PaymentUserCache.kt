package presentation.routes

import java.util.concurrent.ConcurrentHashMap


object PaymentUserCache {
    private const val TTL_MILLIS = 2 * 24 * 60 * 60 * 1000L // 2 дня

    private val map = ConcurrentHashMap<String, Pair<Long, Long>>() // paymentId -> (chatId, timestamp)

    fun save(paymentId: String, chatId: Long) {
        val timestamp = System.currentTimeMillis()
        map[paymentId] = chatId to timestamp
    }

    fun getChatId(paymentId: String): Long? {
        val entry = map[paymentId] ?: return null
        val (chatId, timestamp) = entry
        val now = System.currentTimeMillis()
        return if (now - timestamp <= TTL_MILLIS) {
            chatId
        } else {
            map.remove(paymentId)
            null
        }
    }

    fun cleanupExpired() {
        val now = System.currentTimeMillis()
        map.entries.removeIf { now - it.value.second > TTL_MILLIS }
        println("🧹 Удалены устаревшие записи из PaymentUserCache")
    }

    fun clear() {
        map.clear()
    }
}


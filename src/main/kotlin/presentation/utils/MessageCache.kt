package presentation.utils



object MessageCache {
    private val cache = mutableMapOf<Long, Long>()


    fun save(chatId: Long, messageId: Long) {
        cache[chatId] = messageId
    }


    fun get(chatId: Long): Long? {
        return cache[chatId]
    }


    fun clear(chatId: Long) {
        cache.remove(chatId)
    }
}


package data.api.vpn

interface NetworkClient {
    suspend fun <T> doRequest(request: suspend () -> T): T
}
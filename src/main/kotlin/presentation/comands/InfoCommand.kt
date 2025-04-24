package presentation.comands


import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import javax.inject.Inject


class InfoCommand @Inject constructor() {

    fun register (dispatcher: Dispatcher){
        dispatcher.command("info"){
            val chatId = message.chat.id

        }
    }



}
package com.haneetarya.mychat

import com.haneetarya.mychat.data.MessageData
import io.socket.client.IO
import io.socket.client.Socket

class Util {
    companion object{
        private const val url = "SERVER_URL"
        private var socket: Socket? = null
        fun connect(name: String): Socket? {
            if(socket==null) socket = IO.socket(url)
            socket?.connect()
            socket?.emit("new-user-joined", name)
            return socket
        }
        fun sendMessage(message: MessageData){
            socket?.emit("send", message.message)
        }
    }
}
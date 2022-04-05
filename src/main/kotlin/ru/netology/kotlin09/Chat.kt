package ru.netology.kotlin09

class Chat(val chatId: Int, val addressee: ChatPerson, var chatName: String? = null, firstOutMessage: String? = null) {

    private var messageList: MutableMap<Int, ChatMessage> = mutableMapOf()

    private var messageId = 0

    private var noMessages = 0
    private var noSent = 0
    private var noReceived = 0

    init {
        if (chatName == null) chatName = addressee.name
        if (firstOutMessage != null) {
            createMessage(firstOutMessage, MessageDirection.outgoing)
        }
    }


    fun createMessage(text: String?, direction: MessageDirection): ChatMessage? {
        val mId = getNextMessageId()
        val read = if(direction == MessageDirection.incoming) false else true
        val message = messageList.put(mId, ChatMessage(mId, addressee, text, direction,read))

        noMessages++

        if (direction == MessageDirection.outgoing) {
            noSent++
        } else {
            noReceived++
        }

        return message
    }

    fun deleteMessage(messageId: Int): Boolean {
        val message = messageList.remove(messageId)

        if (message == null) return false

        if (message.direction == MessageDirection.outgoing)
            noSent--
        else
            noReceived--

        noMessages--

        return true
    }


    fun getMessages(mId: Int, count: Int): List<ChatMessage>? {
        if (mId > messageId || mId < 0 || count < 0)
            return null

        val messages = messageList.filter { it.key >= mId }.values.toList().subList(0, count)

        var retList = mutableListOf<ChatMessage>()
        retList.addAll(messages)

        return retList
    }

    fun deleteAllMessages(): Boolean {
        messageList = mutableMapOf()

        messageId = 0
        noMessages = 0
        noSent = 0
        noReceived = 0

        return true
    }

    fun getAllMessages(): List<ChatMessage>? {
        return messageList.values.toList()
    }

    fun markAsRead(mId: Int, count: Int): Boolean {
        if (mId > messageId || mId < 0 || count < 0)
            return false

        messageList.filter { it.key >= mId }.forEach {
            it.value.read = true
        }

        return true
    }

    fun markAsRead(mId: Int): Boolean {
        if (mId > messageId || mId < 0)
            return false

        val message = messageList.get(mId)

        if (message == null)
            return false

        message.read = true

        return true
    }

    fun getLastMessage(): ChatMessage? {
        return messageList.get(messageId)
    }

    fun getNumberUnread(): Int {
        return messageList.count { it.value.read == false }
    }


    private fun getNextMessageId(): Int {
        return ++messageId
    }

    fun getNumberOfMessages(): Int = messageList.count()

}


data class ChatMessage(
    val messageId: Int,
    val personId: ChatPerson,
    var text: String? = null,
    val direction: MessageDirection,
    var read: Boolean = false,
    val timestamp: Int = System.currentTimeMillis().toInt(),
){
    override fun hashCode(): Int {
        return messageId.hashCode()
    }
}

enum class MessageDirection { incoming, outgoing }

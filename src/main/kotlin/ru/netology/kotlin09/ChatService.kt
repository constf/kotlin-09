package ru.netology.kotlin09


class ChatService {
    var personsList: MutableList<ChatPerson> = mutableListOf()
    var chatList: MutableList<Chat> = mutableListOf()

    var chatIds: Int = 0
    var personsIds: Int = 0

    fun unreadChatsNumber(): Int {
        var count: Int = chatList.asSequence().count { it.getNumberUnread() > 0 }

        return count
    }

    fun getChatsShortView(): List<String> {
        var chatsStrings: MutableList<String> = mutableListOf()

        chatList.asSequence().forEach {
            var chatShort: String =
                "[${it.chatId.toString().padStart(4, '0')}] <${"%15s".format(it.chatName)}>:" +
                        " ${"%30s".format(it.getLastMessage()?.text)}"

            chatsStrings.add(chatShort)
        }

        return chatsStrings
    }

    fun addPerson(person: ChatPerson) {
        personsList += person
    }

    fun sendMessage(person: ChatPerson, text: String): ChatMessage? {
        var chat = chatList.find { it.addressee.personId == person.personId }

        if (chat == null) { // Создаём новый чат
            chat = Chat(chatId = getNextChatId(), addressee = person)
            chatList += chat
        }

        return chat?.createMessage(text, MessageDirection.outgoing)
    }

    fun sendMessage(chatId: Int, text: String?): ChatMessage? {
        var chat = chatList.find { it.chatId == chatId }

        return chat?.createMessage(text, MessageDirection.outgoing)
    }


    fun receiveMessage(person: ChatPerson, text: String): ChatMessage? {
        val chat = chatList.find { it.addressee.personId == person.personId }

        return chat?.createMessage(text, MessageDirection.incoming)
    }

    fun receiveMessage(chatId: Int, text: String?): ChatMessage? {
        val chat = chatList.find { it.chatId == chatId }

        return chat?.createMessage(text, MessageDirection.incoming)
    }

    fun deleteMessage(chatId: Int, messageId: Int): Boolean {
        val chat = chatList.find { it.chatId == chatId }

        if (chat == null) return false

        chat.deleteMessage(messageId)

        if (chat.getNumberOfMessages() == 0) {
            this.deleteChat(chatId)
        }

        return true
    }

    fun deleteChat(person: ChatPerson): Boolean {
        var chat = chatList.find { it.addressee.personId == person.personId }

        if (chat == null)
            return false

        chat.deleteAllMessages()
        return chatList.remove(chat)
    }

    fun deleteChat(chatId: Int): Boolean {
        var chat = chatList.find { it.chatId == chatId }

        if (chat == null)
            return false

        chat.deleteAllMessages()
        return chatList.remove(chat)
    }


    private fun getNextChatId(): Int {
        return ++chatIds
    }

    private fun getNextPersonId(): Int {
        return ++personsIds
    }

    fun getMessages(chatNumber: Int): List<String>? {
        val chat = chatList.find { it.chatId == chatNumber }
        if (chat == null) return null

        var outStrings: MutableList<String> = mutableListOf()

        chat.getAllMessages()?.asSequence()?.forEach {
            var ms: String = "<${it.messageId.toString().padStart(4, '0')}>" +
                    " [${if (it.direction == MessageDirection.incoming) "ВХД" else "ИСХ"}] " +
                    "<${if (it.read) " " else "*"}> : ${it.text} "
            outStrings += ms

            it.read = true
        }

        return outStrings
    }

}
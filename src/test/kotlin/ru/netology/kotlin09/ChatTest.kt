package ru.netology.kotlin09

import org.junit.Assert.assertEquals
import org.junit.Test

class ChatTest {

    @Test
    fun createMessage() {
        var chat = Chat(1, ChatPerson(1, "John Read"))
        chat.deleteAllMessages()

        chat.createMessage("The message text 01", MessageDirection.outgoing)
        chat.createMessage("The message text 02", MessageDirection.incoming)

        assertEquals(2, chat.getNumberOfMessages())
    }

    @Test
    fun deleteMessage() {
        var chat = Chat(1, ChatPerson(1, "John Read"))
        chat.deleteAllMessages()

        chat.createMessage("The message text 01", MessageDirection.outgoing)
        chat.createMessage("The message text 02", MessageDirection.incoming)
        chat.createMessage("The message text 03", MessageDirection.outgoing)
        chat.createMessage("The message text 04", MessageDirection.incoming)

        chat.deleteMessage(1)
        chat.deleteMessage(2)

        assertEquals(2, chat.getNumberOfMessages())
    }


    @Test
    fun getAllMessages() {
        var chat = Chat(1, ChatPerson(1, "John Read"))
        chat.deleteAllMessages()

        chat.createMessage("The message text 01", MessageDirection.outgoing)
        chat.createMessage("The message text 02", MessageDirection.incoming)
        chat.createMessage("The message text 03", MessageDirection.outgoing)
        chat.createMessage("The message text 04", MessageDirection.incoming)

        val mess = chat.getAllMessages()

        assertEquals(4, mess?.size)
    }


    @Test
    fun getNumberOfMessages() {
        var chat = Chat(1, ChatPerson(1, "John Read"))
        chat.deleteAllMessages()

        chat.createMessage("The message text 01", MessageDirection.outgoing)
        chat.createMessage("The message text 02", MessageDirection.incoming)

        assertEquals(2, chat.getNumberOfMessages())
    }

    @Test
    fun getMessages() {
        var chat = Chat(1, ChatPerson(1, "John Read"))
        chat.deleteAllMessages()

        chat.createMessage("The message text 01", MessageDirection.outgoing)
        chat.createMessage("The message text 02", MessageDirection.incoming)
        chat.createMessage("The message text 03", MessageDirection.outgoing)
        chat.createMessage("The message text 04", MessageDirection.incoming)
        chat.createMessage("The message text 05", MessageDirection.outgoing)
        chat.createMessage("The message text 06", MessageDirection.incoming)
        chat.createMessage("The message text 07", MessageDirection.outgoing)
        chat.createMessage("The message text 08", MessageDirection.incoming)

        val mess = chat.getMessages(1, 5)

        assertEquals(5, mess?.size)
    }
}
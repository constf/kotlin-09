package ru.netology.kotlin09

import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Test
    fun sendMessage() {
        var cs = ChatService()

        var personsCounter: Int = 0
        val person = ChatPerson(++personsCounter, "Юрий Петров")
        cs.addPerson(person)

        cs.sendMessage(person, "Message to $person, Hi Hi, hello!")
        cs.sendMessage(person, "Message to $person, Hi Hi, hello!")
        cs.sendMessage(person, "Message to $person, Hi Hi, hello!")

        assertEquals(1, cs.chatList.size)
    }
}
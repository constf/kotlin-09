package ru.netology.kotlin09

import kotlin.random.Random

fun main() {
    var cs = ChatService()

    var personsCounter: Int = 0
    cs.addPerson(ChatPerson(++personsCounter, "Юрий Петров"))
    cs.addPerson(ChatPerson(++personsCounter, "Сергей Глазунов"))
    cs.addPerson(ChatPerson(++personsCounter, "John Smith"))
    cs.addPerson(ChatPerson(++personsCounter, "Melinda Carlyle"))
    cs.addPerson(ChatPerson(++personsCounter, "Нетология"))
    cs.addPerson(ChatPerson(++personsCounter, "Рабочие вопросы"))

    // Создаём сообщения
    cs.personsList.forEach {
        //var random = Random(30)
        val noMessages = Random.nextInt(1, 30)

        for (i: Int in 0 until noMessages) {
            if (i % 2 == 0)
                cs.sendMessage(it, "Текст исходящего сообщения $i")
            else
                cs.receiveMessage(it, "Инфомация входящего сообщения $i")
        }
    }

    var userInput: String? = null

    do {
        println("Номер            Чат             Последнее сообщение")
        println("=======================================================================================================")
        val chatsShort = cs.getChatsShortView()
        chatsShort.forEach { println(it) }
        println("=======================================================================================================")
        println("Что делаем?           войти в чат - номер чата, знак ? - справка по чатам, выход - end")
        print("Введите Ваш выбор: ")
        userInput = readLine()

        if (userInput == "?") {
            println("=======================================================================================================")
            println("Количество чатов с непрочитанными сообщениями: ${cs.unreadChatsNumber()}")
            println("=======================================================================================================")
            userInput = readLine()
            continue
        }

        val chatNumber = userInput?.toIntOrNull()
        if (chatNumber == null) continue

        do {
            val chatMessages = cs.getMessages(chatNumber)
            println("Номер   Признаки         Сообщения")
            println("=======================================================================================================")
            chatMessages?.forEach { println(it) }
            println("=======================================================================================================")
            println("* - непрочитанное сообщение.Что тут можно сделать? Введите:")
            println("Номер сообщения - удалить это сообщение, отправить новое - s, получить новое - r, q - выход")
            userInput = readLine()

            if (userInput == "s") {
                print(">>> Введите текст сообщения для отправки: ")
                val mt = readLine()
                cs.sendMessage(chatNumber, mt)
                continue
            }

            if (userInput == "r") {
                print(">>> Введите текст полученного сообщения: ")
                val mt = readLine()
                cs.receiveMessage(chatNumber, mt)
                continue
            }

            val mdId = userInput?.toIntOrNull()
            if (mdId == null) continue

            cs.deleteMessage(chatNumber, mdId)

        } while (userInput != "q")

    } while (userInput != "end")

}
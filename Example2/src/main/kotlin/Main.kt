// головний клас

// клас у вигляді структури з полями
class Employee {
    var name = ""
    var surname = ""
    var patronymic = ""
    var jobTitle = ""
    var childCount = 0
    var child = emptyArray<Child>()
}

// клас "Дитина"
class Child {
    var name = ""
    var age = 0
}

fun main(args: Array<String>) {
    println("Введіть кількість співробітників =>")
    val employeeCount = readln().toInt() // читання кількості=
    var employeeArray = emptyArray<Employee>()
    println("Введіть інформацію про кожного співробітника:")
    for (i in 0 until employeeCount) { // ітерація
        val employee = Employee().apply { // зміна контексту до об'єкту Employee
            println("Введіть прізвище ${i + 1} співробітника =>")
            surname = readln()
            println("Введіть його ім'я =>")
            name = readln()
            println("Введіть його по батькові =>")
            patronymic = readln()
            println("Введіть його посаду =>")
            jobTitle = readln()
            println("Введіть кількість дітей =>")
            childCount = readln().toInt()
            for (j in (0).until(childCount)) {
                val childInstance = Child().apply {
                    println("Введіть ім'я ${j + 1} дитини => ")
                    name = readln()
                    println("Введіть його вік =>")
                    age = readln().toInt()
                }
                child = child.plus(childInstance)
            }
        }
        employeeArray = employeeArray.plus(employee)
    }
    // Виведення інформації про співробітників
    println("Співробітники фірми:\n фам\tім'я\tотч\tпосада\t")
    employeeArray.forEach { em ->
        println(" ${em.surname}\t${em.name}\t${em.patronymic}\t${em.jobTitle}")
        println("Діти:")
        em.child.forEach { ch ->
            println("\t\t${ch.name}\t\t${ch.age}")
            println()
        }
    }
}
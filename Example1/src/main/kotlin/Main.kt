// головний клас

// клас у вигляді структури з полями
class Employee {
    var name = ""
    var surname = ""
    var patronymic = ""
    var jobTitle = ""
    var salary = 0
}

fun main(args: Array<String>) {
    println("Введіть кількість співробітників =>")
    val employeeCount = readln().toInt() // читання кількості
    val employeeArray = arrayOfNulls<Employee>(employeeCount)
    println("Введіть інформацію про кожного співробітника:")
    for (i in 0 until employeeCount) { // ітерація
        employeeArray[i] = Employee().apply { // зміна контексту до об'єкту Employee
            println("Введіть прізвище ${i + 1} співробітника =>")
            surname = readln()
            println("Введіть його ім'я =>")
            name = readln()
            println("Введіть його по батькові =>")
            patronymic = readln()
            println("Введіть його посаду =>")
            jobTitle = readln()
            println("Введіть його оклад =>")
            salary = readln().toInt()
        }
    }
    // Виведення інформації про співробітників
    println("Співробітники фірми:\n фам\tім'я\tотч\t посада \tОклад")
    employeeArray.forEach { employee ->
        val it = employee!!
        println(" ${it.surname}\t${it.name}\t${it.patronymic}\t ${it.jobTitle} \t${it.salary}")
    }
}
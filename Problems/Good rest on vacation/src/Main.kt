import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    var numDays = scanner.nextInt()
    val costPerDay = scanner.nextInt()
    val costPerFly = scanner.nextInt() * 2
    val costPerNight = scanner.nextInt()

    println(numDays * costPerDay + costPerFly + --numDays * costPerNight)
}
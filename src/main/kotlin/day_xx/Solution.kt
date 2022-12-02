package day_xx

import InputType
import java.io.File

fun main(args: Array<String>) {
    val input = getInput(InputType.SAMPLE)


//    println("*** Result part 1: $input ***")

//    println("*** Result part 2: $input ***")
}



private fun getInput(inputType: InputType): List<Int> {
    return File("src/main/kotlin/day_xx/${inputType.fileName}.txt")
            .readLines()
            .map(String::toInt)


}
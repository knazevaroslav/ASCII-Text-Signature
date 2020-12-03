package signature

import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val romanAlphabet = readFont("roman")
    val mediumAlphabet = readFont("medium")

    val scan = Scanner(System.`in`)
    print("Enter name and surname: ")
    val upperText = formatUpperText(scan.nextLine(), romanAlphabet)

    print("Enter person's status: ")
    val bottomText = formatBottomText(scan.nextLine(), mediumAlphabet)

    println(createBorder(upperText, bottomText, romanAlphabet[0].size, mediumAlphabet[0].size))
}

fun readFont(filePath: String): Array<Array<String>> {
    val scan = Scanner(File("C:\\Users\\User\\IdeaProjects\\ASCII Text Signature\\$filePath.txt"))
    val alphabet: Array<Array<String>>

    val header = scan.nextLine().split(' ').toTypedArray()
    val size = header[0].toInt()
    val numLetters = header[1].toInt()

    alphabet = Array(numLetters) { emptyArray() }

    var curr: Char
    var currIndex: Int

    while (scan.hasNextLine()) {
        curr = scan.nextLine().first()
        currIndex = curr.toInt() - when {
            curr.isUpperCase() -> 65
            else -> 71
        }

        alphabet[currIndex] = Array(size) { scan.nextLine() }
    }

    return alphabet
}

fun formatUpperText(text: String, alphabet: Array<Array<String>>): String {
    var out = ""

    var currIndex: Int
    for (i in alphabet[0].indices) {
        for (curr in text) {
            currIndex = curr.toInt()
            out += when {
                curr == ' ' -> " ".repeat(10)
                curr.isUpperCase() -> alphabet[currIndex - 65][i]
                else -> alphabet[currIndex - 71][i]
            }
        }
        out += '\n'
    }

    return out
}

fun formatBottomText(text: String, alphabet: Array<Array<String>>): String {
    var out = ""

    var currIndex: Int
    for (i in alphabet[0].indices) {
        for (curr in text.toUpperCase()) {
            currIndex = curr.toInt()
            out += when (curr) {
                ' ' -> " ".repeat(5)
                else -> alphabet[currIndex - 65][i]
            }
        }
        out += '\n'
    }

    return out
}

fun createBorder(upperText: String, bottomText: String, upperHeight: Int, bottomHeight: Int): String {
    var out = ""

    var currLine: String

    val upperLineSize = upperText.length / upperHeight
    val bottomLineSize = bottomText.length / bottomHeight

    val borderLength: Int

    val upperWhiteSpaceCount: Int
    val bottomWhiteSpaceCount: Int

    if (upperLineSize >= bottomLineSize) {
        upperWhiteSpaceCount = 2

        for (i in upperText.indices step upperLineSize) {
            currLine = upperText.substring(i, i + upperLineSize - 1)
            out += "88" + " ".repeat(upperWhiteSpaceCount) + currLine + " ".repeat(upperWhiteSpaceCount) + "88\n"
        }

        bottomWhiteSpaceCount = out.length / upperHeight - bottomLineSize - 4
        for (i in bottomText.indices step bottomLineSize) {
            currLine = bottomText.substring(i, i + bottomLineSize - 1)
            out += "88" + " ".repeat(bottomWhiteSpaceCount / 2) + currLine + " ".repeat((bottomWhiteSpaceCount + 1) / 2) + "88\n"
        }
    } else {
        upperWhiteSpaceCount = bottomText.length / bottomHeight - upperLineSize + 4

        for (i in upperText.indices step upperLineSize) {
            currLine = upperText.substring(i, i + upperLineSize - 1)
            out += "88" + " ".repeat(upperWhiteSpaceCount / 2) + currLine + " ".repeat((upperWhiteSpaceCount + 1) / 2) + "88\n"
        }

        bottomWhiteSpaceCount = 2
        for (i in bottomText.indices step bottomLineSize) {
            currLine = bottomText.substring(i, i + bottomLineSize - 1)
            out += "88" + " ".repeat(bottomWhiteSpaceCount) + currLine + " ".repeat(bottomWhiteSpaceCount) + "88\n"
        }
    }

    borderLength = out.length / (upperHeight + bottomHeight) - 1
    out = "8".repeat(borderLength) + "\n$out" + "8".repeat(borderLength)

    return out
}
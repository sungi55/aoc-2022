import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
fun readInput(name: String) = getFile(name).readLines()
private fun getFile(name: String) = File("input", "$name.txt")
fun readInputText(name: String) = getFile(name).readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

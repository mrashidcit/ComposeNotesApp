@file:JvmName("Log")

package android.util

fun d(tag: String, msg: String): Int {
    println("$tag: $msg")
    return 0
}


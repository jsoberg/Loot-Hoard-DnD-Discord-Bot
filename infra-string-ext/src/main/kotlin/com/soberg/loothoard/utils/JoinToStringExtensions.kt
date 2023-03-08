@file:JvmName("JoinToStringExtensions")

package com.soberg.loothoard.utils

/** Joins the contents of this [List] into a String, where each element is separated by [separator] up until the last element which will be prepended by [lastSeparator].
 *
 * Examples:
 * listOf(1,2,3,4).joinToString(separator = ", ", lastSeparator = ", and ") = "1, 2, 3, and 4"
 * listOf(1).joinToString(separator = ANY, lastSeparator = ANY) = "1"
 */
fun List<Any>.joinToString(
    separator: String,
    lastSeparator: String = separator,
): String {
    val builder = StringBuilder("${first()}")
    if (size == 1) {
        return builder.toString()
    }

    for (i in 1 until size - 1) {
        builder.append(separator)
            .append(get(i))
    }
    return builder.append(lastSeparator)
        .append(last())
        .toString()
}
package com.freez.pixabay.core.util

import java.util.Locale

fun String.capitalizeFirstChar(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(
                Locale.US,
            )
        } else it.toString()
    }
}

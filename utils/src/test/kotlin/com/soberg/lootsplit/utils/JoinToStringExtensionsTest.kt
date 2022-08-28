package com.soberg.lootsplit.utils

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class JoinToStringExtensionsTest {

    @Test
    fun `return just first value for collections of size 1`() {
        assertThat(listOf(1).joinToString(separator = ", ", lastSeparator = ", and")).isEqualTo("1")
    }

    @Test
    fun `use lastSeparator as expected`() {
        val result = listOf(1, 2, 3, 4).joinToString(separator = ", ", lastSeparator = ", and ")
        assertThat(result).isEqualTo("1, 2, 3, and 4")
    }
}
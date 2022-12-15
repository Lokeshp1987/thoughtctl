package com.thoughtctl.util

import com.thoughtctl.util.Utils.getDateFromTimestamp
import org.junit.Assert.*
import org.junit.Test

class UtilsTest
{
    @Test
    fun TestTimeConverter()
    {
        var timeinmiliseconds : Long = 1670590855
        var date : String? =  getDateFromTimestamp(timeinmiliseconds)
        assertEquals("2022-12-09", date)

    }
    @Test
    fun TestTimeNegativeConverter()
    {
        var timeinmiliseconds : Long = -10
        var date : String? =  getDateFromTimestamp(timeinmiliseconds)
        assertEquals("1970-01-01", date)

    }
}
package solutions.alterego.android.unisannio.utils

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

fun DateTime.toIso8601(): String = ISODateTimeFormat.dateTime().print(this)

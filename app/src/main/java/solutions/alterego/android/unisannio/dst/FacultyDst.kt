package solutions.alterego.android.unisannio.dst

import solutions.alterego.android.unisannio.Faculty
import solutions.alterego.android.unisannio.R
import solutions.alterego.android.unisannio.R.string
import solutions.alterego.android.unisannio.Section
import solutions.alterego.android.unisannio.map.UnisannioGeoData
import solutions.alterego.android.unisannio.scienze.ScienzeDetailParser
import solutions.alterego.android.unisannio.scienze.ScienzeParser

val FacultyDst = Faculty(
    nameResource = R.string.scienze,
    website = "http://www.dstunisannio.it/",
    mapMarkers = UnisannioGeoData.SCIENZE(),
    sections = listOf(
        Section(
            string.title_activity_scienze,
            "http://www.dstunisannio.it/index.php/didattica?format=feed&type=rss",
            ScienzeParser()
        )
    ),
    detailParser = ScienzeDetailParser()
)

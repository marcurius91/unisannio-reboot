package solutions.alterego.android.unisannio

import solutions.alterego.android.unisannio.R.string
import solutions.alterego.android.unisannio.map.UniPoint
import solutions.alterego.android.unisannio.map.UnisannioGeoData
import solutions.alterego.android.unisannio.repository.ArticleParser
import solutions.alterego.android.unisannio.repository.Parser
import solutions.alterego.android.unisannio.scienze.ScienzeDetailParser
import solutions.alterego.android.unisannio.interfaces.Parser as OldParser

class Faculty(
    val hearderImage: Int,
    val name: String /* Ingegneria */,
    val website: String /* https://www.ding.unisannio.it/ */,
    val mapMarkers: List<UniPoint>,
    val sections: List<Section>,
    val detailParser: OldParser<String>? = null
)

data class Section(
    val titleResource: Int /* Avvisi Studenti */,
    val url: String /* https://www.ding.unisannio.it/en/avvisi-com/avvisi-didattica# */,
    val parser: Parser
)

val Dst = Faculty(
    hearderImage = 0,
    name = "Scienze e Tecnologia",
    website = "http://www.dstunisannio.it/",
    mapMarkers = UnisannioGeoData.SCIENZE(),
    sections = listOf(
        Section(
            string.title_activity_scienze,
            "http://www.dstunisannio.it/index.php/didattica?format=feed&type=rss",
            ArticleParser()
        )
    ),
    detailParser = ScienzeDetailParser()
)

val Giurisprudenza = Faculty(
    0,
    "Giurisprudenza",
    URLS.GIURISPRUDENZA,
    UnisannioGeoData.GIURISPRUDENZA(),
    listOf(
        Section(
            titleResource = string.title_activity_giurisprudenza,
            url = "http://www.giurisprudenza.unisannio.it/index.php?option=com_rss&catid=2",
            parser = ArticleParser()
        )
    )
)

//val AteneoFaculty = Faculty(
//    R.string.ateneo,
//    URLS.ATENEO,
//    UnisannioGeoData.ATENEO(),
//    listOf(
//        Section(
//            R.string.title_activity_ateneo,
//            URLS.ATENEO_NEWS,
//            AteneoAvvisiParser()
//        ),
//        Section(
//            R.string.title_activity_ateneo_studenti,
//            URLS.ATENEO_STUDENTI_NEWS,
//            AteneoAvvisiParser()
//        )
//    )
//)

//val IngegneriaFaculty = Faculty(
//    R.string.ingegneria,
//    URLS.INGEGNERIA,
//    UnisannioGeoData.INGEGNERIA(),
//    listOf(
//        Section(
//            R.string.title_activity_ingegneria_avvisi_studenti,
//            URLS.INGEGNERIA_NEWS_STUDENTI,
//            IngegneriaAvvisiStudentiParser()
//        ),
//        Section(
//            R.string.title_activity_ingegneria_dipartimento,
//            URLS.INGEGNERIA_NEWS_DIPARTIMENTO,
//            IngegneriaAvvisiDipartimentoParser()
//        )
//    )
//)

//val SeaFaculty = Faculty(
//    R.string.sea,
//    URLS.SEA,
//    UnisannioGeoData.SEA(),
//    listOf(
//        Section(
//            R.string.title_activity_sea,
//            URLS.SEA_NEWS,
//            SeaParser()
//        )
//    )
//)

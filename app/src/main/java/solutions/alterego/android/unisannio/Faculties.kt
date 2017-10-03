package solutions.alterego.android.unisannio

import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiParser
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaParser
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiDipartimentoParser
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiStudentiParser
import solutions.alterego.android.unisannio.interfaces.Parser
import solutions.alterego.android.unisannio.map.UniPoint
import solutions.alterego.android.unisannio.map.UnisannioGeoData
import solutions.alterego.android.unisannio.models.Article
import solutions.alterego.android.unisannio.scienze.ScienzeDetailParser
import solutions.alterego.android.unisannio.scienze.ScienzeParser
import solutions.alterego.android.unisannio.sea.SeaParser

sealed class Faculty(
    val nameResource: Int /* Ingegneria */,
    val mainUrl: String /* https://www.ding.unisannio.it/ */,
    val mapPoints: List<UniPoint>,
    val sections: List<Section>,
    val detailParser: Parser<String>? = null
)

data class Section(
    val titleResource: Int /* Avvisi Studenti */,
    val url: String /* https://www.ding.unisannio.it/en/avvisi-com/avvisi-didattica# */,
    val parser: Parser<Article> /* IngegneriaAvvisiStudentiParser */
)

class AteneoFaculty : Faculty(
    R.string.ateneo,
    URLS.ATENEO,
    UnisannioGeoData.ATENEO(),
    listOf(
        Section(
            R.string.title_activity_ateneo,
            URLS.ATENEO_NEWS,
            AteneoAvvisiParser()
        ),
        Section(
            R.string.title_activity_ateneo_studenti,
            URLS.ATENEO_STUDENTI_NEWS,
            AteneoAvvisiParser()
        )
    )
)

class IngegneriaFaculty : Faculty(
    R.string.ingegneria,
    URLS.INGEGNERIA,
    UnisannioGeoData.INGEGNERIA(),
    listOf(
        Section(
            R.string.title_activity_ingegneria_avvisi_studenti,
            URLS.INGEGNERIA_NEWS_STUDENTI,
            IngegneriaAvvisiStudentiParser()
        ),
        Section(
            R.string.title_activity_ingegneria_dipartimento,
            URLS.INGEGNERIA_NEWS_DIPARTIMENTO,
            IngegneriaAvvisiDipartimentoParser()
        )
    )
)

class ScienzeFaculty : Faculty(
    R.string.scienze,
    URLS.SCIENZE,
    UnisannioGeoData.SCIENZE(),
    listOf(
        Section(
            R.string.title_activity_scienze,
            URLS.SCIENZE_NEWS,
            ScienzeParser()
        )
    ),
    ScienzeDetailParser()
)

class GiurisprudenzaFaculty : Faculty(
    R.string.giurisprudenza,
    URLS.GIURISPRUDENZA,
    UnisannioGeoData.GIURISPRUDENZA(),
    listOf(
        Section(
            R.string.title_activity_giurisprudenza,
            URLS.GIURISPRUDENZA_AVVISI,
            GiurisprudenzaParser()
        ),
        Section(
            R.string.title_activity_giurisprudenza_comunicazioni,
            URLS.GIURISPRUDENZA_COMUNICAZIONI,
            GiurisprudenzaParser()
        )
    )
)

class SeaFaculty : Faculty(
    R.string.sea,
    URLS.SEA,
    UnisannioGeoData.SEA(),
    listOf(
        Section(
            R.string.title_activity_sea,
            URLS.SEA_NEWS,
            SeaParser()
        )
    )
)

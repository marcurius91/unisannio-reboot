package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.ateneo.AteneoRetriever;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaRetriever;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiDipartimentoRetriever;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiStudentiRetriever;
import solutions.alterego.android.unisannio.scienze.ScienzeRetriever;
import solutions.alterego.android.unisannio.sea.SeaRetriever;

@Module
public class RetrieversModule {

    @Provides
    @Singleton
    GiurisprudenzaRetriever provideGiurisprudenzaRetriever() {
        return new GiurisprudenzaRetriever(URLS.GIURISPRUDENZA_AVVISI);
    }

    @Provides
    @Singleton
    SeaRetriever provideSeaRetriever() {
        return new SeaRetriever(URLS.SEA_NEWS);
    }

    @Provides
    @Singleton
    ScienzeRetriever provideScienzeRetriever() {
        return new ScienzeRetriever(URLS.SCIENZE_NEWS);
    }

    @Provides
    @Singleton
    IngegneriaAvvisiDipartimentoRetriever provideIngegneriaDipartimentoRetriever() {
        return new IngegneriaAvvisiDipartimentoRetriever();
    }

    @Provides
    @Singleton
    IngegneriaAvvisiStudentiRetriever provideIngegneriaStudentiRetriever() {
        return new IngegneriaAvvisiStudentiRetriever();
    }

    @Provides
    @Singleton
    AteneoRetriever provideAteneoRetriever() {
        return new AteneoRetriever(URLS.ATENEO_NEWS);
    }
}
package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.ateneo.AteneoRetriever;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaRetriever;
import solutions.alterego.android.unisannio.scienze.ScienzeRetriever;
import solutions.alterego.android.unisannio.sea.SeaRetriever;

@Module
public class RetrieversModule {

    @Provides
    @Singleton
    AteneoRetriever provideAteneoRetriever() {
        return new AteneoRetriever();
    }

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
        return new ScienzeRetriever();
    }

}
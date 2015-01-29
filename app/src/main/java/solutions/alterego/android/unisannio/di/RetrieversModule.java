package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import solutions.alterego.android.unisannio.ateneo.AteneoRetriever;
import solutions.alterego.android.unisannio.utils.CollectionUtils;

@Module
public class RetrieversModule {

    @Provides
    @Singleton
    AteneoRetriever provideAteneoRetriever() {
        return new AteneoRetriever();
    }
}
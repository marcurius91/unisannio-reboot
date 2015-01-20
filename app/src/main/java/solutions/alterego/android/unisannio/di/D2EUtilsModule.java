package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import solutions.alterego.android.unisannio.utils.CollectionUtils;

@Module
public class D2EUtilsModule {

    @Provides
    @Singleton
    CollectionUtils provideStringUtils() {
        return new CollectionUtils();
    }
}
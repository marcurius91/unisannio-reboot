package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiParser;

@Module
public class ParserModule {

    @Provides
    @Singleton
            AteneoAvvisiParser provideAteneoParser(){
            return new AteneoAvvisiParser();
    }
}

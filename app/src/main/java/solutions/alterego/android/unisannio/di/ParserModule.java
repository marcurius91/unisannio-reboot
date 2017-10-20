package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiParser;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiDipartimentoParser;

@Module
public class ParserModule {

    @Provides
    @Singleton
    AteneoAvvisiParser provideAteneoParser(){
            return new AteneoAvvisiParser();
    }

    @Provides
    @Singleton
    IngegneriaAvvisiDipartimentoParser provideIngegneriaDipartimentoParser(){ return new IngegneriaAvvisiDipartimentoParser();}
}

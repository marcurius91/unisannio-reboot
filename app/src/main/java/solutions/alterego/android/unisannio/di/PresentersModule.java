package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaCercapersoneParser;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaCercapersonePresenter;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaCercapersoneRetriever;

@Module
public class PresentersModule {

    @Provides
    @Singleton
    IngegneriaCercapersonePresenter provideIngegneriaCercapersonePresenter(
            IngegneriaCercapersoneParser parser,
            IngegneriaCercapersoneRetriever retriever) {

        IngegneriaCercapersonePresenter presenter = new IngegneriaCercapersonePresenter();
        presenter.setParser(parser);
        presenter.setRetriever(retriever);
        return presenter;
    }
}

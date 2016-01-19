package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.MainActivity;
import solutions.alterego.android.unisannio.ateneo.AteneoActivity;
import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiFragment;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaActivity;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaAvvisiFragment;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaComunicazioniActivity;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiFragment;
import solutions.alterego.android.unisannio.ingegneria.IngengeriaCercapersoneActivity;
import solutions.alterego.android.unisannio.scienze.ScienzeActivity;
import solutions.alterego.android.unisannio.scienze.ScienzeAvvisiFragment;
import solutions.alterego.android.unisannio.sea.SeaActivity;
import solutions.alterego.android.unisannio.sea.SeaAvvisiFragment;

@Singleton
@dagger.Component(modules = {SystemServicesModule.class, RetrieversModule.class, PresentersModule.class, ParserModule.class})
public interface Component {

    void inject(IngengeriaCercapersoneActivity app);

    void inject(MainActivity app);

    void inject(AteneoAvvisiFragment app);

    void inject(IngegneriaAvvisiFragment app);

    void inject(GiurisprudenzaAvvisiFragment app);

    void inject(SeaAvvisiFragment app);

    void inject(ScienzeAvvisiFragment app);

    void inject(AteneoActivity ateneoActivity);

    void inject(ScienzeActivity scienzeActivity);

    void inject(GiurisprudenzaActivity giurisprudenzaActivity);

    void inject(GiurisprudenzaComunicazioniActivity giurisprudenzaComunicazioniActivity);

    void inject(SeaActivity seaActivity);


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    final class Initializer {

        public static Component init(App app) {
            return DaggerComponent.builder()
                    .systemServicesModule(new SystemServicesModule(app))
                    .build();
        }
    }
}

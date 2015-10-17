package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.MainActivity;
import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiFragment;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaAvvisiFragment;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiFragment;
import solutions.alterego.android.unisannio.scienze.ScienzeAvvisiFragment;
import solutions.alterego.android.unisannio.sea.SeaAvvisiFragment;

@Singleton
@dagger.Component(modules = {SystemServicesModule.class, RetrieversModule.class, PresentersModule.class})
public interface Component {

    void inject(App app);

    void inject(MainActivity app);

    void inject(AteneoAvvisiFragment app);

    void inject(IngegneriaAvvisiFragment app);

    void inject(GiurisprudenzaAvvisiFragment app);

    void inject(SeaAvvisiFragment app);

    void inject(ScienzeAvvisiFragment app);

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final static class Initializer {

        public static Component init(App app) {
            return DaggerComponent.builder()
                    .systemServicesModule(new SystemServicesModule(app))
                    .build();
        }
    }
}
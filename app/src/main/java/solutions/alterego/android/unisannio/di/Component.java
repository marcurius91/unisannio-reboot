package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import solutions.alterego.android.unisannio.MainActivity;
import solutions.alterego.android.unisannio.UnisannioApplication;
import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiFragment;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaAvvisiFragment;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiFragment;

@Singleton
@dagger.Component(modules = {SystemServicesModule.class, RetrieversModule.class})
public interface Component {

    void inject(UnisannioApplication app);

    void inject(MainActivity app);

    void inject(AteneoAvvisiFragment app);

    void inject(IngegneriaAvvisiFragment app);

    void inject(GiurisprudenzaAvvisiFragment app);

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final static class Initializer {

        public static Component init(UnisannioApplication app) {
            return Dagger_Component.builder()
                    .systemServicesModule(new SystemServicesModule(app))
                    .build();
        }
    }
}
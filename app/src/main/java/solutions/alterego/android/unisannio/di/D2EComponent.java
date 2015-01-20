package solutions.alterego.android.unisannio.di;

import javax.inject.Singleton;

import dagger.Component;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import solutions.alterego.android.unisannio.MainActivity;
import solutions.alterego.android.unisannio.UnisannioApplication;
import solutions.alterego.android.unisannio.utils.CollectionUtils;

@Singleton
@Component(modules = {SystemServicesModule.class, D2EUtilsModule.class})
public interface D2EComponent {

    void inject(UnisannioApplication app);

    void inject(MainActivity app);

    CollectionUtils getD2EStringUtils();

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final static class Initializer {

        public static D2EComponent init(UnisannioApplication app) {
            return Dagger_D2EComponent.builder()
                    .systemServicesModule(new SystemServicesModule(app))
                    .build();
        }

    }
}
package solutions.alterego.android

import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations
import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.plugins.RxJavaHooks
import rx.schedulers.Schedulers

open class MockitoRxTest {
    @Before open fun setup() {
        MockitoAnnotations.initMocks(this)

        RxJavaHooks.setOnIOScheduler { Schedulers.immediate() }
        RxAndroidPlugins.getInstance().registerSchedulersHook(object : RxAndroidSchedulersHook() {
            override fun getMainThreadScheduler(): Scheduler? {
                return Schedulers.immediate()
            }
        })
    }

    @After
    fun tearDown() {
        RxJavaHooks.reset()
        RxAndroidPlugins.getInstance().reset()
    }
}

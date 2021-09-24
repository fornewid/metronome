package soup.compose.experimental.sample

import android.app.Application
import logcat.AndroidLogcatLogger
import logcat.LogPriority

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
    }
}

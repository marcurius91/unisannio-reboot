package solutions.alterego.android.unisannio.navigation

import android.app.Activity
import android.content.Intent
import android.support.v4.app.TaskStackBuilder
import solutions.alterego.android.unisannio.ateneo.AteneoActivity

class Navigator(val activityContext: Activity) {

    fun toAteneo() {
        val ateneo = Intent(activityContext, AteneoActivity::class.java)
        activityContext.startActivity(ateneo);
    }

    fun toAteneoStudenti() {
        val ateneo = Intent(activityContext, AteneoActivity::class.java)
        ateneo.putExtra("STUDENTI", true)
        activityContext.startActivity(ateneo);
    }

    fun toIngegneria() {

    }

    fun upToParent() {
        val intent = activityContext.getParentActivityIntent()
        if (intent == null) {
            activityContext.finish()
            return
        }

        if (activityContext.shouldUpRecreateTask(intent)) {
            TaskStackBuilder.create(activityContext)
                    .addParentStack(activityContext)
                    .startActivities()
        } else {
            activityContext.navigateUpTo(intent)
        }
    }
}

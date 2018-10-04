package solutions.alterego.android.unisannio.navigation

import android.app.Activity
import android.content.Intent
import android.support.v4.app.TaskStackBuilder
import solutions.alterego.android.unisannio.ateneo.AteneoActivity
import solutions.alterego.android.unisannio.ateneo.AteneoStudentiActivity
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaActivity
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiStudentiActivity
import solutions.alterego.android.unisannio.ingegneria.IngegneriaDipartimentoActivity
import solutions.alterego.android.unisannio.ingegneria.IngengeriaCercapersoneActivity
import solutions.alterego.android.unisannio.scienze.ScienzeActivity
import solutions.alterego.android.unisannio.sea.SeaActivity

class Navigator(val activityContext: Activity) {

    fun toAteneo() {
        val ateneo = Intent(activityContext, AteneoActivity::class.java)
        activityContext.startActivity(ateneo);
        activityContext.overridePendingTransition(0,0);
    }

    fun toAteneoStudenti() {
        val ateneo_studenti = Intent(activityContext, AteneoStudentiActivity::class.java)
        activityContext.startActivity(ateneo_studenti);
        activityContext.overridePendingTransition(0,0);
    }

    fun toIngegneriaDipartimento() {
        val ingegneriaDipartimento = Intent(activityContext, IngegneriaDipartimentoActivity::class.java);
        activityContext.startActivity(ingegneriaDipartimento);
        activityContext.overridePendingTransition(0,0);
    }

    fun toIngegneriaStudenti(){
        val ingegneriaStudenti = Intent(activityContext, IngegneriaAvvisiStudentiActivity::class.java);
        activityContext.startActivity(ingegneriaStudenti);
        activityContext.overridePendingTransition(0,0);
    }

    fun toIngegneriaCercapersone(){
        val cercapersone_ingegneria = Intent(activityContext, IngengeriaCercapersoneActivity::class.java);
        activityContext.startActivity(cercapersone_ingegneria);
        activityContext.overridePendingTransition(0,0);
    }

    fun toScienzeStudenti(){
        val scienze_studenti = Intent(activityContext, ScienzeActivity::class.java);
        activityContext.startActivity(scienze_studenti);
        activityContext.overridePendingTransition(0,0);
    }

    fun toGiurisprudenzaStudenti(){
        val giurisprudenza = Intent(activityContext, GiurisprudenzaActivity::class.java);
        activityContext.startActivity(giurisprudenza);
        activityContext.overridePendingTransition(0,0);
    }

    fun toSeaStudenti(){
        val sea = Intent(activityContext,SeaActivity::class.java);
        activityContext.startActivity(sea);
        activityContext.overridePendingTransition(0,0);
    }

    fun toAlterego(){

    }

    fun toGithub(){

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

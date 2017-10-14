package solutions.alterego.android.unisannio.dst

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_unisannio_reboot.message
import kotlinx.android.synthetic.main.activity_unisannio_reboot.navigation
import solutions.alterego.android.unisannio.R

class UnisannioReboot : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_ateneo -> {
                message.setText(R.string.ateneo)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_ingegneria -> {
                message.setText(R.string.ingegneria)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_scienze -> {
                message.setText(R.string.scienze)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sea -> {
                message.setText(R.string.sea)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_giurisprudenza -> {
                message.setText(R.string.giurisprudenza)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unisannio_reboot)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}

package solutions.alterego.android.unisannio.ingegneria

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_ingengeria_cercapersone.cercapersone_ingegneria_recycle_view
import kotlinx.android.synthetic.main.activity_ingengeria_cercapersone.cercapersone_ingegneria_swipe_container
import kotlinx.android.synthetic.main.activity_ingengeria_cercapersone.searchView_Cercapersone_Ingegneria
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import solutions.alterego.android.unisannio.NavigationDrawerActivity
import solutions.alterego.android.unisannio.R
import solutions.alterego.android.unisannio.analytics.AnalyticsManager
import solutions.alterego.android.unisannio.cercapersone.CercapersoneAdapter
import solutions.alterego.android.unisannio.cercapersone.Person
import solutions.alterego.android.unisannio.cercapersone.SearchPerson
import solutions.alterego.android.unisannio.navigation.NavigationViewManager
import java.util.ArrayList
import javax.inject.Inject

class IngengeriaCercapersoneActivity : NavigationDrawerActivity() {

    @Inject lateinit var mAnalyticsManager: AnalyticsManager

    private var mAdapter: CercapersoneAdapter? = null
    private val mPersons = ArrayList<Person>()
    private val drawerLayout: DrawerLayout? = null
    private val navigationView: NavigationView? = null
    private var personToSearch: String? = null
    internal var navigationViewManager: NavigationViewManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ingengeria_cercapersone)
        //App.component(this).inject(this);

        /*
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        */

        //Initializing NavigationView
        //navigationView = (NavigationView) findViewById(R.id.navigation_view);

        val toolbar : Toolbar = findViewById(R.id.toolbar_actionbar)
        setSupportActionBar(toolbar)
        val drawer: DrawerLayout = findViewById(R.id.navigation_drawer)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val icp = IngegneriaCercapersonePresenter()

        cercapersone_ingegneria_recycle_view.layoutManager = LinearLayoutManager(this)
        mAdapter = CercapersoneAdapter(ArrayList(), R.layout.ingegneria_cercapersone_list_person)
        cercapersone_ingegneria_recycle_view.adapter = mAdapter

        cercapersone_ingegneria_swipe_container.setColorSchemeColors(ContextCompat.getColor(this, R.color.primaryColor))
        cercapersone_ingegneria_swipe_container.setProgressViewOffset(false, 0,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, resources.displayMetrics).toInt())

        // Progress
        cercapersone_ingegneria_swipe_container.isEnabled = false
        cercapersone_ingegneria_swipe_container.isRefreshing = false
        cercapersone_ingegneria_recycle_view.visibility = View.GONE

        cercapersone_ingegneria_recycle_view.visibility = View.VISIBLE

        searchView_Cercapersone_Ingegneria.queryHint = "Cerca Persona"

        searchView_Cercapersone_Ingegneria.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                personToSearch = query

                icp.people.observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<ArrayList<Person>> {
                    override fun onCompleted() {
                        cercapersone_ingegneria_swipe_container.isRefreshing = false
                    }

                    override fun onError(e: Throwable) {
                        Log.e("ACTIVITY CERCAPERSONE", e.toString())
                    }

                    override fun onNext(persons: ArrayList<Person>) {
                        val prsSrc = SearchPerson.searchPerson(personToSearch, persons)
                        mAdapter!!.addPersons(prsSrc)
                        cercapersone_ingegneria_swipe_container.isRefreshing = false
                    }
                })
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        /*
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_cercapersone);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close) {


            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        */

        //TODO Fix bug on next lines (We don't have a fragment to replace)
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, AteneoAvvisiFragment.newInstance(false)).commit();
        //navigationViewManager = new NavigationViewManager(drawerLayout,this);
        //navigationView = navigationViewManager.setUpNavigationDrawer(navigationView);

    }

    override fun getNavigationDrawerMenuIdForThisActivity(): Int {
        return R.id.cercapersone_ingegneria
    }

    override fun onAppbarNavigationClick() {
        openNavigationDrawer()
    }
}

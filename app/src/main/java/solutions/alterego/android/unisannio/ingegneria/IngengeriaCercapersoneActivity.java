package solutions.alterego.android.unisannio.ingegneria;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.cercapersone.CercapersoneAdapter;
import solutions.alterego.android.unisannio.cercapersone.Person;
import solutions.alterego.android.unisannio.cercapersone.SearchPerson;
import butterknife.ButterKnife;


public class IngengeriaCercapersoneActivity extends FragmentActivity {

    @Bind(R.id.cercapersone_ingegneria_recycle_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.cercapersone_ingegneria_swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CercapersoneAdapter mAdapter;
    private ArrayList<Person> mPersons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingengeria_cercapersone);

        SearchView sv = (SearchView) findViewById(R.id.searchViewCercapersoneIngegneria);

        mRecyclerView = (RecyclerView) findViewById(R.id.cercapersone_ingegneria_recycle_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.cercapersone_ingegneria_swipe_container);

        //error on this.getApplicationContext() TODO repair Error
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CercapersoneAdapter(new ArrayList<>(),R.layout.ingegneria_cercapersone_list_person);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryColor));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        // Progress
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);



        IngegneriaCercapersonePresenter icp = new IngegneriaCercapersonePresenter();

        mRecyclerView.setVisibility(View.VISIBLE);

        icp.getPeople()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Person>>() {
                    @Override
                    public void onCompleted() {
                        //Log.e("ACTIVITY CERCAPERSONE onCompleted()","Completed");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ACTIVITY CERCAPERSONE onError()",e.toString());
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ArrayList<Person> persons) {
                        mPersons = persons;
                        mAdapter.addPersons(persons);
                        /*String search = "Glielmo";

                        ArrayList<Person> prsSrc = SearchPerson.searchPerson(search,persons);
                        for(Person p: prsSrc){
                            //Log.e("ACTIVITY CERCAPERSONE onNext()",p.getNome());
                        }*/


                    }
                });



    }
}

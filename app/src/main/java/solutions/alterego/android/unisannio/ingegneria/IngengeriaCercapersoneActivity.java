package solutions.alterego.android.unisannio.ingegneria;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.cercapersone.Person;

public class IngengeriaCercapersoneActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingengeria_cercapersone);

        TextView tv = (TextView) findViewById(R.id.tvCercapersoneIngegneria);
        SearchView sv = (SearchView) findViewById(R.id.searchViewCercapersoneIngegneria);

        IngegneriaCercapersonePresenter icp = new IngegneriaCercapersonePresenter();

        icp.getPeople();

    }
}

package solutions.alterego.android.unisannio.ingegneria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import solutions.alterego.android.unisannio.R;

public class IngengeriaCercapersoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup container = ((ViewGroup) this.findViewById(R.id.container));
        getLayoutInflater().inflate(R.layout.fragment_ingegneria, container);
    }
}

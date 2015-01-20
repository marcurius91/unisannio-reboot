package solutions.alterego.android.unisannio.example1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import solutions.alterego.android.unisannio.R;

public class FirstExampleFragment extends Fragment {


    public FirstExampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_example, container, false);
    }


}

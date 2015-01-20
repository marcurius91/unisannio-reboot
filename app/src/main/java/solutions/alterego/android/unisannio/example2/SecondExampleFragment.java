package solutions.alterego.android.unisannio.example2;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import solutions.alterego.android.unisannio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondExampleFragment extends Fragment {


    public SecondExampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_example, container, false);
    }


}

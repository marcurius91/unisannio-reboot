package solutions.alterego.android.unisannio.ingegneria;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.UnisannioApplication;

public class IngegneriaAvvisiFragment extends Fragment {

    @InjectView(R.id.ingegneria_recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.ingengeria_ptr)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    IngegneriaRetriever mRetriever;

    private IngegneriaAdapter mAdapter;

    private boolean mIsStudenti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingegneria, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.inject(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.unisannio_yellow,
                R.color.unisannio_yellow_dark,
                R.color.unisannio_yellow_light,
                R.color.unisannio_blue);

        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Bundle bundle = getArguments();
        if (bundle != null) {
            mIsStudenti = bundle.getBoolean("STUDENTI");
        }

        mSwipeRefreshLayout.setOnRefreshListener(this::refreshList);

        mAdapter = new IngegneriaAdapter(new ArrayList<>(), R.layout.ingegneria_card);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        refreshList();
    }

    private void refreshList() {
        mRecyclerView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);

        mRetriever.get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<IngegneriaDidatticaItem>>() {
                    @Override
                    public void onCompleted() {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<IngegneriaDidatticaItem> list) {
                        mAdapter.addNews(list);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        UnisannioApplication.component(activity).inject(this);
    }

}

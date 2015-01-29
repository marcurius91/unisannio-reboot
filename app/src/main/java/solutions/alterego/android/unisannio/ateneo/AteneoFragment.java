package solutions.alterego.android.unisannio.ateneo;

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

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;

public class AteneoFragment extends Fragment {

    @InjectView(R.id.ateneo_recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.ateneo_ptr)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private AteneoAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ateneo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.inject(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.unisannio_yellow,
                R.color.unisannio_yellow_dark,
                R.color.unisannio_yellow_light,
                R.color.unisannio_blue);

        mSwipeRefreshLayout.setOnRefreshListener(this::refreshList);

        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mAdapter = new AteneoAdapter(new ArrayList<>(), R.layout.ateneo_card);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        refreshList();
    }

    private void refreshList() {
        mRecyclerView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);

        AteneoRetriever.getNewsList(URLS.ATENEO_NEWS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AteneoNews>>() {
                    @Override
                    public void onCompleted() {
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onNext(List<AteneoNews> ateneoNewses) {
                        mAdapter.addNews(ateneoNewses);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

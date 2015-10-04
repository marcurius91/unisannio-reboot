package solutions.alterego.android.unisannio.ingegneria;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.models.Article;

public class IngegneriaAvvisiFragment extends Fragment {

    @Bind(R.id.ingegneria_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.ingengeria_ptr)
    SwipeRefreshLayout mSwipeRefreshLayout;

    IngegneriaRetriever mRetriever;

    private IngegneriaAdapter mAdapter;

    public static IngegneriaAvvisiFragment newInstance(boolean isDipartimento) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("DIPARTIMENTO", isDipartimento);

        IngegneriaAvvisiFragment fragment = new IngegneriaAvvisiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingegneria, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

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
            boolean isDipartimento = bundle.getBoolean("DIPARTIMENTO");

            if (isDipartimento) {
                mRetriever = new IngegneriaAvvisiDipartimentoRetriever();
            } else {
                mRetriever = new IngegneriaAvvisiStudentiRetriever();
            }
        }

        mSwipeRefreshLayout.setOnRefreshListener(this::refreshList);

        mAdapter = new IngegneriaAdapter(this, new ArrayList<>());
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
            .subscribe(new Observer<List<Article>>() {
                @Override
                public void onCompleted() {
                    if (mRecyclerView != null && mSwipeRefreshLayout != null) {
                        mRecyclerView.setVisibility(View.VISIBLE);
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
                public void onNext(List<Article> list) {
                    mAdapter.addNews(list);
                }
            });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        App.component(context).inject(this);
    }
}

package com.example.san.gsonandvolley.ui.main;


import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.san.gsonandvolley.R;
import com.example.san.gsonandvolley.base.BaseActivity;
import com.example.san.gsonandvolley.base.CustomLoadMoreView;
import com.example.san.gsonandvolley.model.movie.Search;
import com.example.san.gsonandvolley.ui.detail.MovieDetailActivity;
import com.example.san.gsonandvolley.untils.CheckConnectionInternet;

import java.util.List;

import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_LEFT;
import static com.example.san.gsonandvolley.untils.Constants.KEY.IDMOVIE;
import static com.example.san.gsonandvolley.untils.Constants.KEY.S_PARAMETER;


public class MainActivity extends BaseActivity implements IMainView {
    private FloatingSearchView mSearchView;
    private NavigationView mNavigation;
    private DrawerLayout mDrawerLayout;
    private MainPst mManPst;
    private RecyclerView mRecyclerMovie;
    private Button mButtonLoadMore;
    private ProgressBar mProgressBarLoadMore;
    private MovieAdapter mAdapter = null;
    private LinearLayoutManager mLayoutManager;
    private int mPage = 1;
    private View mFooterView = null, mReplaceView = null;
    private boolean isLoadMore = true, isLoading = false;

    //    List<MovieTypeSection> mListTypeMovie = null;
//    List<MovieTypeSection> mListPageMovie = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mSearchView.setOnSearchListener(mSearchClick);
//        mRecyclerMovie.addOnScrollListener(mOnScrollListener);
        mSearchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
        mSearchView.setOnMenuItemClickListener(mOnMenuItemClick);
        mAdapter.setOnItemClickListener(mItemClickListener);
        mAdapter.setOnLoadMoreListener(mLoadMoreListener, mRecyclerMovie);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        itemTouchHelper.attachToRecyclerView(mRecyclerMovie);

// open drag
        mAdapter.enableDragItem(itemTouchHelper);
        mAdapter.setOnItemDragListener(onItemDragListener);

// open slide to delete
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
    }

    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
        }
    };

    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
        }
    };

    private BaseQuickAdapter.OnItemClickListener mItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (CheckConnectionInternet.isNetworkConnected(getApplicationContext())) {
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra(IDMOVIE, ((Search) adapter.getItem(position)).getImdbID());
                startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
        }
    };

    private BaseQuickAdapter.RequestLoadMoreListener mLoadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            if (!isLoading) {
                isLoading = true;
                mManPst.getDataMovie(MainActivity.this, S_PARAMETER, mSearchView.getQuery(), String.valueOf(mPage));
            }
        }
    };

    private FloatingSearchView.OnMenuItemClickListener mOnMenuItemClick = new FloatingSearchView.OnMenuItemClickListener() {
        @Override
        public void onActionMenuItemSelected(MenuItem item) {
            mSearchView.clearSearchFocus();
            if (item.getItemId() == R.id.action_menu_search)
                mManPst.getDataMovie(getApplicationContext(), S_PARAMETER, mSearchView.getQuery(), String.valueOf(mPage));
        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mPage != 1 && isLoading != true && isLoadMore
                    && ((mLayoutManager.findLastCompletelyVisibleItemPosition() + 1) == mLayoutManager.getItemCount())) {
                isLoading = true;
                if (mFooterView == null)
                    mFooterView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footer_progressbar, null);
                mProgressBarLoadMore = mFooterView.findViewById(R.id.progressBar_load_more);
                mButtonLoadMore = mFooterView.findViewById(R.id.button_load_more);
                mButtonLoadMore.setVisibility(View.VISIBLE);
                mProgressBarLoadMore.setVisibility(View.INVISIBLE);
                mAdapter.addFooterView(mFooterView);
                mButtonLoadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAdapter.removeAllFooterView();
                        mManPst.getDataMovie(MainActivity.this, S_PARAMETER, mSearchView.getQuery(), String.valueOf(mPage));
                    }
                });
            }
        }
    };


    private FloatingSearchView.OnSearchListener mSearchClick = new FloatingSearchView.OnSearchListener() {
        @Override
        public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

        }

        @Override
        public void onSearchAction(final String currentQuery) {
            mPage = 1;
            isLoading = true;
            isLoadMore = true;
            mManPst.getDataMovie(MainActivity.this, S_PARAMETER, currentQuery, "1");
            showDialog();
        }
    };

    private void findViewsById() {
        mManPst = new MainPst(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        mNavigation = (NavigationView) findViewById(R.id.navigation_main);
        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        mRecyclerMovie = (RecyclerView) findViewById(R.id.recycler_movie_main);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerMovie.setLayoutManager(mLayoutManager);
        mRecyclerMovie.setHasFixedSize(true);
        mAdapter = new MovieAdapter(getApplicationContext(), R.layout.item_movie, null);
        ///**********Adapter Section*******///
//        mListTypeMovie = new ArrayList<>();
//        mAdapter = new MovieAdapter(R.layout.item_movie,R.layout.view_section_head,mListTypeMovie);
        mRecyclerMovie.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        mAdapter.setEnableLoadMore(true);
        mSearchView.setShowMoveUpSuggestion(true);


    }


    @Override
    public void onRequestSearhSuccess(final List<Search> movies, String totalResults) {
///***********section**************///
//        int totalPage = (Integer.parseInt(totalResults) % 10) == 0 ? (Integer.parseInt(totalResults) / 10):((Integer.parseInt(totalResults) / 10)+1);
//        mListTypeMovie.add(new MovieTypeSection(true,"Page "+mPage+"/"+totalPage));
//        mAdapter.notifyDataSetChanged();
//        for(int i = 0; i < movies.size(); i++){
//            mListTypeMovie.add(new MovieTypeSection(movies.get(i)));
//            mAdapter.notifyDataSetChanged();
//
//        }
        ///*********Asynchronous********///
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//        if (movies != null) {
//            if (mAdapter == null) {
//                mAdapter = new MovieAdapter(getApplicationContext(), R.layout.item_movie, movies);
//                mRecyclerMovie.setAdapter(mAdapter);
//                mAdapter.openLoadAnimation(SLIDEIN_LEFT);
//                mAdapter.setOnItemClickListener(mItemClickListener);
//                mAdapter.isFirstOnly(false);
//                mAdapter.setEnableLoadMore(true);
//                mAdapter.setOnLoadMoreListener(mLoadMoreListener, mRecyclerMovie);
//                mAdapter.setLoadMoreView(new CustomLoadMoreView());
//            } else {
//                if (mPage != 1) {
//                    Log.i("HAHA", "onRequestSearhSuccess: " + mPage);
//                    mAdapter.addData(movies);
//                    mAdapter.loadMoreComplete();
//                } else {
//                    Log.i("HAHA", "onRequestSearhSuccess1323: " + mPage);
//                    mAdapter.setNewData(movies);
//                }
//            }
//            mPage++;
//        } else {
//            mAdapter.loadMoreFail();
//        }
//            }
//        });
        if (movies != null) {
            if (mPage != 1) {
                mAdapter.addData(movies);
                mAdapter.loadMoreComplete();
            } else {
                mAdapter.setNewData(movies);
            }
            mPage++;
        } else {
            mAdapter.loadMoreFail();
        }

        if (Integer.parseInt(totalResults) <= (mPage - 1) * 10) {
            isLoadMore = false;
            mAdapter.loadMoreEnd();
        }

        isLoading = false;
        hideDialog();
    }

    @Override
    public void onRequestSearhError(final String msg) {
        if (mPage != 1) mAdapter.loadMoreFail();
        else Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        isLoading = false;
        hideDialog();
///*********Asynchronous********///
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mAdapter != null && isLoading) mAdapter.loadMoreFail();
//                isLoading = false;
//                hideDialog();
//            }
//        });
    }

}



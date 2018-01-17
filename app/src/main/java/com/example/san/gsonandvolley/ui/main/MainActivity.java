package com.example.san.gsonandvolley.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.san.gsonandvolley.R;
import com.example.san.gsonandvolley.base.BaseActivity;
import com.example.san.gsonandvolley.model.movie.Search;
import com.example.san.gsonandvolley.ui.detail.MovieDetailActivity;

import java.util.List;

import static com.example.san.gsonandvolley.untils.Constants.KEY.IDMOVIE;
import static com.example.san.gsonandvolley.untils.Constants.KEY.S_PARAMETER;


public class MainActivity extends BaseActivity implements IMainView,MovieAdapter.OnItemClickListener {
    private FloatingSearchView mSearchView;
    private NavigationView mNavigation;
    private DrawerLayout mDrawerLayout;
    private MainPst mManPst;
    private RecyclerView mRecyclerMovie;
    private MovieAdapter mAdapter = null;
    private int mPage = 1;
    private boolean isLoadMore = true, isLoad = false;
    private String mKeyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mSearchView.setOnSearchListener(mSearchClick);
        mRecyclerMovie.addOnScrollListener(mOnScrollListener);
        mSearchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
        mSearchView.setOnMenuItemClickListener(mOnMenuItemClick);
    }

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
            GridLayoutManager manager = (GridLayoutManager) mRecyclerMovie.getLayoutManager();
            if (mPage != 1 && isLoad != true && isLoadMore
                    && ((manager.findLastCompletelyVisibleItemPosition() + 1) == manager.getItemCount())) {
                isLoad = true;
                mManPst.getDataMovie(getApplicationContext(), S_PARAMETER, mKeyword, String.valueOf(mPage));
            }
        }
    };


    private FloatingSearchView.OnSearchListener mSearchClick = new FloatingSearchView.OnSearchListener() {
        @Override
        public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

        }

        @Override
        public void onSearchAction(String currentQuery) {
            mPage = 1;
            isLoad = true;
            isLoadMore = true;
            mKeyword = currentQuery;
            mManPst.getDataMovie(MainActivity.this, S_PARAMETER, currentQuery, "1");
            showDialog();
        }
    };

    private void findViewsById() {
        mManPst = new MainPst(this);
        mDrawerLayout = findViewById(R.id.drawer_layout_main);
        mNavigation = findViewById(R.id.navigation_main);
        mSearchView = findViewById(R.id.floating_search_view);
        mRecyclerMovie = findViewById(R.id.recycler_movie_main);
        mRecyclerMovie.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        mRecyclerMovie.setHasFixedSize(true);
    }

    @Override
    public void onRequestSearhSuccess(List<Search> movies, String totalResults) {
        if (Integer.parseInt(totalResults) <= mPage * 10) isLoadMore = false;
        if (movies != null) {
            if (mAdapter == null) {
                mAdapter = new MovieAdapter(getApplicationContext(), movies, this);
                mRecyclerMovie.setAdapter(mAdapter);
            } else {
                if (mPage != 1) {
                    mAdapter.add(movies);
                } else mAdapter.setmMovies(movies);
            }
            ++mPage;
        }
        isLoad = false;
        hideDialog();
    }

    @Override
    public void onRequestSearhError(String msg) {
        Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        isLoad = false;
        hideDialog();
    }

    @Override
    public void onItemClick(Search item) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra(IDMOVIE, item.getImdbID());
        startActivity(intent);
    }
}

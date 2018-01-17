package com.example.san.gsonandvolley.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.san.gsonandvolley.R;
import com.example.san.gsonandvolley.base.BaseActivity;
import com.example.san.gsonandvolley.model.movie.Movie;
import com.example.san.gsonandvolley.ui.main.MainPst;
import com.squareup.picasso.Picasso;

import static com.example.san.gsonandvolley.untils.Constants.KEY.IDMOVIE;
import static com.example.san.gsonandvolley.untils.Constants.KEY.I_PARAMETER;


public class MovieDetailActivity extends BaseActivity implements IMovieDetail {
    private ImageView mImageMovie;
    private TextView mTextTitle, mTextYear, mTextAwards, mTextLanguage, mTextCountry, mTextActor, mTextType, mTextGenre, mTextWriter, mTextDirector, mTextProduction, mTextReleased, mTextWebsite, mTextRuntime, mTextRated, mTextPlot, mTextMetascore, mTextBoxOffice;

    private MainPst mMainPst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        findViewsById();
        mMainPst = new MainPst(this);
        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra(IDMOVIE);
            mMainPst.getDataMovie(getApplicationContext(), I_PARAMETER, id,"1");
            showDialog();
        }
    }

    private void findViewsById() {
        mImageMovie = findViewById(R.id.image_movie_detail);
        mTextActor = findViewById(R.id.text_actor_movie_detail);
        mTextCountry = findViewById(R.id.text_country_movie_detail);
        mTextDirector = findViewById(R.id.text_director_movie_detail);
        mTextGenre = findViewById(R.id.text_genre_movie_detail);
        mTextLanguage = findViewById(R.id.text_language_movie_detail);
        mTextProduction = findViewById(R.id.text_production_movie_detail);
        mTextAwards = findViewById(R.id.text_awards_movie_detail);
        mTextReleased = findViewById(R.id.text_released_movie_detail);
        mTextTitle = findViewById(R.id.text_title_movie_detail);
        mTextType = findViewById(R.id.text_type_movie_detail);
        mTextWebsite = findViewById(R.id.text_website_movie_detail);
        mTextYear = findViewById(R.id.text_year_movie_detail);
        mTextWriter = findViewById(R.id.text_writer_movie_detail);
        mTextBoxOffice = findViewById(R.id.text_boxOffice_movie_detail);
        mTextRated = findViewById(R.id.text_rated_movie_detail);
        mTextRuntime = findViewById(R.id.text_runtime_movie_detail);
        mTextMetascore = findViewById(R.id.text_metascore_movie_detail);
        mTextPlot = findViewById(R.id.text_plot_movie_detail);
    }

    @Override
    public void onRequestSearhSuccess(Movie movie) {
        hideDialog();
        Picasso.with(MovieDetailActivity.this)
                .load(movie.getPoster())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_image_error)
                .fit()
                .into(mImageMovie);
        mTextActor.setText("Actor: " + movie.getActors());
        mTextCountry.setText("Country: " + movie.getCountry());
        mTextDirector.setText("Director: " + movie.getDirector());
        mTextGenre.setText("Genre: " + movie.getGenre());
        mTextLanguage.setText("Language: " + movie.getLanguage());
        mTextProduction.setText("Production: " + movie.getProduction());
        mTextAwards.setText("Awards: " + movie.getAwards());
        mTextReleased.setText("Released: " + movie.getReleased());
        mTextTitle.setText("Title: " + movie.getTitle());
        mTextType.setText("Type: " + movie.getType());
        mTextWebsite.setText("Website: " + movie.getWebsite());
        mTextYear.setText("Year: " + movie.getYear());
        mTextWriter.setText("Writer: " + movie.getWriter());
        mTextRuntime.setText("Runtime: " + movie.getRuntime());
        mTextRated.setText("Rated: " + movie.getRated());
        mTextPlot.setText("Plot: " + movie.getPlot());
        mTextMetascore.setText("Meta score: " + movie.getMetascore());
        mTextBoxOffice.setText("BoxOffice: " + movie.getBoxOffice());
    }

    @Override
    public void onRequestSearhError(String msg) {
        hideDialog();
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

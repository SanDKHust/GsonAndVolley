package com.example.san.gsonandvolley.model.movie;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.example.san.gsonandvolley.model.movie.Search;

/**
 * Created by san on 18/01/2018.
 */

public class MovieTypeSection extends SectionEntity<Search> {
    private boolean isMore;
    public MovieTypeSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MovieTypeSection(Search movie) {
        super(movie);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }
}

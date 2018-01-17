package com.example.san.gsonandvolley.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.san.gsonandvolley.R;
import com.example.san.gsonandvolley.model.movie.Search;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by San on 1/13/2018.
 */

//public class MovieAdapter extends BaseAdapter {
//    Context context;
//    private List<Search> mMovies;
//
//    public MovieAdapter(Context context, List<Search> mMovies) {
//        this.context = context;
//        this.mMovies = mMovies;
//    }
//
//    @Override
//    public int getCount() {
//        return mMovies.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mMovies.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(context);
////            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.item_movie, null);
//            holder = new ViewHolder();
//            holder.imageMovie = convertView.findViewById(R.id.image_item_movie);
//            holder.textTitleMovie = convertView.findViewById(R.id.text_title_item_movie);
//            holder.textYearMovie = convertView.findViewById(R.id.text_year_item_movie);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        Search movie = (Search) getItem(position);
//
//        Picasso.with(context)
//                .load(movie.getPoster())
//                .placeholder(R.drawable.ic_placeholder)
//                .error(R.drawable.ic_image_error)
//                .fit()
//                .into(holder.imageMovie);
//        holder.textTitleMovie.setText("Title: " + movie.getTitle());
//        holder.textYearMovie.setText("Year: " + movie.getYear());
//        return convertView;
//    }
//
//    public void add(List<Search> moives){
//        mMovies.addAll(moives);
//        notifyDataSetChanged();
//    }
//
//    public String getIdMovie(int position) {
//        return mMovies.get(position).getImdbID();
//    }
//
//
//    public void setmMovies(List<Search> mMovies) {
//        this.mMovies = mMovies;
//        notifyDataSetChanged();
//    }
//
//    class ViewHolder{
//        public TextView textTitleMovie,textYearMovie;
//        public ImageView imageMovie;
//    }
//
//}

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    private List<Search> mMovies;
    private final OnItemClickListener listener;

    public MovieAdapter(Context context, List<Search> mMovies, OnItemClickListener listener) {
        this.context = context;
        this.mMovies = mMovies;
        this.listener = listener;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Search movie = mMovies.get(position);
        Picasso.with(context)
                .load(movie.getPoster())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_image_error)
                .fit()
                .into(holder.imageMovie);
        holder.textTitleMovie.setText("Title: " + movie.getTitle());
        holder.textYearMovie.setText("Year: " + movie.getYear());
        holder.onClick(movie,listener);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void add(List<Search> moives){
        mMovies.addAll(moives);
        notifyDataSetChanged();
    }


    public void setmMovies(List<Search> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }
    

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textTitleMovie,textYearMovie;
        public ImageView imageMovie;

        public ViewHolder(View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.image_item_movie);
            textTitleMovie = itemView.findViewById(R.id.text_title_item_movie);
            textYearMovie = itemView.findViewById(R.id.text_year_item_movie);
        }

        public void onClick(final Search item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Search item);
    }


}


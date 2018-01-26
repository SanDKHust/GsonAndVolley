package com.example.san.gsonandvolley.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.san.gsonandvolley.R;
import com.example.san.gsonandvolley.model.movie.MovieTypeSection;
import com.example.san.gsonandvolley.model.movie.Search;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by San on 1/13/2018.
 */

public class MovieAdapter extends BaseItemDraggableAdapter<Search,BaseViewHolder> {
    private Context mContext;
    public MovieAdapter(Context context,int layoutResId, @Nullable List<Search> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Search item) {
        helper.setText(R.id.text_title_item_movie,"Title: "+ item.getTitle())
                .setText(R.id.text_year_item_movie,"Year: "+item.getYear())
                .setText(R.id.text_type_item_movie,"Type: "+item.getType());
        Picasso.with(mContext)
                .load(item.getPoster())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_image_error)
                .fit()
                .into((ImageView) helper.getView(R.id.image_item_movie));
        //Glide.with(mContext).
    }
}


//public class MovieAdapter extends BaseQuickAdapter<Search,BaseViewHolder> {
//    private Context mContext;
//    public MovieAdapter(Context context,int layoutResId, @Nullable List<Search> data) {
//        super(layoutResId, data);
//        mContext = context;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, Search item) {
//        helper.setText(R.id.text_title_item_movie,"Title: "+ item.getTitle())
//                .setText(R.id.text_year_item_movie,"Year: "+item.getYear())
//                .setText(R.id.text_type_item_movie,"Type: "+item.getType());
//        Picasso.with(mContext)
//                .load(item.getPoster())
//                .placeholder(R.drawable.ic_placeholder)
//                .error(R.drawable.ic_image_error)
//                .fit()
//                .into((ImageView) helper.getView(R.id.image_item_movie));
//        //Glide.with(mContext).
//    }
//}

//public class MovieAdapter extends BaseSectionQuickAdapter<MovieTypeSection,BaseViewHolder>{
//
//    /**
//     * Same as QuickAdapter#QuickAdapter(Context,int) but with
//     * some initialization data.
//     *
//     * @param layoutResId      The layout resource id of each item.
//     * @param sectionHeadResId The section head layout id for each item
//     * @param data             A new list is created out of this one to avoid mutable list
//     */
//    public MovieAdapter(int layoutResId, int sectionHeadResId, List<MovieTypeSection> data) {
//        super(layoutResId, sectionHeadResId, data);
//    }
//
//    @Override
//    protected void convertHead(BaseViewHolder helper, MovieTypeSection item) {
//        helper.setText(R.id.text_header, item.header);
//        helper.setVisible(R.id.text_more, item.isMore());
//        helper.addOnClickListener(R.id.text_more);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, MovieTypeSection item) {
//        helper.setText(R.id.text_title_item_movie,"Title: "+ item.t.getTitle())
//                .setText(R.id.text_year_item_movie,"Year: "+item.t.getYear())
//                .setText(R.id.text_type_item_movie,"Type: "+item.t.getType());
//        Picasso.with(mContext)
//                .load(item.t.getPoster())
//                .placeholder(R.drawable.ic_placeholder)
//                .error(R.drawable.ic_image_error)
//                .fit()
//                .into((ImageView) helper.getView(R.id.image_item_movie));
//    }
//}

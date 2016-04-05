
package com.example.sharedemo.multiselectrecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Avni on 22-03-2016.
 */
public class ShowAlbumImagesAdapter_grid extends SelectableAdapter<ShowAlbumImagesAdapter_grid.ViewHolder> {

    public Context mContext;
    private ArrayList<AlbumImages> mAlbumImages;
    public boolean mShowCheckBox;
    private ViewHolder.ClickListener clickListener;
//    public ArrayList<Uri> mShareImages = new ArrayList<Uri> ();

    public ShowAlbumImagesAdapter_grid (Context context, ArrayList<AlbumImages> galleryImagesList,ViewHolder.ClickListener clickListener) {
        this.mAlbumImages = galleryImagesList;
        this.mContext = context;
        this.clickListener = clickListener;

    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_show_gallery_images_grid, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView,clickListener);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        final int pos = position;



        Glide.with(mContext)
                .load("file://"+mAlbumImages.get (position).getAlbumImages ())
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .crossFade()
                .into (viewHolder.imgAlbum);

        viewHolder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);


    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return mAlbumImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private final RelativeLayout selectedOverlay;
        public ImageView imgAlbum;


        public AlbumsModel singleItem;
        private ClickListener listener;

        public ViewHolder(View itemLayoutView,ClickListener listener) {
            super(itemLayoutView);

            this.listener = listener;
            imgAlbum = (ImageView) itemLayoutView.findViewById(R.id.img_album);
            selectedOverlay = (RelativeLayout) itemView.findViewById(R.id.selected_overlay);

            //  itemLayoutView.setOnClickListener(this);

            itemLayoutView.setOnLongClickListener (this);

        }


        /*  @Override
         public void onClick(View v) {
             if (listener != null) {
                 listener.onItemClicked(getAdapterPosition ());
             }
         }*/
        @Override
        public boolean onLongClick (View view) {
            if (listener != null) {
                return listener.onItemLongClicked(getAdapterPosition ());
            }
            return true;
        }

        public interface ClickListener {
            // public void onItemClicked(int position);

            boolean onItemLongClicked(int position);


        }
    }

    // method to access in activity after updating selection
    public ArrayList<AlbumImages> getAlbumImagesList() {
        return mAlbumImages;
    }
}
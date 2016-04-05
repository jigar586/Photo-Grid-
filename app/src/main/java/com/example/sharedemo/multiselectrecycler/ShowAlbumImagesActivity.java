package com.example.sharedemo.multiselectrecycler;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.view.ActionMode;
import java.io.File;
import java.util.ArrayList;

public class ShowAlbumImagesActivity extends AppCompatActivity implements ShowAlbumImagesAdapter.ViewHolder.ClickListener {

   // private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private ShowAlbumImagesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


//    private List<Student> studentList;

    private Button btnSelection;
    private ArrayList<AlbumsModel> albumsModels;
    private int mPosition;

    public ArrayList<Uri> mShareImages = new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        android.support.v7.app.ActionBar bb = getSupportActionBar();
        android.support.v7.app.ActionBar ba = getSupportActionBar();
        android.support.v7.app.ActionBar bd = getSupportActionBar();

        // ab.setLogo(R.drawable.ic_done_all_black_18dp);
        ab.setDisplayUseLogoEnabled(true);
        ab.setHomeButtonEnabled(true);
        bb.setDisplayUseLogoEnabled(true);
        bb.setHomeButtonEnabled(true);
        ba.setDisplayUseLogoEnabled(true);
        ba.setHomeButtonEnabled(true);
        bd.setDisplayUseLogoEnabled(true);
        bd.setHomeButtonEnabled(true);



        setContentView(R.layout.activity_show_gallery_images);
       // toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnSelection = (Button) findViewById(R.id.btnShow);
        //if (toolbar != null) {
//            setSupportActionBar(toolbar);
         //   getSupportActionBar().setTitle("Album Images");

      //  }
        mPosition = (int) getIntent().getIntExtra("position", 0);
        albumsModels = (ArrayList<AlbumsModel>) getIntent().getSerializableExtra("albumsList");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // int id = v.getId();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setType("image/*");
                intent.setDataAndType(Uri.parse("file://" + "mAlbumimages"), "image/*");
                startActivity(intent);


            }
        });



        // use a linear layout manager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // create an Object for Adapter
        mAdapter = new ShowAlbumImagesAdapter(ShowAlbumImagesActivity.this, getAlbumImages(), this);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        btnSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");
                intent.setType("image/jpeg"); /* This example is sharing jpeg images. */
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, mShareImages);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.action_search:
                 Toast.makeText(this, "You have selected", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), ShowAlbumImagesActivity_grid.class);
                i.putExtra("SelectedImage", mShareImages);
                startActivity(i);
                break;
            case R.id.action_grid:
                Toast.makeText(this,"Send ur images",Toast.LENGTH_SHORT).show();
                Intent ii = new Intent(getApplicationContext(),NewActivity.class);
                ii.putExtra("SelectedImage", true);
                //finish();
                startActivity(ii);
               // return super.onOptionsItemSelected(item);
                break;
            case R.id.action_thumb:
                Toast.makeText(this, "You have selected Thumbnail view", Toast.LENGTH_LONG).show();
                Intent iii = new Intent(getApplicationContext(), ShowAlbumImagesActivity_grid.class);
                iii.putExtra("SelectedImage", mShareImages);
                startActivity(iii);
                break;
            case R.id.action_list:
                Toast.makeText(this, "You have selected list view", Toast.LENGTH_LONG).show();
                Intent iiii = new Intent(getApplicationContext(), ShowAlbumImagesActivity_grid.class);
                iiii.putExtra("SelectedImage", mShareImages);
                startActivity(iiii);
                ;


        }
        return true;
    }
    private Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = this.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return this.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    private ArrayList<AlbumImages> getAlbumImages() {
         Object[] abc = albumsModels.get(mPosition).folderImages.toArray();

        Log.i("imagesLength", "" + abc.length);
        ArrayList<AlbumImages> paths = new ArrayList<AlbumImages>();
        int size = abc.length;
        for (int i = 0; i < size; i++) {

            AlbumImages albumImages = new AlbumImages();
            albumImages.setAlbumImages((String) abc[i]);
            paths.add(albumImages);
        }
        return paths;

    }


    @Override
    public void onItemClicked(int position) {

        toggleSelection(position);

    }
    @Override
    public boolean onItemLongClicked(int position) {

        toggleSelection(position);

        return true;
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        Log.i("string path", "" + mAdapter.getAlbumImagesList().get(position).getAlbumImages());

        Uri uriPath = Uri.parse(mAdapter.getAlbumImagesList().get(position).getAlbumImages());
        String path = uriPath.getPath();
        File imageFile = new File(path);
        Uri uri = getImageContentUri(imageFile);
        if (mAdapter.isSelected(position)) {
            mShareImages.add(uri);
        } else {
            mShareImages.remove(uri);
        }
        Log.i("uri path", "" + mShareImages);
    }
}
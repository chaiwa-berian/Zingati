package com.bantu.industrial.zingati;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

public class ProductCartLoader extends ListActivity implements LoaderManager.LoaderCallbacks{


    //This is the adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;

    //These are the contacts rows that we will retrieve
    static final String[] PROJECTION=new String[]{ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME
    };

    //This is the select criteria
    static final String SELECTION="(("+
            ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND ("+
            ContactsContract.Data.DISPLAY_NAME + " != '' ))";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart_loader);

        //Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        //Must add the layout to the root of the layout
        ViewGroup root = findViewById(R.id.coordinator_layout);
        root.addView(progressBar);

        //For the cursor adapter, specify which columns go into which views
        String[] fromColumns  = {ContactsContract.Data.DISPLAY_NAME};
        int[] toViews = {R.id.list_item};// The TextView in activity_product_cart_loader

        //Create an empty adapter we will use to display the loaded data
        //We pass null for the cursor, then update in onLoadFinished
        mAdapter=new SimpleCursorAdapter(this,
                R.layout.activity_product_cart_loader,null,
                fromColumns,toViews,0);

        //Prepare the Loader . Either re-connect with existing one, or start a new one
        getLoaderManager().initLoader(0,null,this);

    }

    // Called when a new Loader needs to be created
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        //Now create and return a CursorLoader that will take care of
        //creating a cursor for the data being displayed

        return new CursorLoader(this, ContactsContract.Data.CONTENT_URI,
                PROJECTION,SELECTION,null,null);
    }

//    @Override
//    public void onLoadFinished(Loader loader, Object o) {
//
//    }

//    @Override
//    public void onLoaderReset(Loader loader) {
//
//    }

    // Called when a previously created loader has finished loading
    @Override
    public void onLoadFinished(Loader loader, Object data) {
        //Swap the new Cursor in. (The framework will take care of closing the old cursor once
        //we return)
        mAdapter.swapCursor((Cursor)data);


    }

    // Called when a previously created loader is reset, making the data unavailable
    @Override
    public void onLoaderReset(Loader loader) {
        //This is called when the last cursor provided to onLoadFinished()
        //above is about to be closed. We need to make sure we are no longer using it
        mAdapter.swapCursor(null);

    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
    }
}

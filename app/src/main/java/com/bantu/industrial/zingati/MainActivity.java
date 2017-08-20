package com.bantu.industrial.zingati;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.util.SparseArray;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Get Scan Button, this for testing only
        Button scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                TextView txtView = (TextView) findViewById(R.id.txtContent);

                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                //This step could potentially be replaced with getting image via Camera, but for now
                //we use the image in the application folder
                Bitmap imgBitmap = BitmapFactory.decodeResource(
                        getApplicationContext().getResources(),R.drawable.puppy);

                imgView.setImageBitmap(imgBitmap);

                //We now setup the Barcode Detector
                BarcodeDetector detector=
                        new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
                if (!detector.isOperational()){
                    txtView.setText("Could not setup the detector!");
                    return;
                }

                //Detect the Barcode
                //it creates a frame from the bitmap, and passes it to the detector.
                // This returns a SparseArray of barcodes.
                Frame frame = new Frame.Builder().setBitmap(imgBitmap).build();
                SparseArray<Barcode> barcodes=detector.detect(frame);

                //Decode the Barcode
                //Typically in this step you would iterate through the SparseArray,
                // and process each bar code independently.
                Barcode thisCode = barcodes.valueAt(0);
                txtView.setText(thisCode.rawValue);

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //If you need to update your activity based on the visibility of your action view,
        // you can receive callbacks when the action is expanded and collapsed by defining
        // an OnActionExpandListener and passing it to setOnActionExpandListener().
       /* MenuItem searchView = (MenuItem) findViewById(R.id.action_search);

        MenuItemCompat.setOnActionExpandListener(searchView, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true; //Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true; //Return true to expand action view
            }
        });
        */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch ( id){
            case R.id.action_scan:
                onScanClick(coordinatorLayout);
                break;
            case R.id.action_search:
                onSearchClick(coordinatorLayout);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_stock_control) {
            // Handle the stock control action
        } else if (id == R.id.nav_stock_list) {

        } else if (id == R.id.nav_sales_report) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        else if (id == R.id.nav_settings) {

        }
        else if (id == R.id.nav_logout) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onScanClick(View view){
        Snackbar.make(view,"Scan in progress...", Snackbar.LENGTH_SHORT).setAction("No Action",null).show();
    }
    public void onSearchClick(View view){
        Snackbar.make(view,"Search in progress...", Snackbar.LENGTH_SHORT).setAction("No Action",null).show();
    }
}

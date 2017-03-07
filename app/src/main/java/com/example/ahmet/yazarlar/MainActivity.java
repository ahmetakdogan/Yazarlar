package com.example.ahmet.yazarlar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String URL = "http://www.gazeteoku.com/tum-yazarlar.html";
    ArrayList<String> yazarlar ;
    ArrayList<String> gazeteler ;
    ArrayList<String> haberler ;
    ArrayList<String> images ;



    String hurriyetUrl = "http://www.hurriyet.com.tr/yazarlar/";
    String starUrl = "http://www.star.com.tr/yazarlar/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Popüler Yazılar"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorilerim"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        new ParsingAsync().execute();
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class ParsingAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Document doc  = Jsoup.connect(URL).get();
                Elements elements = doc.select("li.clearfix");
                Elements elementsImg = elements.select("img");
                Elements aTags = elements.select("div.yazarlistTBlock");
                aTags = aTags.select("a");

                Elements gazetelerElemnts = elements.select(".ylGName");

                yazarlar = new ArrayList<>();
                gazeteler = new ArrayList<>();
                haberler = new ArrayList<>();
                images = new ArrayList<>();
                for(int i=0;i<aTags.size();i++){
                    yazarlar.add(aTags.get(i).text());
                    gazeteler.add(gazetelerElemnts.get(i).text());
                    haberler.add(aTags.get(i).attr("title"));
                    images.add(elementsImg.get(i).attr("src"));
                }

            }catch (Exception e){

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void e) {
            ListView mListView=(ListView)findViewById(R.id.list_news);
            ColumnistAdapter mLayerAdaptor = new ColumnistAdapter(MainActivity.this,yazarlar);
            mListView.setAdapter(mLayerAdaptor);

            mLayerAdaptor.arrayListGazeteler = gazeteler;
            mLayerAdaptor.arrayListHaberler = haberler;
            mLayerAdaptor.arrayListImg = images;

            Log.e("gazeteler:",gazeteler.size()+"");
            Log.e("yazarlar:",yazarlar.size()+"");
            Log.e("haberler:",haberler.size()+"");
            Log.e("fotoğraflar:",images.size()+"");
        }
    }
}

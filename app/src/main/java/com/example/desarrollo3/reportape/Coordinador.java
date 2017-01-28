package com.example.desarrollo3.reportape;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.desarrollo3.reportape.Adaptadores.PaginadorAdaptador;
import com.example.desarrollo3.reportape.View.CamaraFragment;
import com.example.desarrollo3.reportape.View.RecicleViewReportesFragment;
import java.util.ArrayList;

public class Coordinador extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    LocationManager handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegador);
        //Se asigna el view al id que le corresponde
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        setUpNewPager();
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_navegador);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_navegador);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_navegador);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //// Recoge el parametro "email" enviado del MainActivity
    public String recogerparametro() {
        String email = "";
        email = getIntent().getStringExtra("email");
        System.out.println("email: "+ email );
        return email;

    }

    //// Añadir fragments
    private ArrayList<Fragment> agregarFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        // Pasa el parpametro email al fragment
        Bundle bundle = new Bundle();
        bundle.putString("email", recogerparametro());

        // Envía el parámetro recogido a camara_fragment
        CamaraFragment camaraFragment = new CamaraFragment();
        camaraFragment.setArguments(bundle);
        fragments.add(camaraFragment);

        // Envía el parámetro recogido a RecicleViewReportesFragment
        RecicleViewReportesFragment fragmentRecyclerView = new RecicleViewReportesFragment();
        fragmentRecyclerView.setArguments(bundle);
        fragments.add(fragmentRecyclerView);
         return fragments;
    }

    private void setUpNewPager(){
        viewPager.setAdapter(new PaginadorAdaptador(getSupportFragmentManager(), agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);
        //Agrega los Tabs
        tabLayout.getTabAt(0).setText("Reportar");
        tabLayout.getTabAt(1).setText("Ver Reportes");
    }

}

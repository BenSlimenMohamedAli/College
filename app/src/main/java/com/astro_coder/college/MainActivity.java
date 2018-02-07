package com.astro_coder.college;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle Toggle;
    private NavigationView navigationView;
    private Dialog dialog;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   //  get the support of action bar

        //  Setting the drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        Toggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close){
            /*
                Slide the main content when sliding the navigation drawer
             */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View container = findViewById(R.id.container);
                container.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };  // Setting the action bar toggle

        drawerLayout.addDrawerListener(Toggle); // adding the listener
        Toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Enable the home button

        /*
         *  Navigation view selections
         */
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.insertions :  connectAdmin();
                                            break;
                    case R.id.enseignant :  i = new Intent(MainActivity.this,Enseignants.class);
                                            startActivity(i);
                                            MainActivity.this.overridePendingTransition(R.anim.scale_up, R.anim.rotate1);
                                            finish();
                                            break;
                    case R.id.eleve :   i = new Intent(MainActivity.this,Eleves.class);
                                        startActivity(i);
                                        MainActivity.this.overridePendingTransition(R.anim.scale_up, R.anim.rotate1);
                                        finish();
                                        break;
                    case R.id.emplois :     i = new Intent(MainActivity.this,Emplois.class);
                                            startActivity(i);
                                            MainActivity.this.overridePendingTransition(R.anim.scale_up, R.anim.rotate1);
                                            finish();
                                            break;
                    case R.id.affiches :    i = new Intent(MainActivity.this,Affiches.class);
                                            startActivity(i);
                                            MainActivity.this.overridePendingTransition(R.anim.scale_up, R.anim.rotate1);
                                            finish();
                                            break;
                    case R.id.quitter   :   onBackPressed(); break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    /*
        Le menu du ActionBar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu,menu);
        return true;
    }

    /*
        Si l'utilisateur sélectionne une option
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(Toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        Si l'utilisateur cliquer sur back
      */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Quitter l'application ?");
        alertDialogBuilder
                .setMessage("Cliquer sur Oui pour quitter!")
                .setCancelable(false)
                .setPositiveButton("Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /*
        La fonction du connection de l'administrateur
     */

    public boolean connectAdmin(){
        dialog = new Dialog(MainActivity.this,R.style.cust_dialog);
        dialog.setContentView(R.layout.connect_admin);
        dialog.show();

        final EditText e1 = (EditText) dialog.findViewById(R.id.ut);
        final EditText e2 = (EditText) dialog.findViewById(R.id.ps);
        Button bu = (Button) dialog.findViewById(R.id.connect);

        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().equals("dali") && e2.getText().toString().equals("123")){
                    i = new Intent(MainActivity.this,Insertions.class);
                    startActivity(i);
                    MainActivity.this.overridePendingTransition(R.anim.scale_up, R.anim.rotate1);
                    finish();
                }else{
                    Snackbar snackbar = Snackbar.make(view,"Vérifier vos données",Snackbar.LENGTH_LONG);
                    View v = snackbar.getView();
                    v.setBackgroundColor(Color.RED);
                    snackbar.show();
                }
            }
        });

        return true;
    }
}

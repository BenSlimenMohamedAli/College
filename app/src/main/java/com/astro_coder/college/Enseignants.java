package com.astro_coder.college;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.astro_coder.college.Gestion.Enseignant;
import com.dali.astrocoder.college.R;


public class Enseignants extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    private EditText e1,e2;
    private Database databaseHelper;
    private SQLiteDatabase sqliteDB;
    private Enseignant ens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enseignants);
        /* Modification du action bar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Enseignants");


        /*
            La connection
         */
        // initialisation de la base de données
        databaseHelper = new Database(Enseignants.this, "College", null, 2);
        sqliteDB = databaseHelper.getWritableDatabase();
        sqliteDB.setForeignKeyConstraintsEnabled(true);
        //  initialisation des elements
        button = (Button) findViewById(R.id.connect);
        e1 = (EditText) findViewById(R.id.ut);
        e2 = (EditText) findViewById(R.id.ps);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Cursor resultSet = sqliteDB.rawQuery("select *from enseignant where (cin_ens == " + e2.getText().toString() + ")", null);
                    int c = 0;
                    String ut = "";
                    while (resultSet.moveToNext()) {
                        c++;
                        ut = resultSet.getString(1) + " " + resultSet.getString(2);
                        ens = new Enseignant(resultSet.getInt(0),resultSet.getString(1),resultSet.getString(2));
                    }
                    if (ut.equals(e1.getText().toString()) && !ut.equals("") && c > 0 && !e1.getText().toString().equals("")) {
                        Intent i = new Intent(Enseignants.this, Ens.class);
                        Bundle b = new Bundle();
                        b.putString("name",ens.getNom_ens()+" "+ens.getPrenom_ens());
                        b.putInt("cin",ens.getCin_ens());
                        i.putExtras(b);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(Enseignants.this, "Vérifier vos données", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(Enseignants.this,"Il faut insérer vos données", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            Intent i = new Intent(Enseignants.this,MainActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Enseignants.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    // Il faut fermer la base de données si lactivité est finis
    @Override
    protected void onStop() {
        super.onStop();
        sqliteDB.close();
    }
}

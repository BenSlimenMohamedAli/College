package com.astro_coder.college;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.astro_coder.college.Gestion.Seance;
import com.dali.astrocoder.college.R;

import java.util.ArrayList;

public class Ens extends AppCompatActivity {
    private Toolbar toolbar;
    private Database databaseHelper;
    private SQLiteDatabase sqliteDB;
    private ListView listView;
    private ArrayList<Seance> seances;
    private ArrayList<String> listStrings;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enseignant);


        /* Modification du action bar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // récupération des données
        Bundle b = getIntent().getExtras();
        getSupportActionBar().setTitle(b.getString("name"));

        // initialisation de la base de données
        databaseHelper = new Database(Ens.this, "College", null, 2);
        sqliteDB = databaseHelper.getWritableDatabase();
        sqliteDB.setForeignKeyConstraintsEnabled(true);

        /*
            Remplissage de la liste
         */

        listView = (ListView) this.findViewById(R.id.lists);
        seances = new ArrayList<>();
        listStrings = new ArrayList<>();
        Cursor resultSet = sqliteDB.rawQuery("Select * from séance where cin_ens == "+b.getInt("cin")+"",null);

        while(resultSet.moveToNext()){
            listStrings.add(resultSet.getString(0)+"H || "+resultSet.getString(1)+" || "+resultSet.getString(3)
                    +" || Classe :"+resultSet.getString(4)+" ; "+resultSet.getString(5)+" || Salle : "+resultSet.getString(6));
            seances.add(new Seance(resultSet.getInt(0),resultSet.getString(1),resultSet.getInt(2),
                    resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5),resultSet.getInt(6)));
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (Ens.this, R.layout.list_item, listStrings);
        listView.setAdapter(arrayAdapter);
        /*
            Evenments des elements de la liste
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                listStrings = new ArrayList<>();
                Cursor result  = sqliteDB.rawQuery("select *from eleve where num_niveau =="+seances.get(i).getNum_niveau()+" and num_classe == "+seances.get(i).getNum_classe()+"",null);
                while(result.moveToNext()){
                    listStrings.add(result.getString(0)+" || "+result.getString(1)+" || "+result.getString(2));
                }

                dialog = new Dialog(Ens.this,R.style.cust_dialog);
                dialog.setContentView(R.layout.affiche);
                ListView list = dialog.findViewById(R.id.affiche);
                ArrayAdapter arrayAdapter1 = new ArrayAdapter<String>
                        (Ens.this, R.layout.spinner_item, listStrings);
                list.setAdapter(arrayAdapter1);
                dialog.show();

                //  si l'utilisateur qliquer pour un long temp il va marquer une absence
                list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                        return false;
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Ens.this,MainActivity.class);
        startActivity(i);
        finish();
        Toast.makeText(Ens.this,"Vous avez quittez",Toast.LENGTH_LONG).show();
    }
}

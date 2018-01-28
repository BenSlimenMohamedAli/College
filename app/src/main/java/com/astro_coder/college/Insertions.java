package com.astro_coder.college;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.astro_coder.college.Gestion.*;
import com.dali.astrocoder.college.R;

import java.util.ArrayList;

public class Insertions extends AppCompatActivity {
    private Toolbar toolbar;

    private ListView listView;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private ArrayList<Enseignement> enseignements;
    private ArrayList<Salle> salles;
    private ArrayList<Classe> classes;

    private Database databaseHelper;
    private SQLiteDatabase sqliteDB;

    private Dialog dialog;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertions);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Enable the home button
        getSupportActionBar().setTitle("Espace admnistratif");

        /*
         *  Ajout des elements a la listview
         */
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.insertions,R.layout.list_item);
        listView.setAdapter(arrayAdapter);

        databaseHelper = new Database(Insertions.this, "College", null, 2);
        sqliteDB = databaseHelper.getWritableDatabase();
        sqliteDB.setForeignKeyConstraintsEnabled(true);

        /*
         *  Ajout de item Click listener a la listview
         */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    /* Une classe */
                    case 0 :    init(Insertions.this,"Insérer une classe",R.layout.insert_classe);
                                button = (Button) dialog.findViewById(R.id.insérer_classe);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Classe.insérer_classe(Insertions.this,dialog,sqliteDB);
                                    }
                                });break;
                    case 1 :    init(Insertions.this,"La liste des classes",R.layout.affiche);
                                Classe.afficher_classes(Insertions.this,dialog,sqliteDB);break;
                    /* Une salle */
                    case 2 :    init(Insertions.this,"Insérer une salle",R.layout.insert_salle);
                                button = (Button) dialog.findViewById(R.id.insérer_salle);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Salle.insérer_salle(Insertions.this,dialog,sqliteDB);
                                    }
                                });break;
                    case 3 :    init(Insertions.this,"La liste des salles",R.layout.affiche);
                                Salle.afficher_salles(Insertions.this,dialog,sqliteDB);break;
                    /*  Un éleve */
                    case 4 :    init(Insertions.this,"Insérer un éleve",R.layout.insert_eleve);
                                button = (Button) dialog.findViewById(R.id.insérer_eleve);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Eleve.insérer_eleve(Insertions.this,dialog,sqliteDB);
                                    }
                                });break;
                    case 5 :    init(Insertions.this,"La liste des éleves",R.layout.affiche);
                                Eleve.afficher_eleves(Insertions.this,dialog,sqliteDB);break;
                    /*  Une matiére */
                    case 6 :    init(Insertions.this,"Insérer une matiére",R.layout.insert_matiere);
                                button = (Button) dialog.findViewById(R.id.insérer_mat);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Matiére.insérer_matiére(Insertions.this,dialog,sqliteDB);
                                    }
                                });
                                break;
                    case 7 :    init(Insertions.this,"Liste des matiéres",R.layout.affiche);
                                Matiére.afficher_matiére(Insertions.this,dialog,sqliteDB);break;
                    /* Un enseignant */
                    case 8 :    init(Insertions.this,"Insérer un enseignant",R.layout.insert_ens);
                                button = (Button) dialog.findViewById(R.id.insérer_enseignant);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Enseignant.insérer_enseignant(Insertions.this,dialog,sqliteDB);
                                    }
                                }); break;
                    case 9 :    init(Insertions.this,"Liste des enseignants",R.layout.affiche);
                                Enseignant.afficher_enseignants(Insertions.this,dialog,sqliteDB); break;
                    /*  Un enseignement */
                    case 10 :   init(Insertions.this,"Insérer un enseignement",R.layout.insert_eg);
                                button = (Button) dialog.findViewById(R.id.insérer_eg);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Enseignement.insérer_enseignement(Insertions.this,dialog,sqliteDB);
                                    }
                                });
                                break;
                    case 11 :   init(Insertions.this,"Liste des enseignements",R.layout.affiche);
                                Enseignement.afficher_enseignements(Insertions.this,dialog,sqliteDB);
                                 break;
                    /*  Une séance */
                    case 12 :   init(Insertions.this,"Ajouter une séance",R.layout.insert_seance);
                                enseignements = Seance.ajouter_ens(Insertions.this,dialog,sqliteDB);
                                classes = Seance.ajouter_classe(Insertions.this,dialog,sqliteDB);
                                salles = Seance.ajouter_salles(Insertions.this,dialog,sqliteDB);
                                Spinner sp1 = (Spinner) dialog.findViewById(R.id.heures);
                                Spinner sp2 = (Spinner) dialog.findViewById(R.id.jours);
                                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(Insertions.this,R.array.heures,R.layout.spinner_item);
                                sp1.setAdapter(adapter1);
                                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Insertions.this,R.array.jours,R.layout.spinner_item);
                                sp2.setAdapter(adapter2);
                                button = (Button) dialog.findViewById(R.id.inserer_seance);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Seance.insérer_seance(Insertions.this,dialog,sqliteDB,enseignements,classes,salles);
                                    }
                                });break;
                    case 13 :   init(Insertions.this,"Liste des séances",R.layout.affiche);
                                Seance.afficher_séances(Insertions.this,dialog,sqliteDB);break;

                }
            }
        });
    }

    // initialisation
    public void init(Context c, String s, int id){
        dialog = new Dialog(Insertions.this,R.style.cust_dialog);
        dialog.setContentView(id);
        dialog.setTitle(s);
        dialog.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            Intent i = new Intent(Insertions.this,MainActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Insertions.this,MainActivity.class);
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
package com.astro_coder.college;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;
import java.util.Arrays;

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

    private Snackbar snackbar;

    private ArrayList<String> itemname = new ArrayList<>();
    private String[] items ;

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
        items = getResources().getStringArray(R.array.insertions); // get the elements from arrays
        listView = (ListView) findViewById(R.id.listView);
        itemname.addAll(Arrays.asList(items));
        listView.setAdapter(new ArrayAdapter<String>(
                this, R.layout.list_item,
                R.id.Item,itemname));
        /*
            Ouvrir la base de données
         */
        databaseHelper = new Database(Insertions.this, "College", null, 2);
        sqliteDB = databaseHelper.getWritableDatabase();
        sqliteDB.setForeignKeyConstraintsEnabled(true);


        /*
         *  Ajout de item Click listener a la listview
         */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view1, int i, long l) {
                switch (i){
                    /* Une classe */
                    case 0 :    init(Insertions.this,"Insérer une classe",R.layout.insert_classe);
                                Spinner sp4 = (Spinner) dialog.findViewById(R.id.niveau);
                                Spinner sp5 = (Spinner) dialog.findViewById(R.id.numero);
                                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(Insertions.this,R.array.levels,R.layout.spinner_item);
                                sp4.setAdapter(adapter4);
                                ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(Insertions.this,R.array.classrooms,R.layout.spinner_item);
                                sp5.setAdapter(adapter5);
                                button = (Button) dialog.findViewById(R.id.insérer_classe);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Classe.insérer_classe(dialog,sqliteDB,view,view1);
                                    }
                                });break;

                    case 1 :    init(Insertions.this,"La liste des classes",R.layout.affiche);
                                Classe.afficher_classes(Insertions.this,dialog,sqliteDB);break;
                    /* Une salle */
                    case 2 :    init(Insertions.this,"Insérer une salle",R.layout.insert_salle);
                                Spinner sp2 = (Spinner) dialog.findViewById(R.id.type_salle);
                                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(Insertions.this,R.array.choixTypeSalles,R.layout.spinner_item);
                                sp2.setAdapter(adapter1);
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
                            Spinner sp6 = (Spinner) dialog.findViewById(R.id.numero);
                            Spinner sp7 = (Spinner) dialog.findViewById(R.id.numero_classe);
                            ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(Insertions.this,R.array.levels,R.layout.spinner_item);
                            sp6.setAdapter(adapter7);
                            ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(Insertions.this,R.array.classrooms,R.layout.spinner_item);
                            sp7.setAdapter(adapter6);
                                button = (Button) dialog.findViewById(R.id.insérer_eleve);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Eleve.insérer_eleve(dialog,sqliteDB,view,view1);
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
                                        Enseignant.insérer_enseignant(Insertions.this,dialog,sqliteDB,view,view1);
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
                                Spinner sp3 = (Spinner) dialog.findViewById(R.id.jours);
                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(Insertions.this,R.array.heures,R.layout.spinner_item);
                                sp1.setAdapter(adapter3);
                                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Insertions.this,R.array.jours,R.layout.spinner_item);
                                sp3.setAdapter(adapter2);
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

    /*
        initialisation du dialog
      */
    public void init(Context c, String s, int id){
        dialog = new Dialog(Insertions.this,R.style.cust_dialog);
        dialog.setContentView(id);
        dialog.setTitle(s);
        dialog.show();
    }

    /*
        Si l'utilisateur sélectionne HomeButton
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            Intent i = new Intent(Insertions.this,MainActivity.class);
            startActivity(i);
            Insertions.this.overridePendingTransition(R.anim.scale_up, R.anim.rotate1);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        si l'utilisateur cliquer sur back
     */
    @Override
    public void onBackPressed() {

        Intent i = new Intent(Insertions.this,MainActivity.class);
        startActivity(i);
        Insertions.this.overridePendingTransition(R.anim.scale_up, R.anim.rotate1);
        finish();
    }

    /*
        Il faut fermer la base de données si lactivité est finis
      */
    @Override
    protected void onStop() {
        super.onStop();
        sqliteDB.close();
    }

    /*
        Si l'utilisateur résume l'application aprés un stop il faur réouvrir la base de données
     */
    @Override
    protected void onResume() {
        super.onResume();
        /*
            Ouvrir la base de données
         */
        databaseHelper = new Database(Insertions.this, "College", null, 2);
        sqliteDB = databaseHelper.getWritableDatabase();
        sqliteDB.setForeignKeyConstraintsEnabled(true);
    }
}
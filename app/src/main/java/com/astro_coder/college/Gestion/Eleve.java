package com.astro_coder.college.Gestion;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.astro_coder.college.R;

import java.util.ArrayList;

/**
 * Created by astro-coder on 16/01/18.
 */

public class Eleve {
    private int num_inscri,num_niveau,num_classe;
    private String nom,prenom;

    public Eleve(int num_inscri, String nom, String prenom, int num_niveau, int num_classe) {
        this.num_inscri = num_inscri;
        this.num_niveau = num_niveau;
        this.num_classe = num_classe;
        this.nom = nom;
        this.prenom = prenom;
    }

    /*
        Setters et getters
     */

    public int getNum_inscri() {
        return num_inscri;
    }

    public void setNum_inscri(int num_inscri) {
        this.num_inscri = num_inscri;
    }

    public int getNum_niveau() {
        return num_niveau;
    }

    public void setNum_niveau(int num_niveau) {
        this.num_niveau = num_niveau;
    }

    public int getNum_classe() {
        return num_classe;
    }

    public void setNum_classe(int num_classe) {
        this.num_classe = num_classe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /*
        Les méthodes
     */

    public static void insérer_eleve(Dialog dialog, SQLiteDatabase sqliteDB,View view,View view1){
        EditText edit1,edit2,edit3,edit6;
        Spinner edit4,edit5;
        edit1 = (EditText) dialog.findViewById(R.id.num_inscri);
        edit2 = (EditText) dialog.findViewById(R.id.nom_eleve);
        edit3 = (EditText) dialog.findViewById(R.id.prenom_eleve);
        edit4 = (Spinner) dialog.findViewById(R.id.numero);
        edit5 = (Spinner) dialog.findViewById(R.id.numero_classe);
        edit6 = (EditText) dialog.findViewById(R.id.email_parent);
        Snackbar snackbar;

        try{
            sqliteDB.execSQL("insert into eleve values ("+edit1.getText().toString()+",'"+edit2.getText().toString()+
                    "','"+edit3.getText().toString()+"',"+edit4.getSelectedItem().toString()+","+edit5.getSelectedItem().toString()+",'"+edit6.getText().toString()+"')");
            dialog.hide();
            snackbar = Snackbar.make(view1,"L'éleve est inséré",Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            v.setBackgroundColor(Color.GREEN);
            snackbar.show();
        }catch(SQLiteConstraintException e){
            snackbar = Snackbar.make(view,"L'éleve déja existe ou la classe n'existe pas",Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            v.setBackgroundColor(Color.RED);
            snackbar.show();
        }
        catch(Exception e){
            snackbar = Snackbar.make(view,"Il faut insérer un éleve",Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            v.setBackgroundColor(Color.RED);
            snackbar.show();
        }
    }

    /*
        Affichage de la liste des éleves
       */
    public static void afficher_eleves(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){
        final ArrayList<Eleve> eleves = new ArrayList<Eleve>();
        Cursor resultSet = sqliteDB.rawQuery("Select * from eleve order by num_inscri",null);
        ListView listAffiche = (ListView) dialog.findViewById(R.id.affiche);
        final ArrayList listStrings = new ArrayList<String>();

        while(resultSet.moveToNext()){
            listStrings.add(resultSet.getString(0)+" || "+resultSet.getString(1)+
                    " || "+resultSet.getString(2)+ " || "+resultSet.getString(3)+" || "+resultSet.getString(4));
            eleves.add(new Eleve(resultSet.getInt(0),resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4)));
        }
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (context, R.layout.spinner_item, listStrings);
        listAffiche.setAdapter(arrayAdapter);

        listAffiche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                sqliteDB.execSQL("delete from eleve where (num_inscri =='"+eleves.get(i).getNum_inscri()+"' )");
                eleves.remove(i);
                listStrings.remove(i);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(context,"La classe est supprimée", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}

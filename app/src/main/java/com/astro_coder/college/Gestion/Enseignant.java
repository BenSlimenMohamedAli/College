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
import android.widget.Toast;


import com.astro_coder.college.R;

import java.util.ArrayList;

/**
 * Created by astro-coder on 17/01/18.
 */

public class Enseignant {
    private int cin_ens;
    private String nom_ens,prenom_ens;

    public Enseignant(int cin_ens, String nom_ens, String prenom_ens) {
        this.cin_ens = cin_ens;
        this.nom_ens = nom_ens;
        this.prenom_ens = prenom_ens;
    }

    /*
        Getters et setters
     */

    public int getCin_ens() {
        return cin_ens;
    }

    public void setCin_ens(int cin_ens) {
        this.cin_ens = cin_ens;
    }

    public String getNom_ens() {
        return nom_ens;
    }

    public void setNom_ens(String nom_ens) {
        this.nom_ens = nom_ens;
    }

    public String getPrenom_ens() {
        return prenom_ens;
    }

    public void setPrenom_ens(String prenom_ens) {
        this.prenom_ens = prenom_ens;
    }

    /*
        Methodes
     */
    public static void insérer_enseignant(Context context, Dialog dialog, SQLiteDatabase sqliteDB,View view, View view1){
        EditText edit1,edit2,edit3;
        edit1 = (EditText) dialog.findViewById(R.id.cin_ens);
        edit2 = (EditText) dialog.findViewById(R.id.nom_ens);
        edit3 = (EditText) dialog.findViewById(R.id.prenom_ens);
        Snackbar snackbar;

        try{
            sqliteDB.execSQL("insert into enseignant values ("+edit1.getText().toString()+",'"+edit2.getText().toString()+
                    "','"+edit3.getText().toString()+"')");
            snackbar = Snackbar.make(view1,"L'enseignant est inséré",Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            v.setBackgroundColor(Color.GREEN);
            snackbar.show();
            dialog.hide();
        } catch(SQLiteConstraintException e){
            snackbar = Snackbar.make(view,"L'enseignant est déja existe",Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            v.setBackgroundColor(Color.RED);
            snackbar.show();
        }catch(Exception e){
            snackbar = Snackbar.make(view,"Vérifier vos données",Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            v.setBackgroundColor(Color.RED);
            snackbar.show();
        }
    }

    //  Affichage
    public static void afficher_enseignants(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){
        final ArrayList<Enseignant> ens = new ArrayList<Enseignant>();
        Cursor resultSet = sqliteDB.rawQuery("Select * from enseignant order by nom",null);
        ListView listAffiche = (ListView) dialog.findViewById(R.id.affiche);
        final ArrayList listStrings = new ArrayList<String>();
        final Snackbar[] snackbar = new Snackbar[1];

        while(resultSet.moveToNext()){
            listStrings.add(resultSet.getString(1)+" | "+resultSet.getString(2)+" | "+resultSet.getString(0));
            ens.add(new Enseignant(resultSet.getInt(0),resultSet.getString(1),resultSet.getString(2)));
        }
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (context, R.layout.spinner_item, listStrings);
        listAffiche.setAdapter(arrayAdapter);

        listAffiche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                sqliteDB.execSQL("delete from enseignant where (cin_ens =='"+ens.get(i).getCin_ens()+"' )");
                ens.remove(i);
                listStrings.remove(i);
                arrayAdapter.notifyDataSetChanged();

                snackbar[0] = Snackbar.make(view,"L'enseignant est supprimé",Snackbar.LENGTH_SHORT);
                View v = snackbar[0].getView();
                v.setBackgroundColor(Color.GREEN);
                snackbar[0].show();
                return false;
            }
        });
    }

    /*
        Compter le nombre des enseignant
     */
    public static String nb_ens(SQLiteDatabase sqliteDB){
        String s ="";
        Cursor resultSet = sqliteDB.rawQuery("Select * from enseignant",null);
        int count =0;

        while(resultSet.moveToNext()){
            count++;
        }
        s+= count+"";
        return s;
    }
}

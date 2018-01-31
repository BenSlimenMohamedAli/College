package com.astro_coder.college.Gestion;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
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
    public static void insérer_enseignant(Context context, Dialog dialog, SQLiteDatabase sqliteDB){
        EditText edit1,edit2,edit3;
        edit1 = (EditText) dialog.findViewById(R.id.cin_ens);
        edit2 = (EditText) dialog.findViewById(R.id.nom_ens);
        edit3 = (EditText) dialog.findViewById(R.id.prenom_ens);

        try{
            sqliteDB.execSQL("insert into enseignant values ("+edit1.getText().toString()+",'"+edit2.getText().toString()+
                    "','"+edit3.getText().toString()+"')");
            Toast.makeText(context,"L'enseignant est inséré", Toast.LENGTH_LONG).show();
            dialog.hide();
        } catch(SQLiteConstraintException e){
            Toast.makeText(context,"L'enseignant est déja existe", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(context,"Vérifier vos données", Toast.LENGTH_LONG).show();
        }
    }

    //  Affichage
    public static void afficher_enseignants(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){
        final ArrayList<Enseignant> ens = new ArrayList<Enseignant>();
        Cursor resultSet = sqliteDB.rawQuery("Select * from enseignant order by nom",null);
        ListView listAffiche = (ListView) dialog.findViewById(R.id.affiche);
        final ArrayList listStrings = new ArrayList<String>();
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
                Toast.makeText(context,"L'enseignant est supprimé", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}

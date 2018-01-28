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

import com.dali.astrocoder.college.R;

import java.util.ArrayList;

/**
 * Created by astro-coder on 17/01/18.
 */

public class Enseignement {
    private int cin_ens;
    private String type_cours;

    public Enseignement(int cin_ens, String type_cours) {
        this.cin_ens = cin_ens;
        this.type_cours = type_cours;
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

    public String getType_cours() {
        return type_cours;
    }

    public void setType_cours(String type_cours) {
        this.type_cours = type_cours;
    }

    /*
        Les méthodes
     */

    public static void insérer_enseignement(Context context, Dialog dialog, SQLiteDatabase sqliteDB){
        EditText edit1,edit2;
        edit1 = (EditText) dialog.findViewById(R.id.cin_ens1);
        edit2 = (EditText) dialog.findViewById(R.id.type_cours1);

        try{
            sqliteDB.execSQL("insert into enseignement values ("+edit1.getText().toString()+",'"+edit2.getText().toString()+"')");
            Toast.makeText(context,"L'enseignement est inséré", Toast.LENGTH_LONG).show();
            dialog.hide();
        } catch(Exception e){
            Toast.makeText(context,"Vérifier vos données", Toast.LENGTH_LONG).show();
        }
    }

    //  Affichage
    public static void afficher_enseignements(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){
        final ArrayList<Enseignement> ens = new ArrayList<Enseignement>();
        Cursor resultSet = sqliteDB.rawQuery("Select * from enseignement order by cin_ens",null);
        ListView listAffiche = (ListView) dialog.findViewById(R.id.affiche);
        final ArrayList listStrings = new ArrayList<String>();
        while(resultSet.moveToNext()){
            listStrings.add(resultSet.getString(0)+" | "+resultSet.getString(1));
            ens.add(new Enseignement(resultSet.getInt(0),resultSet.getString(1)));
        }
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (context, R.layout.spinner_item, listStrings);
        listAffiche.setAdapter(arrayAdapter);

        listAffiche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                sqliteDB.execSQL("delete from enseignement where (cin_ens =="+ens.get(i).getCin_ens()+") and  (type_mat =='"+ens.get(i).getType_cours()+"')");
                ens.remove(i);
                listStrings.remove(i);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(context,"L'enseignement est supprimé", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }


}

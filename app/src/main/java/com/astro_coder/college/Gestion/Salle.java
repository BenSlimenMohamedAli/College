package com.astro_coder.college.Gestion;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.dali.astrocoder.college.R;

import java.util.ArrayList;

/**
 * Created by astro-coder on 16/01/18.
 */

public class Salle {
    private int num_salle,capacité;
    private String type_salle;

    public Salle(int num_salle, int capacité, String type_salle) {
        this.num_salle = num_salle;
        this.capacité = capacité;
        this.type_salle = type_salle;
    }

    /*
        Getters et setters
     */

    public int getNum_salle() {
        return num_salle;
    }

    public void setNum_salle(int num_salle) {
        this.num_salle = num_salle;
    }

    public int getCapacité() {
        return capacité;
    }

    public void setCapacité(int capacité) {
        this.capacité = capacité;
    }

    public String getType_salle() {
        return type_salle;
    }

    public void setType_salle(String type_salle) {
        this.type_salle = type_salle;
    }

    /*
        Les méthodes
     */

    public static void insérer_salle(Context context, Dialog dialog, SQLiteDatabase sqliteDB){
        Spinner sp = (Spinner) dialog.findViewById(R.id.type_salle);
        EditText edit2 = (EditText) dialog.findViewById(R.id.capacité);
        try{
            sqliteDB.execSQL("insert into salles(type_salle, capacité ) values('"+sp.getSelectedItem().toString()+"',"+edit2.getText().toString()+")");
            Toast.makeText(context,"La classe est insérée", Toast.LENGTH_LONG).show();
            dialog.hide();
        }catch(SQLiteException e){
            Toast.makeText(context,"Vérifier vos données", Toast.LENGTH_LONG).show();
        }

    }

    //  Affichage
    public static void afficher_salles(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){

        final ArrayList<Salle> salles = new ArrayList<>();
        Cursor resultSet = sqliteDB.rawQuery("Select * from salles",null);
        ListView listAffiche = (ListView) dialog.findViewById(R.id.affiche);
        final ArrayList listStrings = new ArrayList<String>();
        while(resultSet.moveToNext()){
            listStrings.add("N : "+resultSet.getString(0)+" || C : "+resultSet.getString(1)+" || T : "+resultSet.getString(2));
            salles.add(new Salle(resultSet.getInt(0),resultSet.getInt(1),resultSet.getString(2)));
        }
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (context, R.layout.spinner_item, listStrings);
        listAffiche.setAdapter(arrayAdapter);
        listAffiche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                sqliteDB.execSQL("delete from salles where (num_salle == '"+salles.get(i).getNum_salle()+"')");
                salles.remove(i);
                listStrings.remove(i);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(context,"La salle est supprimée", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}

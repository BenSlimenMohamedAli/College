package com.astro_coder.college.Gestion;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.astro_coder.college.Insertions;
import com.astro_coder.college.R;

import java.util.ArrayList;

/**
 * Created by astro-coder on 2/8/18.
 */

public class Justification {
    private int num_inscri ;
    private String jour;
    private int heure;
    private String cause;

    /*
        Getters and setters
     */

    public int getNum_inscri() {
        return num_inscri;
    }

    public void setNum_inscri(int num_inscri) {
        this.num_inscri = num_inscri;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    /*
        L'ajout des éleves au dialog
     */

    public static ArrayList<Integer> ajouter_eleves(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){
        final ArrayList<Integer> eleves = new ArrayList<>();
        ArrayList<String> listStrings = new ArrayList<>();
        try{
            Spinner sp1 = (Spinner) dialog.findViewById(R.id.num_ins);
            Cursor resultSet = sqliteDB.rawQuery("select  num_inscri from eleve order by num_inscri",null);

            while(resultSet.moveToNext()){
                eleves.add(new Integer(resultSet.getString(0)));
                listStrings.add(resultSet.getString(0));
            }
            if(listStrings.size() == 0){
                listStrings.add("Il n'ya aucun éleve");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item,listStrings);
            sp1.setAdapter(adapter);
        }catch(Exception e){
            Toast.makeText(context,e.toString(), Toast.LENGTH_LONG).show();
        }

        return eleves;
    }

    /*
        Ajouter une Justification
     */

    public static void insérer_Justification(Context c,Dialog dialog, SQLiteDatabase sqliteDB, View view, View view1){
        Snackbar snackbar;
        Spinner edit1,edit2,edit3;
        edit1 = (Spinner) dialog.findViewById(R.id.num_ins);
        edit2 = (Spinner) dialog.findViewById(R.id.jours);
        edit3 = (Spinner) dialog.findViewById(R.id.heures);
        EditText text = (EditText) dialog.findViewById(R.id.cause);
        try{
            sqliteDB.execSQL("insert into justification values('"+edit1.getSelectedItem().toString()+"','"+edit2.getSelectedItem().toString()+"','"+edit3.getSelectedItem().toString()+"','"+text.getText().toString()+"')");
            dialog.hide();
            snackbar = Snackbar.make(view1,"La justification est insérée",Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            v.setBackgroundColor(Color.GREEN);
            snackbar.show();
        }catch(SQLiteConstraintException e){
            snackbar = Snackbar.make(view,"La Justification est déja existe",Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            v.setBackgroundColor(Color.RED);
            snackbar.show();
        } catch(Exception e){
            snackbar = Snackbar.make(view,"Vérifier vos données",Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            v.setBackgroundColor(Color.RED);
            snackbar.show();
        }

    }
}

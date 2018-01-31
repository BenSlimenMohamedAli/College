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
 * Created by astro-coder on 16/01/18.
 */

public class Classe {
    private int num_niveau,num_classe;  //  Le numero du niveau et du classe

    public Classe(int num_niveau, int num_classe) {
        this.num_niveau = num_niveau;   //  initialisation ...
        this.num_classe = num_classe;   //  initialisation ...
    }

    /*
        Getters et setters
     */

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

    /*
     *  Les méthodes
     */

    /*
        Insérer une classe
      */
    public static void insérer_classe(Dialog dialog, SQLiteDatabase sqliteDB,View view,View view1){
        Snackbar snackbar;
        EditText edit1,edit2;
        edit1 = (EditText) dialog.findViewById(R.id.niveau);
        edit2 = (EditText) dialog.findViewById(R.id.numero);
        try{
            sqliteDB.execSQL("insert into classe values("+edit1.getText().toString()+","+edit2.getText().toString()+")");
            dialog.hide();
            snackbar = Snackbar.make(view1,"La classe est insérée",Snackbar.LENGTH_LONG);
            View v = snackbar.getView();
            v.setBackgroundColor(Color.GREEN);
            snackbar.show();
        }catch(SQLiteConstraintException e){
            snackbar = Snackbar.make(view,"La classe est déja existe",Snackbar.LENGTH_LONG);
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

    /*
        Afficher La liste des classe
       */
    public static void afficher_classes(final Context context, final Dialog dialog, final SQLiteDatabase sqliteDB){
        final ArrayList<Classe> classes = new ArrayList<>();
        final Snackbar[] snackbar = new Snackbar[1];
        Cursor resultSet = sqliteDB.rawQuery("Select * from classe order by num_niveau",null);
        final ListView listAffiche = (ListView) dialog.findViewById(R.id.affiche);
        final ArrayList listStrings = new ArrayList<String>();
        while(resultSet.moveToNext()){
            listStrings.add(resultSet.getString(0)+"  ||  "+resultSet.getString(1));
            classes.add(new Classe(resultSet.getInt(0),resultSet.getInt(1)));
        }
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (context, R.layout.spinner_item, listStrings);
        listAffiche.setAdapter(arrayAdapter);

        listAffiche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                sqliteDB.execSQL("delete from classe where (num_niveau =="+classes.get(i).getNum_niveau()+") and (num_classe =="+classes.get(i).getNum_classe()+")");
                classes.remove(i);
                listStrings.remove(i);
                arrayAdapter.notifyDataSetChanged();
                snackbar[0] = Snackbar.make(view,"La classe est supprimée",Snackbar.LENGTH_SHORT);
                View v = snackbar[0].getView();
                v.setBackgroundColor(Color.GREEN);
                snackbar[0].show();
                return false;
            }
        });
    }
}

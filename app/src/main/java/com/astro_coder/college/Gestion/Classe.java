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

    // Insérer une classe
    public static void insérer_classe(Context context, Dialog dialog, SQLiteDatabase sqliteDB){
        EditText edit1,edit2;
        edit1 = (EditText) dialog.findViewById(R.id.niveau);
        edit2 = (EditText) dialog.findViewById(R.id.numero);
        try{
            sqliteDB.execSQL("insert into classe values("+edit1.getText().toString()+","+edit2.getText().toString()+")");
            Toast.makeText(context,"La classe est insérée"+edit1.getText().toString()+" "+edit2.getText().toString(), Toast.LENGTH_LONG).show();
            dialog.hide();
        }catch(SQLiteConstraintException e){
            Toast.makeText(context,"La classe est déja existe", Toast.LENGTH_LONG).show();
        } catch(Exception e){
            Toast.makeText(context,e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    //  Afficher une classe
    public static void afficher_classes(final Context context, final Dialog dialog, final SQLiteDatabase sqliteDB){
        final ArrayList<Classe> classes = new ArrayList<>();
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
                Toast.makeText(context,"La classe est supprimée", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}

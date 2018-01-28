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

public class Matiére {
    private String type_mat;

    public Matiére(String type_mat) {
        this.type_mat = type_mat;
    }

    /*
        Getters et setters
     */

    public String getType_mat() {
        return type_mat;
    }

    public void setType_mat(String type_mat) {
        this.type_mat = type_mat;
    }

    /*
        Les méthodes
     */

    public static void insérer_matiére(Context context, Dialog dialog, SQLiteDatabase sqliteDB){
        EditText edit2 = (EditText) dialog.findViewById(R.id.type_mat);
        try{
            if(!edit2.getText().toString().equals("")){
                sqliteDB.execSQL("insert into matiére values('" + edit2.getText().toString() + "')");
                Toast.makeText(context, "La matiére est insérée", Toast.LENGTH_LONG).show();
                dialog.hide();
            }else{
                Toast.makeText(context,"Il faut insérer une matiére", Toast.LENGTH_LONG).show();
            }
        }catch(SQLiteConstraintException e){
            Toast.makeText(context,"La matiére est déja existe", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(context,"Il faut insérer une matiére", Toast.LENGTH_LONG).show();
        }
    }

    //  Affichage
    public static void afficher_matiére(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){

        final ArrayList<Matiére> matiéres = new ArrayList<Matiére>();
        Cursor resultSet = sqliteDB.rawQuery("Select * from matiére order by type_mat",null);
        ListView listAffiche = (ListView) dialog.findViewById(R.id.affiche);
        final ArrayList listStrings = new ArrayList<String>();
        while(resultSet.moveToNext()){
            listStrings.add(resultSet.getString(0));
            matiéres.add(new Matiére(resultSet.getString(0)));
        }
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (context, R.layout.spinner_item, listStrings);
        listAffiche.setAdapter(arrayAdapter);

        listAffiche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                sqliteDB.execSQL("delete from matiére where (type_mat == '"+matiéres.get(i).getType_mat()+"')");
                matiéres.remove(i);
                listStrings.remove(i);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(context,"Le cours est supprimée", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}

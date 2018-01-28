package com.astro_coder.college.Gestion;

/*
 * Created by astro-coder on 17/01/18.
 */

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dali.astrocoder.college.R;

import java.util.ArrayList;

public class Seance {
    private int heure;
    private String jour;
    private int cin_ens;
    private String type_mat;
    private int num_niveau,num_classe,num_salle;

    public Seance(int heure, String jour, int cin_ens, String type_mat, int num_niveau, int num_classe,int num_salle) {
        this.heure = heure;
        this.jour = jour;
        this.cin_ens = cin_ens;
        this.type_mat = type_mat;
        this.num_niveau = num_niveau;
        this.num_classe = num_classe;
        this.num_salle = num_salle;
    }

    /*
        Getters et setters
     */

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public int getCin_ens() {
        return cin_ens;
    }

    public void setCin_ens(int cin_ens) {
        this.cin_ens = cin_ens;
    }

    public String getType_mat() {
        return type_mat;
    }

    public void setType_mat(String type_mat) {
        this.type_mat = type_mat;
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

    /*
        L'ajout des enseignants au dialog
     */

    public static ArrayList<Enseignement> ajouter_ens(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){
        final ArrayList<Enseignement> enseignements = new ArrayList<>();
        try{
            Spinner sp1 = (Spinner) dialog.findViewById(R.id.spinner);
            final ArrayList<String> listStrings = new ArrayList<>();
            Cursor resultSet = sqliteDB.rawQuery("select *from enseignement",null);

            while(resultSet.moveToNext()){
                enseignements.add(new Enseignement(resultSet.getInt(0),resultSet.getString(1)));
                Cursor resultSet1 = sqliteDB.rawQuery("select *from enseignant where (cin_ens== "+resultSet.getInt(0)+") ",null);
                resultSet1.moveToNext();
                listStrings.add(resultSet1.getString(1)+" "+resultSet1.getString(2)+" -> "+resultSet.getString(1));
            }
            if(listStrings.size() == 0){
                listStrings.add("Il n'ya aucun enseignement !!");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item,listStrings);
            sp1.setAdapter(adapter);
        }catch(Exception e){
            Toast.makeText(context,e.toString(), Toast.LENGTH_LONG).show();
        }

        return enseignements;
    }

    /*
        L'ajout des classes au dialog
     */

    public static ArrayList<Classe> ajouter_classe(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){
        final ArrayList<Classe> classes = new ArrayList<>();
        try{
            Spinner sp1 = (Spinner) dialog.findViewById(R.id.spinner2);
            final ArrayList<String> listStrings = new ArrayList<>();
            Cursor resultSet = sqliteDB.rawQuery("select *from classe",null);

            while(resultSet.moveToNext()){
                classes.add(new Classe(resultSet.getInt(0),resultSet.getInt(1)));
                listStrings.add(resultSet.getString(0)+" || "+resultSet.getString(1));
            }
            if(listStrings.size() == 0){
                listStrings.add("Il n'ya aucune classe !!");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item,listStrings);
            sp1.setAdapter(adapter);
        }catch(Exception e){
            Toast.makeText(context,e.toString(), Toast.LENGTH_LONG).show();
        }

        return classes;
    }

    /*
        L'ajout des salles au dialog
     */

    public static ArrayList<Salle> ajouter_salles(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){
        final ArrayList<Salle> salles = new ArrayList<>();
        try{
            Spinner sp1 = (Spinner) dialog.findViewById(R.id.spinner3);
            final ArrayList<String> listStrings = new ArrayList<>();
            Cursor resultSet = sqliteDB.rawQuery("select *from salles",null);

            while(resultSet.moveToNext()){
                salles.add(new Salle(resultSet.getInt(0),resultSet.getInt(1),resultSet.getString(2)));
                listStrings.add(resultSet.getString(0)+" || "+resultSet.getString(1)+" || "+resultSet.getString(2));
            }
            if(listStrings.size() == 0){
                listStrings.add("Il n'ya aucune salle !!");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item,listStrings);
            sp1.setAdapter(adapter);
        }catch(Exception e){
            Toast.makeText(context,e.toString(), Toast.LENGTH_LONG).show();
        }

        return salles;
    }

    /*
        L'insertion d'une séance
     */

    public static void insérer_seance(Context context,Dialog dialog,SQLiteDatabase sqliteDB,ArrayList<Enseignement> enseignements,ArrayList<Classe> classes,ArrayList<Salle> salles){
        Spinner sp1,sp2,sp3,sp4,sp5;
        sp1 = (Spinner) dialog.findViewById(R.id.heures);
        sp2 = (Spinner) dialog.findViewById(R.id.jours);
        sp3 = (Spinner) dialog.findViewById(R.id.spinner);
        sp4 = (Spinner) dialog.findViewById(R.id.spinner2);
        sp5 = (Spinner) dialog.findViewById(R.id.spinner3);


        if (salles.size() > 0 && enseignements.size() > 0 && classes.size() > 0){

            Cursor resultSet = sqliteDB.rawQuery("Select * from séance where (heure == "+sp1.getSelectedItem().toString()+" and " +
                    " jour == '"+sp2.getSelectedItem().toString()+"' and num_niveau == "+classes.get(sp4.getSelectedItemPosition()).getNum_niveau()+"" +
                    " and num_classe == "+classes.get(sp4.getSelectedItemPosition()).getNum_classe()+")",null);
            Cursor resultSet1 = sqliteDB.rawQuery("Select * from séance where (heure == "+sp1.getSelectedItem().toString()+" and " +
                    " jour == '"+sp2.getSelectedItem().toString()+"' and num_salle == "+salles.get(sp5.getSelectedItemPosition()).getNum_salle()+")",null);
            int c=0;
            while(resultSet1.moveToNext()){
                c++;
            }
            if(c != 0){
                Toast.makeText(context,"La salle est occupée", Toast.LENGTH_LONG).show();
                return;
            }

            while(resultSet.moveToNext()){
                    c++;
            }
            if(c != 0){
                Toast.makeText(context,"La classe a un cour maintenant", Toast.LENGTH_LONG).show();
                return;
            }
            try{

                sqliteDB.execSQL("insert into séance values ("+sp1.getSelectedItem().toString()+",'"+sp2.getSelectedItem().toString()+"'," +
                        ""+enseignements.get(sp3.getSelectedItemPosition()).getCin_ens()+",'"+enseignements.get(sp3.getSelectedItemPosition()).getType_cours()+"'," +
                        ""+classes.get(sp4.getSelectedItemPosition()).getNum_niveau()+","+classes.get(sp4.getSelectedItemPosition()).getNum_classe()+","+salles.get(sp5.getSelectedItemPosition()).getNum_salle()+")");
                Toast.makeText(context,"La séance est insérée", Toast.LENGTH_LONG).show();
                dialog.hide();
            }catch(Exception e){
                Toast.makeText(context,"Le professeur a un cour maintenant", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(context,"Il faut remplir tous les champs", Toast.LENGTH_LONG).show();
        }

    }

    public static void afficher_séances(final Context context, Dialog dialog, final SQLiteDatabase sqliteDB){

        final ArrayList<Seance> seances = new ArrayList<>();
        Cursor resultSet = sqliteDB.rawQuery("Select * from séance",null);
        ListView listAffiche = (ListView) dialog.findViewById(R.id.affiche);
        final ArrayList listStrings = new ArrayList<String>();
        while(resultSet.moveToNext()){
            listStrings.add(resultSet.getString(0)+" || "+resultSet.getString(1)+" || "+resultSet.getString(2)+" || "+resultSet.getString(3)
            +" || "+resultSet.getString(4)+" || "+resultSet.getString(5)+" || "+resultSet.getString(6));
            seances.add(new Seance(resultSet.getInt(0),resultSet.getString(1),resultSet.getInt(2),
                    resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5),resultSet.getInt(6)));
        }
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (context, R.layout.spinner_item, listStrings);
        listAffiche.setAdapter(arrayAdapter);
        listAffiche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                sqliteDB.execSQL("delete from séance where (heure =="+seances.get(i).getHeure()+" and jour == '"+seances.get(i).getJour()+"'" +
                        " and cin_ens == "+seances.get(i).getCin_ens()+")");
                seances.remove(i);
                listStrings.remove(i);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(context,"La séance est supprimée", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}

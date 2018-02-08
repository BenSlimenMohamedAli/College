package com.astro_coder.college;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * Created by astro-coder on 15/01/18.
 */

public class Database extends SQLiteOpenHelper {
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
            classe(num_niveau,num_classe)
         */
        sqLiteDatabase.execSQL("create table if not exists classe (num_niveau integer, num_classe integer,primary key(num_niveau, num_classe))");
        /*
            salles(num_salle,capacité,type_salle)
         */
        sqLiteDatabase.execSQL("create table if not exists salles(num_salle integer primary key autoincrement, capacité integer not null, type_salle varchar(20) not null)");
        /*
            eleve(num_inscri,nom,prenom,#num_niveau,#num_classe,email_parent)
         */
        sqLiteDatabase.execSQL("create table if not exists eleve(num_inscri integer(4) primary key, nom text, prenom text, num_niveau integer, num_classe integer,email_parent text" +
                                ", foreign key (num_niveau,num_classe) references classe(num_niveau,num_classe) on delete cascade)");
        /*
            matiére(type_mat)
         */
        sqLiteDatabase.execSQL("create table if not exists matiére(type_mat text primary key)");
        /*
            enseignant(cin_ens,nom,prenom)
         */
        sqLiteDatabase.execSQL("create table if not exists enseignant(cin_ens integer(8) primary key,nom text,prenom text)");
        /*
            enseignement(#cin_ens,#type_mat)
         */
        sqLiteDatabase.execSQL("create table if not exists enseignement(cin_ens integer(8),type_mat text,foreign key(cin_ens) references enseignant(cin_ens) on delete cascade," +
                                "foreign key (type_mat) references matiére(type_mat) on delete cascade,primary key(cin_ens,type_mat))");
        /*
            Seance(heure,jour,#cin_ens,#type_mat,#num_niveau,#num_classe,#num_salle)
         */
        sqLiteDatabase.execSQL("create table if not exists séance(heure integer, jour text,cin_ens integer, type_mat text, num_niveau integer, num_classe integer,num_salle integer," +
                                "foreign key (cin_ens,type_mat) references enseignement(cin_ens,type_mat),foreign key(num_niveau,num_classe) references classe(num_niveau,num_classe)," +
                                "foreign key(num_salle) references salles(num_salle) on delete cascade,primary key (heure,jour,cin_ens))");

        /*
            Absence(date,#heure,#num_inscri)
         */
        sqLiteDatabase.execSQL("create table if not exists absence (date_abs date,heure integer,num_inscri integer,foreign key(num_inscri) references eleve(num_inscri),primary key(date_abs,heure,num_inscri))");
        /*
            Justification(#jour,#heure,#num_inscri)
         */
        sqLiteDatabase.execSQL("create table if not exists justification (num_inscri integer, jour text,heure integer, cause text, foreign key (num_inscri) references eleve(num_inscri) " +
                                " on delete cascade,primary key(num_inscri, jour, heure ))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}

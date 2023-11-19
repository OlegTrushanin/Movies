package com.example.movies;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)// создание БД
public abstract class DataBase extends RoomDatabase {

    public static final String NAME_DB = "movie.db";
    private static DataBase instance = null;

    public static DataBase getInstance(Application application) {

        if (instance == null) {

            instance = Room.databaseBuilder(application,
                    DataBase.class,
                    NAME_DB).build();
        }
        return instance;
    }


    abstract MovieDao movieDao();



}

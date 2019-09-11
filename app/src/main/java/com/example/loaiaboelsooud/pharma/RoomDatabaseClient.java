package com.example.loaiaboelsooud.pharma;


import android.arch.persistence.room.Room;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RoomDatabaseClient {
    private static AppDatabase dbInstance;


    public static synchronized AppDatabase getInstance() {

        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "drugeye_name").build();
        }
        return dbInstance;
    }


}

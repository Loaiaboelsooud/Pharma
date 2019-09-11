package com.example.loaiaboelsooud.pharma;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

public class PharmaConstants extends Application {
    private static Context mContext;
    private static Resources res;

    public static final String ISFILTERED = "isFiltered";

    public static final String SELLING = "selling";
    public static final String RENTING = "renting";
    public static final String BUYING = "buying";
    public static final String PHARMACY = "pharmacy";
    public static final String WAREHOUSE = "warehouse";
    public static final String FACTORY = "factory";
    public static final String HOSPITAL = "hospital";
    public static final String LISTEDFOR = "listed_for";
    public static final String TYPE = "type";
    public static final String MANAGER = "manager";
    public static final String SECONDPHARMACIST = "second_pharmacist";
    public static final String OPENCLOSE = "open_close";
    public static final String INTERN = "intern";
    public static final String ASSISTANT = "assistant";
    public static final String PHARMACIST = "pharmacist";
    public static final String COMPANY = "company";
    public static final String WORKPLACE = "workplace";
    public static final String POSITION = "position";
    public static final String BEARER = "Bearer ";
    public static final String PROPERTIES = "properties/";
    public static final String IMAGES = "/images";
    public static final String JOBADS = "job-ads/";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String PRESCRIPTIONS = "prescriptions/";
    public static final String AUTHORIZATION = "Authorization";
    public static final String API = "api/";

    public static String[] listedForArray;
    public static String[] typeArray;
    public static String[] positionArray;
    public static String[] workPlaceArray;

    public static final Map<String, String> listedForMapView = new HashMap<String, String>();
    public static final Map<String, String> typeMapView = new HashMap<String, String>();
    public static final Map<String, String> listedForMapAdd = new HashMap<String, String>();
    public static final Map<String, String> typeMapAdd = new HashMap<String, String>();
    public static final Map<String, String> positionMapView = new HashMap<String, String>();
    public static final Map<String, String> workPlaceMapView = new HashMap<String, String>();
    public static final Map<String, String> positionMapAdd = new HashMap<String, String>();
    public static final Map<String, String> workPlaceMapAdd = new HashMap<String, String>();


    public PharmaConstants(Context context) {
        this.mContext = context;
        res = context.getResources();

        listedForArray = res.getStringArray(R.array.properties_listed_for_array);
        typeArray = res.getStringArray(R.array.properties_type_array);

        listedForMapView.put(SELLING, listedForArray[0]);
        listedForMapView.put(RENTING, listedForArray[1]);
        listedForMapView.put(BUYING, listedForArray[2]);

        typeMapView.put(PHARMACY, typeArray[0]);
        typeMapView.put(WAREHOUSE, typeArray[1]);
        typeMapView.put(FACTORY, typeArray[2]);
        typeMapView.put(HOSPITAL, typeArray[3]);

        listedForMapAdd.put(listedForArray[0], SELLING);
        listedForMapAdd.put(listedForArray[1], RENTING);
        listedForMapAdd.put(listedForArray[2], BUYING);

        typeMapAdd.put(typeArray[0], PHARMACY);
        typeMapAdd.put(typeArray[1], WAREHOUSE);
        typeMapAdd.put(typeArray[2], FACTORY);
        typeMapAdd.put(typeArray[3], HOSPITAL);

        positionArray = res.getStringArray(R.array.job_position_array);
        workPlaceArray = res.getStringArray(R.array.job_work_place_array);

        positionMapView.put(MANAGER, positionArray[0]);
        positionMapView.put(SECONDPHARMACIST, positionArray[1]);
        positionMapView.put(OPENCLOSE, positionArray[2]);
        positionMapView.put(INTERN, positionArray[3]);
        positionMapView.put(ASSISTANT, positionArray[4]);
        positionMapView.put(PHARMACIST, positionArray[5]);

        workPlaceMapView.put(PHARMACY, workPlaceArray[0]);
        workPlaceMapView.put(COMPANY, workPlaceArray[1]);
        workPlaceMapView.put(FACTORY, workPlaceArray[2]);
        workPlaceMapView.put(WAREHOUSE, workPlaceArray[3]);

        positionMapAdd.put(positionArray[0], MANAGER);
        positionMapAdd.put(positionArray[1], SECONDPHARMACIST);
        positionMapAdd.put(positionArray[2], OPENCLOSE);
        positionMapAdd.put(positionArray[3], INTERN);
        positionMapAdd.put(positionArray[4], ASSISTANT);
        positionMapAdd.put(positionArray[5], PHARMACIST);

        workPlaceMapAdd.put(workPlaceArray[0], PHARMACY);
        workPlaceMapAdd.put(workPlaceArray[1], COMPANY);
        workPlaceMapAdd.put(workPlaceArray[2], FACTORY);
        workPlaceMapAdd.put(workPlaceArray[3], WAREHOUSE);

    }
}

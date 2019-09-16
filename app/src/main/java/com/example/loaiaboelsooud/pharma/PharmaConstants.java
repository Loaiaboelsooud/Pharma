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
    public static final String CITIES = "city";

    public static final String ASWAN = "Aswan";
    public static final String ASYUT = "Asyut";
    public static final String LUXOR = "Luxor";
    public static final String ALEXANDRIA = "Alexandria";
    public static final String ISMAILIA = "Ismailia";
    public static final String REDSEA = "Red Sea";
    public static final String BEHEIRA = "Beheira";
    public static final String GIZA = "Giza";
    public static final String DAKAHLIA = "Dakahlia";
    public static final String SUEZ = "Suez";
    public static final String SHARQIA = "Sharqia";
    public static final String GHARBIA = "Gharbia";
    public static final String FAYOUM = "Fayoum";
    public static final String CAIRO = "Cairo";
    public static final String QALYUBIA = "Qalyubia";
    public static final String MONUFIA = "Monufia";
    public static final String MINYA = "Minya";
    public static final String NEWVALLEY = "New Valley";
    public static final String BENISUEF = "Beni Suef";
    public static final String PORTSAID = "Port Said";
    public static final String SOUTHSINAI = "South Sinai";
    public static final String DAMIETTA = "Damietta";
    public static final String SOHAG = "Sohag";
    public static final String NORTHSINAI = "North Sinai";
    public static final String QENA = "Qena";
    public static final String KAFRALSHEIKH = "Kafr al-Sheikh";
    public static final String MATRUH = "Matruh";
    public static final String NORTHCOAST = "North Coast";

    public static String[] listedForArray;
    public static String[] typeArray;
    public static String[] positionArray;
    public static String[] workPlaceArray;
    public static String[] citiesArray;

    public static final Map<String, String> listedForMapView = new HashMap<String, String>();
    public static final Map<String, String> typeMapView = new HashMap<String, String>();
    public static final Map<String, String> positionMapView = new HashMap<String, String>();
    public static final Map<String, String> workPlaceMapView = new HashMap<String, String>();
    public static final Map<String, String> citiesMapView = new HashMap<String, String>();

    public static final Map<String, String> positionMapAdd = new HashMap<String, String>();
    public static final Map<String, String> workPlaceMapAdd = new HashMap<String, String>();
    public static final Map<String, String> listedForMapAdd = new HashMap<String, String>();
    public static final Map<String, String> typeMapAdd = new HashMap<String, String>();
    public static final Map<String, String> citiesMapAdd = new HashMap<String, String>();


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

        citiesArray = res.getStringArray(R.array.cities_array);

        citiesMapAdd.put(citiesArray[0], ASWAN);
        citiesMapAdd.put(citiesArray[1], ASYUT);
        citiesMapAdd.put(citiesArray[2], LUXOR);
        citiesMapAdd.put(citiesArray[3], ALEXANDRIA);
        citiesMapAdd.put(citiesArray[4], ISMAILIA);
        citiesMapAdd.put(citiesArray[5], REDSEA);
        citiesMapAdd.put(citiesArray[6], BEHEIRA);
        citiesMapAdd.put(citiesArray[7], GIZA);
        citiesMapAdd.put(citiesArray[8], DAKAHLIA);
        citiesMapAdd.put(citiesArray[9], SUEZ);
        citiesMapAdd.put(citiesArray[10], SHARQIA);
        citiesMapAdd.put(citiesArray[11], GHARBIA);
        citiesMapAdd.put(citiesArray[12], FAYOUM);
        citiesMapAdd.put(citiesArray[13], CAIRO);
        citiesMapAdd.put(citiesArray[14], QALYUBIA);
        citiesMapAdd.put(citiesArray[15], MONUFIA);
        citiesMapAdd.put(citiesArray[16], MINYA);
        citiesMapAdd.put(citiesArray[17], NEWVALLEY);
        citiesMapAdd.put(citiesArray[18], BENISUEF);
        citiesMapAdd.put(citiesArray[19], PORTSAID);
        citiesMapAdd.put(citiesArray[20], SOUTHSINAI);
        citiesMapAdd.put(citiesArray[21], DAMIETTA);
        citiesMapAdd.put(citiesArray[22], SOHAG);
        citiesMapAdd.put(citiesArray[23], NORTHSINAI);
        citiesMapAdd.put(citiesArray[24], QENA);
        citiesMapAdd.put(citiesArray[25], KAFRALSHEIKH);
        citiesMapAdd.put(citiesArray[26], MATRUH);
        citiesMapAdd.put(citiesArray[27], NORTHCOAST);

        citiesMapView.put(ASWAN, citiesArray[0]);
        citiesMapView.put(ASYUT, citiesArray[1]);
        citiesMapView.put(LUXOR, citiesArray[2]);
        citiesMapView.put(ALEXANDRIA, citiesArray[3]);
        citiesMapView.put(ISMAILIA, citiesArray[4]);
        citiesMapView.put(REDSEA, citiesArray[5]);
        citiesMapView.put(BEHEIRA, citiesArray[6]);
        citiesMapView.put(GIZA, citiesArray[7]);
        citiesMapView.put(DAKAHLIA, citiesArray[8]);
        citiesMapView.put(SUEZ, citiesArray[9]);
        citiesMapView.put(SHARQIA, citiesArray[10]);
        citiesMapView.put(GHARBIA, citiesArray[11]);
        citiesMapView.put(FAYOUM, citiesArray[12]);
        citiesMapView.put(CAIRO, citiesArray[13]);
        citiesMapView.put(QALYUBIA, citiesArray[14]);
        citiesMapView.put(MONUFIA, citiesArray[15]);
        citiesMapView.put(MINYA, citiesArray[16]);
        citiesMapView.put(NEWVALLEY, citiesArray[17]);
        citiesMapView.put(BENISUEF, citiesArray[18]);
        citiesMapView.put(PORTSAID, citiesArray[19]);
        citiesMapView.put(SOUTHSINAI, citiesArray[20]);
        citiesMapView.put(DAMIETTA, citiesArray[21]);
        citiesMapView.put(SOHAG, citiesArray[22]);
        citiesMapView.put(NORTHSINAI, citiesArray[23]);
        citiesMapView.put(QENA, citiesArray[24]);
        citiesMapView.put(KAFRALSHEIKH, citiesArray[25]);
        citiesMapView.put(MATRUH, citiesArray[26]);
        citiesMapView.put(NORTHCOAST, citiesArray[27]);


    }
}

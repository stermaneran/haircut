package com.example.user.eran;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by sefi on 11/24/2017.
 */

public class JSONParser {


    public static Haircut getHaircutInstance(String jsonToParse) {
        StringTokenizer parser = new StringTokenizer(jsonToParse, "{},=:");
        HashMap<String, String> args = new HashMap<>();

        while (parser.hasMoreTokens()) args.put(parser.nextToken().trim(), parser.nextToken());

        String name = args.get("_name");
        String price = args.get("_price");
        String duration = args.get("_duration");

        return new Haircut(name, price, duration);
    }

}

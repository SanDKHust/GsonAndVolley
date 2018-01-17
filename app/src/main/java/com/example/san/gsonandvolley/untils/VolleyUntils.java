package com.example.san.gsonandvolley.untils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by San on 1/13/2018.
 */

public class VolleyUntils {
    public static String makeUrl(String url, HashMap<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(url);
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        int i= 1;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (i==1) {
                stringBuilder.append("?" + entry.getKey() + "=" + entry.getValue());
            } else {
                if(i==2){
                    stringBuilder.append("&" + entry.getKey() + "=" + entry.getValue());
                }else {
                    stringBuilder.append("&" + entry.getKey() + "=" + entry.getValue());
                }

            }
            iterator.remove();
            i++;
        }
        return stringBuilder.toString();
    }
}

package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static List<String> fromJsonArrayToArray(JSONArray jsonArray) {
        List<String> list = new ArrayList<String>();
        int jsonArrayLength = jsonArray.length();

        for (int i = 0; i < jsonArrayLength; i++) {
            try {
                list.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static Sandwich parseSandwichJson(String json) {
        JSONObject sandwichJson;
        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;
        Sandwich selectedSandwich = new Sandwich();

        try {
            sandwichJson = new JSONObject(json);
            try {
                selectedSandwich.setMainName(sandwichJson.getJSONObject("name").getString("mainName"));
                selectedSandwich.setAlsoKnownAs(fromJsonArrayToArray(sandwichJson.getJSONObject("name").getJSONArray("alsoKnownAs")));
                selectedSandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
                selectedSandwich.setDescription(sandwichJson.getString("description"));
                selectedSandwich.setImage(sandwichJson.getString("image"));
                selectedSandwich.setIngredients(fromJsonArrayToArray(sandwichJson.getJSONArray("ingredients")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return selectedSandwich;
    }
}

package com.guilherme.appempresas;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.guilherme.appempresas.domain.model.EnterpriseResponse;

import java.lang.reflect.Type;

public class EnterprisesJsonDeserializer implements JsonDeserializer {

    private static String TAG = "EnterpriseJsonDeserializer";

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null || !json.isJsonObject()) {
            return null;
        }

        try {
            JsonObject jsonObject = json.getAsJsonObject();

            return context.deserialize(jsonObject.get("enterprises"), EnterpriseResponse.class);
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize element: %s", json.toString()));
            return null;
        }
    }
}

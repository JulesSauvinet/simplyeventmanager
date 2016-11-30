package fr.univlyon1.sem.utils.json;

/**
 * Created by jordan on 21/10/15.
 */
public class Json {

    public static JsonObject object(JsonKeyValue... items) {
        return new JsonObject(items);
    }

    public static JsonArray array(Object... values) {
        return new JsonArray(values);
    }

    public static JsonKeyValue pair(String key, Object value) {
        return new JsonKeyValue(key, value);
    }

    public static JsonKeyValue pair(String key, JsonValue value) {
        return new JsonKeyValue(key, value);
    }
}

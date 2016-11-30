package fr.univlyon1.sem.utils.json;

/**
 * Created by jordan on 21/10/15.
 */
public class JsonKeyValue extends JsonValue {

    String key;
    JsonValue value;

    JsonKeyValue(String key, Object value) {
        this.key = key;
        this.value = new JsonPrimitive(value);
    }

    public JsonKeyValue(String key, JsonValue value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(quote(key)).append(": ");
        builder.append(value);

        return builder.toString();
    }
}

package fr.univlyon1.sem.utils.json;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jordan on 21/10/15.
 */
public class JsonObject extends JsonValue {

    private List<JsonKeyValue> items;

    JsonObject(JsonKeyValue items[]) {
        this.items = new ArrayList<>(items.length);

        for (JsonKeyValue item : items) {
            add(item);
        }
    }

    public JsonObject add(JsonKeyValue kv){
        items.add(kv);
        return this;
    }

    public JsonObject add(String key, Object value){
        items.add(new JsonKeyValue(key, value));
        return this;
    }

    public JsonObject add(String key, JsonValue value){
        items.add(new JsonKeyValue(key, value));
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");

        boolean first = true;
        for (JsonKeyValue item : items) {
            if (!first) builder.append(",");
            first = false;

            builder.append(item);
        }

        builder.append("}");

        return builder.toString();
    }
}

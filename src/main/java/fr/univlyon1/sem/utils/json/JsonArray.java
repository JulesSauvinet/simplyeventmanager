package fr.univlyon1.sem.utils.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jordan on 21/10/15.
 */
public class JsonArray extends JsonValue {

    List<JsonValue> values;

    JsonArray(Object values[]) {
        this.values = new ArrayList<>(values.length);

        for (Object value : values) {
            add(value);
        }
    }

    /**
     * Ajoute un élément
     * @param o
     */
    public JsonArray add(Object o) {
        if (o instanceof JsonValue) {
            this.values.add((JsonValue) o);
        } else {
            this.values.add(new JsonPrimitive(o));
        }

        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[");

        boolean first = true;
        for (JsonValue value : values) {
            if (!first) builder.append(",");
            first = false;
            builder.append(value);
        }

        builder.append("]");

        return builder.toString();
    }
}

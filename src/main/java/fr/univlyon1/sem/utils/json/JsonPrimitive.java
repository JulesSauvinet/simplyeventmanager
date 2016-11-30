package fr.univlyon1.sem.utils.json;

/**
 * Created by jordan on 21/10/15.
 */
public class JsonPrimitive extends JsonValue {

    Object value;

    JsonPrimitive(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {

        if (value == null) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();

        if (value.getClass() == Integer.class
                || value.getClass() == Double.class
                || value.getClass() == Float.class) {
            builder.append(String.valueOf(value));
        } else if (value.getClass() == Boolean.class) {
            builder.append(((boolean) value) ? "true" : "false");
        } else {
            builder.append(quote(value.toString()));
        }

        return builder.toString();
    }
}

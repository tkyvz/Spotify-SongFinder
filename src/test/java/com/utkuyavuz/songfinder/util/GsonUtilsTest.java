package com.utkuyavuz.songfinder.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
public class GsonUtilsTest {

    private static final String PLAIN_KEY = "plainKey";
    private static final String PLAIN_VALUE = "plainValue";

    private static final String INNER_OBJECT_KEY = "inner";
    private static final String INNER_KEY = "innerKey";
    private static final String INNER_VALUE = "innerValue";

    private static final int PRIMITIVE_ARRAY_SIZE = 3;
    private static final String PRIMITIVE_ARRAY_KEY = "array";

    private static final int ARRAY_SIZE = 5;
    private static final String ARRAY_KEY = "items";

    private static final String ARRAY_ITEM_KEY = "item";

    private String json;

    /**
     * Creating the json object
     * {
     *     "plainKey": "plainValue",
     *     "inner": {
     *         "innerKey": "innerValue"
     *     },
     *     "array": [0, 1, 2],
     *     "items": [
     *     {
     *         "item": 0
     *     },
     *     {
     *         "item": 1
     *     },
     *     {
     *         "item": 2
     *     },
     *     {
     *         "item": 3
     *     },
     *     {
     *         "item": 4
     *     }]
     * }
     */
    @Before
    public void fetchData() {
        JsonObject root = new JsonObject();
        root.addProperty(PLAIN_KEY, PLAIN_VALUE);

        JsonObject inner = new JsonObject();
        inner.addProperty(INNER_KEY, INNER_VALUE);
        root.add(INNER_OBJECT_KEY, inner);

        JsonArray primitive = new JsonArray();
        for (int i = 0; i < PRIMITIVE_ARRAY_SIZE; i++) {
            primitive.add(i);
        }
        root.add(PRIMITIVE_ARRAY_KEY, primitive);

        JsonArray items = new JsonArray();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            JsonObject obj = new JsonObject();
            obj.addProperty(ARRAY_ITEM_KEY, i);
            items.add(obj);
        }
        root.add(ARRAY_KEY, items);

        this.json = root.toString();
    }

    @Test
    public void getAsJsonArrayTest() {
        /* Get "items" and check it is a JsonArray and has size ARRAY_SIZE */
        GsonUtils gsonUtils_Array = new GsonUtils(this.json);
        gsonUtils_Array.getAsJsonArray(ARRAY_KEY);
        assert (gsonUtils_Array.isSuccess());
        assert (gsonUtils_Array.isJsonArray());
        assert (gsonUtils_Array.getCurrent().getAsJsonArray().size() == ARRAY_SIZE);

        /* Get "array" and check it is a JsonArray and has size PRIMITIVE_ARRAY_SIZE */
        GsonUtils gsonUtils_Primitive = new GsonUtils(this.json);
        gsonUtils_Primitive.getAsJsonArray(PRIMITIVE_ARRAY_KEY);
        assert (gsonUtils_Primitive.isSuccess());
        assert (gsonUtils_Primitive.isJsonArray());
        assert (gsonUtils_Primitive.getCurrent().getAsJsonArray().size() == PRIMITIVE_ARRAY_SIZE);
    }

    @Test
    public void getAsJsonObjectTest() {
        /* Get "inner" and check it is a JsonObject */
        GsonUtils gsonUtils = new GsonUtils(this.json);
        gsonUtils.getAsJsonObject(INNER_OBJECT_KEY);
        assert (gsonUtils.isSuccess());
        assert (gsonUtils.isJsonObject());
    }

    @Test
    public void getJsonPrimitiveTest() {
        /* Get "inner" > "innerKey" and check it is a JsonPrimitive and String and has value INNER_VALUE */
        GsonUtils gsonUtils = new GsonUtils(this.json);
        gsonUtils.getAsJsonObject(INNER_OBJECT_KEY);
        gsonUtils.getJsonPrimitive(INNER_KEY);
        assert (gsonUtils.isSuccess());
        assert (gsonUtils.isJsonPrimitive());
        assert (gsonUtils.getCurrent().getAsJsonPrimitive().isString());
        assert (gsonUtils.getCurrent().getAsJsonPrimitive().getAsString().equals(INNER_VALUE));
    }

    @Test
    public void getJsonObjectAtIndexTest() {
        /*
            Create a random index (idx) within ARRAY_SIZE and get idx'th element of "items" and check it is a
         JsonObject and that JsonObject has "item" as a JsonPrimitive and a Number and has idx as its value.
         */
        int randIdx = new Random().nextInt(ARRAY_SIZE);

        GsonUtils gsonUtils = new GsonUtils(this.json);
        gsonUtils.getAsJsonArray(ARRAY_KEY);
        gsonUtils.getJsonObjectAtIndex(randIdx);
        assert (gsonUtils.isSuccess());
        assert (gsonUtils.isJsonObject());
        gsonUtils.getJsonPrimitive(ARRAY_ITEM_KEY);
        assert (gsonUtils.isJsonPrimitive());
        assert (gsonUtils.getCurrent().getAsJsonPrimitive().isNumber());
        assert (gsonUtils.getCurrent().getAsJsonPrimitive().getAsInt() == randIdx);
    }

    @Test
    public void getObjectAtIndexTest() {
        /*
            Create a random index (idx) within PRIMITIVE_ARRAY_SIZE and get idx'th element of "array" and check it is a
         JsonPrimitive and a Number and has idx as its value.
         */
        int randIdx = new Random().nextInt(PRIMITIVE_ARRAY_SIZE);

        GsonUtils gsonUtils = new GsonUtils(this.json);
        gsonUtils.getAsJsonArray(PRIMITIVE_ARRAY_KEY);
        gsonUtils.getObjectAtIndex(randIdx);
        assert (gsonUtils.isSuccess());
        assert (gsonUtils.isJsonPrimitive());
        assert (gsonUtils.getCurrent().getAsJsonPrimitive().isNumber());
        assert (gsonUtils.getCurrent().getAsJsonPrimitive().getAsInt() == randIdx);
    }
}

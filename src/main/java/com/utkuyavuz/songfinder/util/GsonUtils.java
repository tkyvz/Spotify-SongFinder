package com.utkuyavuz.songfinder.util;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class to fetch data from a Json string
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
public class GsonUtils {

    /* The Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(GsonUtils.class);

    /* Enum to track current JsonElement's type */
    private enum GsonUtilsCurrentModeEnum {
        JSON_ARRAY, JSON_OBJECT, JSON_PRIMITVE, JSON_NULL, ERROR
    }

    /* Current JsonElement */
    private JsonElement current;
    /* Type of the current JsonElement */
    private GsonUtilsCurrentModeEnum currentMode;
    /* Error message */
    private String errorMessage;

    /**
     * Initialize GsonUtils with a json string
     * Parse the input string as a {@link JsonElement} and set type of the current JsonElement.
     *
     * @param json Json object as {@link String}
     */
    public GsonUtils(String json) {
        try {
            LOGGER.debug("Initializing GsonUtils with Json[" + json + "]");
            this.current = new JsonParser().parse(json);
            if (this.current.isJsonObject()) {
                this.currentMode = GsonUtilsCurrentModeEnum.JSON_OBJECT;
            } else if (this.current.isJsonArray()) {
                this.currentMode = GsonUtilsCurrentModeEnum.JSON_ARRAY;
            } else if (this.current.isJsonPrimitive()) {
                this.currentMode = GsonUtilsCurrentModeEnum.JSON_PRIMITVE;
            } else if (this.current.isJsonNull()) {
                this.currentMode = GsonUtilsCurrentModeEnum.JSON_NULL;
            } else {
                LOGGER.error("Input[" + json + "] is in unknown form.");
                this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
                this.errorMessage = "Parsed object is in unknown form.";
            }
        } catch (JsonIOException | JsonSyntaxException e) {
            LOGGER.error("Cannot parse input[" + json + "] as a JsonElement - Exception: " + e.getMessage());
            this.errorMessage = "Cannot parse input as a JsonElement - Exception: " + e.getMessage();
            this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
        }
    }

    /**
     * Get JsonArray from the current JsonElement with the given key
     *
     * @param key Json key to get the JsonArray
     * @return A {@link GsonUtils} instance
     */
    public GsonUtils getAsJsonArray(String key) {
        if (this.currentMode == GsonUtilsCurrentModeEnum.JSON_OBJECT) {
            JsonObject json = (JsonObject) this.current.getAsJsonObject();
            if (json.has(key) && json.get(key).isJsonArray()) {
                LOGGER.debug("Get JsonArray[" + key + "]");
                this.current = json.get(key);
                this.currentMode = GsonUtilsCurrentModeEnum.JSON_ARRAY;
            } else {
                LOGGER.error("Current object does not have key[" + key + "] and/or is not a JsonArray.");
                this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
                this.errorMessage = "Key[" + key + "] does not exists or resulting item is not a JsonArray.";
            }
        } else if (this.currentMode != GsonUtilsCurrentModeEnum.ERROR) {
            LOGGER.error("This method is invalid, the current JsonElement is not a JsonObject.");
            this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
            this.errorMessage = "Current item is not a JsonObject.";
        }
        return this;
    }

    /**
     * Get JsonObject from the current JsonElement with the given key
     *
     * @param key Json key to get the JsonObject
     * @return A {@link GsonUtils} instance
     */
    public GsonUtils getAsJsonObject(String key) {
        if (this.currentMode == GsonUtilsCurrentModeEnum.JSON_OBJECT) {
            JsonObject json = (JsonObject) this.current.getAsJsonObject();
            if (json.has(key) && json.get(key).isJsonObject()) {
                LOGGER.debug("Get JsonObject[" + key + "]");
                this.current = json.get(key);
            } else {
                LOGGER.error("Current object does not have key[" + key + "] and/or is not a JsonObject.");
                this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
                this.errorMessage = "Key[" + key + "] does not exists or resulting item is not a JsonObject.";
            }
        } else if (this.currentMode != GsonUtilsCurrentModeEnum.ERROR) {
            LOGGER.error("This method is invalid, the current JsonElement is not a JsonObject.");
            this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
            this.errorMessage = "Current item is not a JsonObject.";
        }
        return this;
    }

    /**
     * Get JsonPrimitive from the current JsonElement with the given key
     *
     * @param key Json key to get the JsonPrimitive
     * @return A {@link GsonUtils} instance
     */
    public GsonUtils getJsonPrimitive(String key) {
        if (this.currentMode == GsonUtilsCurrentModeEnum.JSON_OBJECT) {
            JsonObject json = (JsonObject) this.current.getAsJsonObject();
            if (json.has(key) && json.get(key).isJsonPrimitive()) {
                LOGGER.debug("Get JsonPrimitive[" + key + "]");
                this.current = json.get(key);
                this.currentMode = GsonUtilsCurrentModeEnum.JSON_PRIMITVE;
            } else {
                LOGGER.error("Current object does not have key[" + key + "] and/or is not a JsonPrimitive.");
                this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
                this.errorMessage = "Key[" + key + "] does not exists or resulting item is not a JsonPrimitive.";
            }
        } else if (this.currentMode != GsonUtilsCurrentModeEnum.ERROR) {
            LOGGER.error("This method is invalid, the current JsonElement is not a JsonObject.");
            this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
            this.errorMessage = "Current item is not a JsonObject.";
        }
        return this;
    }

    /**
     * Get the JsonObject at the given index from the current JsonArray
     *
     * @param idx Index of the JsonObject to retrieve
     * @return A {@link GsonUtils} instance
     */
    public GsonUtils getJsonObjectAtIndex(int idx) {
        if (this.currentMode == GsonUtilsCurrentModeEnum.JSON_ARRAY) {
            JsonArray json = (JsonArray) this.current.getAsJsonArray();
            if (json.size() > idx) {
                JsonElement element = json.get(idx);
                if (element.isJsonObject()) {
                    LOGGER.debug("Get JsonObject@[" + idx + "]");
                    this.currentMode = GsonUtilsCurrentModeEnum.JSON_OBJECT;
                    this.current = element;
                } else {
                    LOGGER.error("Element at index " + idx + " is not a JsonObject.");
                    this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
                    this.errorMessage = "Object at index[" + idx + "] is not a JsonObject.";
                }
            } else {
                LOGGER.error("Current array has size[" + json.size() + "] less than the desired index[" + idx + "].");
                this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
                this.errorMessage = "IndexOutOfBounds - Idx[" + idx + "] and JsonArray has size[" + json.size() + "].";
            }
        } else if (this.currentMode != GsonUtilsCurrentModeEnum.ERROR) {
            LOGGER.error("This method is invalid, the current JsonElement is not a JsonArray.");
            this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
            this.errorMessage = "Current item is not a JsonArray.";
        }
        return this;
    }

    /**
     * Get the JsonPrimitive at the given index from the current JsonArray
     *
     * @param idx Index of the JsonPrimitive to retrieve
     * @return A {@link GsonUtils} instance
     */
    public GsonUtils getObjectAtIndex(int idx) {
        if (this.currentMode == GsonUtilsCurrentModeEnum.JSON_ARRAY) {
            JsonArray json = (JsonArray) this.current.getAsJsonArray();
            if (json.size() > idx) {
                JsonElement element = json.get(idx);
                if (element.isJsonPrimitive()) {
                    LOGGER.debug("Get JsonPrimitive@[" + idx + "]");
                    this.currentMode = GsonUtilsCurrentModeEnum.JSON_PRIMITVE;
                    this.current = element;
                } else {
                    LOGGER.error("Element at index " + idx + " is not a JsonPrimitive.");
                    this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
                    this.errorMessage = "Object at index[" + idx + "] is not a JsonPrimitive.";
                }
            } else {
                LOGGER.error("Current array has size[" + json.size() + "] less than the desired index[" + idx + "].");
                this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
                this.errorMessage = "IndexOutOfBounds - Idx[" + idx + "] and JsonArray has size[" + json.size() + "].";
            }
        } else if (this.currentMode != GsonUtilsCurrentModeEnum.ERROR) {
            LOGGER.error("This method is invalid, the current JsonElement is not a JsonArray.");
            this.currentMode = GsonUtilsCurrentModeEnum.ERROR;
            this.errorMessage = "Current item is not a JsonArray.";
        }
        return this;
    }

    /**
     * Check whether the current instance has encountered an error or not.
     *
     * @return Returns <code>true</code> if there exists no errors, else <code>false</code>
     */
    public boolean isSuccess() {
        return this.currentMode != GsonUtilsCurrentModeEnum.ERROR;
    }

    /**
     * Check whether the current JsonElement is a JsonArray
     *
     * @return Return <code>true</code> if the current JsonElement is JsonArray, else <code>false</code>
     */
    public boolean isJsonArray() {
        return this.currentMode == GsonUtilsCurrentModeEnum.JSON_ARRAY;
    }

    /**
     * Check whether the current JsonElement is a JsonObject
     *
     * @return Return <code>true</code> if the current JsonElement is JsonObject, else <code>false</code>
     */
    public boolean isJsonObject() {
        return this.currentMode == GsonUtilsCurrentModeEnum.JSON_OBJECT;
    }

    /**
     * Check whether the current JsonElement is a JsonPrimitive
     *
     * @return Return <code>true</code> if the current JsonElement is JsonPrimitve, else <code>false</code>
     */
    public boolean isJsonPrimitive() {
        return this.currentMode == GsonUtilsCurrentModeEnum.JSON_PRIMITVE;
    }

    /**
     * Check whether the current JsonElement is a JsonNull
     *
     * @return Return <code>true</code> if the current JsonElement is JsonNull, else <code>false</code>
     */
    public boolean isJsonNull() {
        return this.currentMode == GsonUtilsCurrentModeEnum.JSON_NULL;
    }

    /**
     * Gets the current JsonElement
     *
     * @return The current JsonElement
     */
    public JsonElement getCurrent() {
        return current;
    }

    /**
     * Gets the error message
     *
     * @return The error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}

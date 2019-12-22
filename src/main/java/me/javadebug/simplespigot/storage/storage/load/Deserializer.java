package me.javadebug.simplespigot.storage.storage.load;

import com.google.gson.JsonObject;

@FunctionalInterface
public interface Deserializer<T> {

    T apply(JsonObject json);
}

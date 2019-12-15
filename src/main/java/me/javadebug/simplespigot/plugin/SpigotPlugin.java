package me.javadebug.simplespigot.plugin;

import me.javadebug.simplespigot.registry.Registry;
import me.javadebug.simplespigot.service.ClassReflector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class SpigotPlugin extends JavaPlugin implements SimplePlugin {

    @Override
    public void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(this, runnable);
    }

    @Override
    public <T> CompletableFuture<T> runAsyncCallback(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, this::runAsync);
    }

    @Override
    public void runSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(this, runnable);
    }

    @Override
    public <T> CompletableFuture<T> runSyncCallback(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, this::runSync);
    }

    @Override
    @SafeVarargs
    public final <T extends Registry> void registerRegistries(Consumer<ClassReflector<T>> consumer, Class<T>... registries) {
        for (Class<T> registryClass : registries) {
            consumer.accept(new ClassReflector<>(registryClass));
        }
    }
}

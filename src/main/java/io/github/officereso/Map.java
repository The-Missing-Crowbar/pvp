package io.github.officereso;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class Map {
    public List<Location> locations;
    public String mapName;

    public Map(List<String> stringLocations, String mapName) {
        List<Location> locations = new ArrayList<>();
        for (String stringLocation : stringLocations) {
            String[] split = stringLocation.split(" ");
            World world = Bukkit.getWorld(split[0]);
            locations.add(new Location(world, Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3])));
        }
        this.locations = locations;
        this.mapName = mapName;
    }

    public Location getSpawnLocation() {
        TreeMap<Integer, Location> spawns = new TreeMap<>();
        List<Player> players = new ArrayList<>();
        for (Entity entity : Objects.requireNonNull(locations.get(0).getWorld()).getEntities()) {
            if (entity instanceof Player) {
                players.add((Player) entity);
            }
        }
        for (Location location : locations) {
            int distance = Integer.MAX_VALUE;
            for (Player player : players) {
                if (distance >= player.getLocation().distance(location)) {
                    distance = (int) player.getLocation().distance(location);
                }
            }
            if (distance > 400) {
                distance = Integer.MAX_VALUE;
            }
            while (spawns.get(distance) != null) {
                distance--;
            }
            spawns.put(distance, location);
        }
        int amount = (int) (locations.size() * .8);
        for (int i = 0; i <= amount; i++) {
            spawns.remove(spawns.firstEntry().getKey());
        }
        Random generator = new Random();
        Object[] values = spawns.values().toArray();
        Object randomValue = values[generator.nextInt(values.length)];
        return (Location) randomValue;
    }
}

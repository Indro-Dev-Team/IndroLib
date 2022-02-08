package io.github.indroDevTeam.indroLib.objects.warps;

import io.github.indroDevTeam.indroLib.objects.homes.Home;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Warp {

    /**
     * @param name Name of new warp
     * @param location Location object of new warp
     */
    public Warp(String name, Location location) {
        setWorld(location.getWorld());
        setId(name);
        setLocation(location);
        setWarpName(name);
        setPitch(location.getPitch());
        setYaw(location.getYaw());
        setX(location.getX());
        setY(location.getY());
        setZ(location.getZ());
    }

    /**
     * @param name Name of new warp
     * @param world World object for location
     * @param x X coordinate (double)
     * @param y y coordinate (double)
     * @param z z coordinate  (double)
     * @param pitch pitch (float)
     * @param yaw yaw (float)
     */
    public Warp(String name, World world, Double x, Double y, Double z, Float pitch, Float yaw) {
        setWorld(world);
        setId(name);
        setLocation(new Location(world, x, y, z, yaw, pitch));
        setWarpName(name);
        setPitch(pitch);
        setYaw(yaw);
        setX(x);
        setY(y);
        setZ(z);
    }

    //home data
    private String warpName;
    private String id;

    //location data
    private World world;
    private Double x;
    private Double y;
    private Double z;
    private Float pitch;
    private Float yaw;
    private Location location;

    //getters
    public World getWorld() {
        return world;
    }
    public String getWarpName() {
        return warpName;
    }
    public String getId() {
        return id;
    }
    public Double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }
    public Double getZ() {
        return z;
    }
    public Float getPitch() {
        return pitch;
    }
    public Float getYaw() {
        return yaw;
    }
    public Location getLocation() {
        return location;
    }

    //setters
    public void setWorld(World world) {
        this.world = world;
    }
    public void setWarpName(String warpName) {
        this.warpName = warpName;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public void setY(Double y) {
        this.y = y;
    }
    public void setZ(Double z) {
        this.z = z;
    }
    public void setPitch(Float pitch) {
        this.pitch = pitch;
    }
    public void setYaw(Float yaw) {
        this.yaw = yaw;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public Home toHome(Player owner) {
        return new Home(owner, this.warpName, this.location);
    }
}

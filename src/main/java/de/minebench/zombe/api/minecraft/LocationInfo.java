package de.minebench.zombe.api.minecraft;

/**
 * Copyright 2016 Max Lee (https://github.com/Phoenix616/)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Mozilla Public License as published by
 * the Mozilla Foundation, version 2.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * Mozilla Public License v2.0 for more details.
 * <p/>
 * You should have received a copy of the Mozilla Public License v2.0
 * along with this program. If not, see <http://mozilla.org/MPL/2.0/>.
 */
public class LocationInfo {
    private final double x;
    private final double y;
    private final double z;

    public LocationInfo(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LocationInfo(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double distance(LocationInfo locInfo) {
        return Math.sqrt(distanceSquared(locInfo));
    }

    public double distanceSquared(LocationInfo locInfo) {
        if(locInfo == null) {
            throw new IllegalArgumentException("Location is null, cannot measure distance!");
        }
        return square(locInfo.getX() - x) + square(locInfo.getY() - y) + square(locInfo.getZ() - z);
    }

    private double square(double n) {
        return n * n;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }
}

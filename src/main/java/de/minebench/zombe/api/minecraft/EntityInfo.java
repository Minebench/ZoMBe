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
public class EntityInfo {
    private final LocationInfo location;
    private final String name;
    private final float eyeHeight;

    public EntityInfo(String name, LocationInfo location, float eyeHeight) {
        this.name = name.toLowerCase();
        this.location = location;
        this.eyeHeight = eyeHeight;
    }

    public String getName() {
        return name;
    }

    public LocationInfo getLocation() {
        return location;
    }

    public float getEyeHeight() {
        return eyeHeight;
    }
}

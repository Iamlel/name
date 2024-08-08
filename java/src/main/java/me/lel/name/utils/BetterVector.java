package me.lel.name.utils;

import org.bukkit.util.Vector;

public class BetterVector extends Vector {

    /**
     * Convert a Vector to a BetterVector.
     * 
     * @param vector Vector to convert
     */
    public static BetterVector fromVector(Vector vector) {
        return new BetterVector(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Construct the vector with all components as 0.
     */
    public BetterVector() {
        super(0,0,0);
    }

    /**
     * Construct the vector with provided integer components.
     *
     * @param x X component
     * @param y Y component
     * @param z Z component
     */
    public BetterVector(int x, int y, int z) {
        super(x, y, z);
    }

    /**
     * Construct the vector with provided spherical components.
     *
     * @param yaw Yaw component
     * @param pitch Pitch component
     * @param length Magnetude of the vector
     * @param noNegative If true, yaw and pitch will be clamped to positive values
     */
    public BetterVector(int yaw, int pitch, int length, boolean noNegative) {
        if (noNegative) {
            yaw += 180;
            pitch += 90;
        }

        setCartesian(yaw, pitch, length);
    }

    /**
     * Construct the vector with provided double components.
     *
     * @param x X component
     * @param y Y component
     * @param z Z component
     */
    public BetterVector(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Construct the vector with provided spherical components.
     *
     * @param yaw Yaw component
     * @param pitch Pitch component
     * @param length Magnetude of the vector
     * @param noNegative If true, yaw and pitch will be clamped to positive values
     */
    public BetterVector(double yaw, double pitch, double length, boolean noNegative) {
        if (noNegative) {
            yaw += 180;
            pitch += 90;
        }

        setCartesian(yaw, pitch, length);
    }

    /**
     * Construct the vector with provided float components.
     *
     * @param x X component
     * @param y Y component
     * @param z Z component
     */
    public BetterVector(float x, float y, float z) {
        super(x, y, z);
    }

    /**
     * Construct the vector with provided spherical components.
     *
     * @param yaw Yaw component
     * @param pitch Pitch component
     * @param length Magnetude of the vector
     * @param noNegative If true, yaw and pitch will be clamped to positive values
     */
    public BetterVector(float yaw, float pitch, float length, boolean noNegative) {
        if (noNegative) {
            yaw += 180;
            pitch += 90;
        }

        setCartesian(yaw, pitch, length);
    }

    /**
     * Sets the yaw component.
     *
     * @param yaw Yaw component
     * @param noNegative If true, yaw will be clamped to positive values
     */
    public void setYaw(int yaw, boolean noNegative) {
        double pitch = getPitch(noNegative);
        if (noNegative) {   yaw += 180; }
        setCartesian(yaw, pitch, length());
    }

    /**
     * Sets the pitch component.
     *
     * @param pitch Pitch component
     * @param noNegative If true, yaw will be clamped to positive values
     */
    public void setPitch(int pitch, boolean noNegative) {
        double yaw = getYaw(noNegative);
        if (noNegative) {   pitch += 90; }
        setCartesian(yaw, pitch, length());
    }

    /**
     * Sets the yaw component.
     *
     * @param yaw Yaw component
     * @param noNegative If true, yaw will be clamped to positive values
     */
    public void setYaw(double yaw, boolean noNegative) {
        double pitch = getPitch(noNegative);
        if (noNegative) {   yaw += 180; }
        setCartesian(yaw, pitch, length());
    }

    /**
     * Sets the pitch component.
     *
     * @param pitch Pitch component
     * @param noNegative If true, yaw will be clamped to positive values
     */
    public void setPitch(double pitch, boolean noNegative) {
        double yaw = getYaw(noNegative);
        if (noNegative) {   pitch += 90; }
        setCartesian(yaw, pitch, length());
    }

    /**
     * Sets the yaw component.
     *
     * @param yaw Yaw component
     * @param noNegative If true, yaw will be clamped to positive values
     */
    public void setYaw(float yaw, boolean noNegative) {
        double pitch = getPitch(noNegative);
        if (noNegative) {   yaw += 180; }
        setCartesian(yaw, pitch, length());
    }

    /**
     * Sets the pitch component.
     *
     * @param pitch Pitch component
     * @param noNegative If true, yaw will be clamped to positive values
     */
    public void setPitch(float pitch, boolean noNegative) {
        double yaw = getYaw(noNegative);
        if (noNegative) {   pitch += 90; }
        setCartesian(yaw, pitch, length());
    }

    /**
     * Sets the cartesian components.
     *
     * @param yaw Yaw component
     * @param pitch Pitch component
     * @param length Magnetude of the vector
     */
    protected void setCartesian(double yaw, double pitch, double length) {
        yaw = Math.toRadians(yaw);
        pitch = Math.toRadians(pitch);

        super.setX(Math.sin(pitch) * Math.sin(yaw));
        super.setY(Math.cos(pitch));
        super.setZ(-1 * Math.sin(pitch) * Math.cos(yaw));
        super.multiply(length);
    }

    /**
     * Gets the yaw component.
     *
     * @param noNegative If true, yaw will be clamped to positive values
     * @return The Yaw component.
     */
    public double getYaw(boolean noNegative) {
        return Math.toDegrees(Math.atan2(getX() * -1, getZ())) + (noNegative ? 180 : 0);
    }

    /**
     * Gets the yaw component.
     *
     * @param noNegative If true, pitch will be clamped to positive values
     * @return The Yaw component.
     */
    public double getPitch(boolean noNegative) {
        double x = getX();
        double z = getZ();
        return ((noNegative ? 180 : 90) - Math.toDegrees(Math.atan2(Math.sqrt(z * z + x * x), getY())));
    }
}
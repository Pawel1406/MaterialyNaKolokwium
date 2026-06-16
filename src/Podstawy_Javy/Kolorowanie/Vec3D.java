package Podstawy_Javy.Kolorowanie;


class Vec3D {
    protected double x;
    protected double y;
    protected double z;

    public Vec3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected Vec3D add(Vec3D other) {
        return new Vec3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    protected Vec3D mul(double scalar) {
        return new Vec3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }
}



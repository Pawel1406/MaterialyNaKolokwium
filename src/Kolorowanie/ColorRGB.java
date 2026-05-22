package Kolorowanie;

class ColorRGB extends Vec3D {

    public ColorRGB(double r, double g, double b) {
        // Składowe: x odpowiada r, y odpowiada g, z odpowiada b
        super(Math.max(0, Math.min(1, r)),
                Math.max(0, Math.min(1, g)),
                Math.max(0, Math.min(1, b)));
    }

    public static ColorRGB mix(ColorRGB c1, ColorRGB c2, double ratio) {
        // Zabezpieczenie zakresu ratio (0 do 1)
        ratio = Math.max(0, Math.min(1, ratio));

        // c1 * ratio
        Vec3D v1 = c1.mul(ratio);
        // c2 * (1 - ratio)
        Vec3D v2 = c2.mul(1.0 - ratio);

        // Dodawanie wektorów: (c1 * ratio) + (c2 * (1 - ratio))
        Vec3D result = v1.add(v2);

        return new ColorRGB(result.x, result.y, result.z);
    }
}
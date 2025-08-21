import java.awt.Color;

public class Test {
    
    public static void main(String[] args)
    {
        int c = Color.GREEN.getRGB();

        System.out.println(Test.adjustBrightness(c, 0.5f));
    }

    public static int adjustBrightness(int color, float scale) {
    // Extract color components
    int r = (color >> 16) & 0xFF;
    int g = (color >> 8) & 0xFF;
    int b = color & 0xFF;

    // Optionally keep alpha if using ARGB format
    int a = (color >> 24) & 0xFF;
    boolean hasAlpha = (color >>> 24) != 0;

    // Apply brightness scale
    r = Math.min(255, Math.max(0, Math.round(r * scale)));
    g = Math.min(255, Math.max(0, Math.round(g * scale)));
    b = Math.min(255, Math.max(0, Math.round(b * scale)));

    // Recombine color
    if (hasAlpha) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    } else {
        return (r << 16) | (g << 8) | b;
    }
}

}

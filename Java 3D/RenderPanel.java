import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;


public class RenderPanel extends JPanel{
    
    private final int HEIGHT;
    private final int WIDTH;

    private int FPS = 60;

    private BufferedImage buff;
    private float[][] zBuffer;

    private Vector3 light = new Vector3(-1, -1, -1);
    private Camera3D camera = new Camera3D(new Vector3(0, 0, 0), 0, 0, 0, (float) Math.PI / 2);

    // shapes and stuff
    Triangle3D t = new Triangle3D(
        new Vector3(1, 0, 5),
        new Vector3(1, 1, 5),
        new Vector3(-1, 1, 5),
        Color.RED.getRGB()
        );

    Triangle3D t2 = new Triangle3D(
        new Vector3(1, 0, 4),
        new Vector3(1, 1, 4),
        new Vector3(-1, 1, 4),
        Color.GREEN.getRGB()
        );

    Rectangle3D r = new Rectangle3D(
        new Vector3(-1, 1, 4),
        new Vector3(1, 1, 4),
        new Vector3(1, -1, 4),
        new Vector3(-1, -1, 4),
        Color.RED.getRGB()
        );

    Cube3D c = new Cube3D(new Vector3(0, 0, 5), 2, Color.MAGENTA.getRGB());
    Cube3D c2;
    Cube3D c3;
    ComplexShape3D cps;

    ArrayList<Cube3D> list = new ArrayList<>();

    

    //

    public RenderPanel(int h, int w)
    {
        this.HEIGHT = h;
        this.WIDTH = w;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        buff = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        resetZBuffer();

        light = light.normalize();
    }

    private void resetZBuffer()
    {
        zBuffer = new float[HEIGHT][WIDTH];
        for (int x = 0; x < zBuffer.length; x++)
            for (int y = 0; y < zBuffer[x].length; y++)
                zBuffer[x][y] = Float.MAX_VALUE;
    }

    public Vector3 getLight()
    {
        return light;
    }

    public Camera3D getCamera()
    {
        return camera;
    }

    @Override public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(buff, 0, 0, null);

    }

    public int getWidth()
    {
        return WIDTH;
    }

    public int getHeight()
    {
        return HEIGHT;
    }

    //game loop
    public void start() {
    preStart();
    Thread loop = new Thread(() -> {
        while (true) {
            updateBuffer();   
            repaint(); // request paintComponent()
                try {
                    Thread.sleep(1000/FPS); //about 60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        loop.start();
    }

    public void preStart()
    {
    
        for (int x = -3; x <= 3; x++)
            for(int z = 1; z <=5; z++)
            {
                list.add(new Cube3D(new Vector3(x,2,z),1.0f, Color.GRAY.getRGB()));
            }
        
        c2 = c.copy();
        c3 = c.copy();
        c2.translate(-3, 0, 0);
        c.translate(3, 0, 0);
        c2.setColor(Color.RED.getRGB());

        HashSet<Shape3D> set = new HashSet<>();
        set.add(c); set.add(c2);
        cps = new ComplexShape3D(set);

        //c3.rotateX(1.3f, c3.getCenter());
        //c3.rotateY(0.4f, c3.getCenter());
    }

    //update pixels
    public void updateBuffer()
    {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                buff.setRGB(x, y, Color.BLACK.getRGB());
        resetZBuffer();
        
        //for (Cube3D c : list) c.draw(this);

        //c3.rotateY(0.01f, c3.getCenter());
        //c3.rotateX(0.01f, c3.getCenter());
        //c3.rotateY(0.01f);
        //light = light.rotateY(0.03f, c3.getCenter());
        c3.drawCamPOV(this);
        camera.rotateX(0.01f, c3.getCenter());
        camera.lookAt(c3.getCenter());
    }

    public void drawLine(Vector3 p1, Vector3 p2)
    {
        Vector3 direction = p2.subtract(p1);
        double length = (double) p1.distance(p2);
        Matrix4x4 translation = Matrix4x4.createTranslation(
            direction.getX() / (float) length, 
            direction.getY() / (float) length, 
            direction.getZ() / (float) length
        );

        Vector3 line = p1;

        for (int i = 0; i < length; i++)
        {
            int x = (int) line.getX();
            int y = (int) line.getY();

            if (x < WIDTH && x >= 0 && y < HEIGHT && y >= 0)
            {
                buff.setRGB(x, y, Color.WHITE.getRGB());
                line = translation.transform(line);
            }
        }
    }

    public void fillTriangle(Vector3 p1, Vector3 p2, Vector3 p3, int color)
    {
        int x1 = (int) p1.getX(); int y1 = (int) p1.getY();
        int x2 = (int) p2.getX(); int y2 = (int) p2.getY();
        int x3 = (int) p3.getX(); int y3 = (int) p3.getY();

        //establish bounds for bounding box
        //also checks bounds
        int minX = Math.max(0, Math.min(x1, Math.min(x2, x3)));
        int maxX = Math.min(WIDTH - 1, Math.max(x1, Math.max(x2, x3)));
        int minY = Math.max(0, Math.min(y1, Math.min(y2, y3)));
        int maxY = Math.min(HEIGHT - 1, Math.max(y1, Math.max(y2, y3)));


        //loop throught each pixel in bounding box
        for (int x = minX; x <= maxX; x++)
            for (int y = minY; y <= maxY; y++)
            {
                //calculate cross product of each edge for each test point
                int cp1 = (x2 - x1) * (y - y1) - (y2 - y1) * (x - x1); //1 - 2
                int cp2 = (x3 - x2) * (y - y2) - (y3 - y2) * (x - x2); //2 - 3
                int cp3 = (x1 - x3) * (y - y3) - (y1 - y3) * (x - x3); //3 - 1

                //check if all cross procucts have the same sign (+ or -)
                boolean allPositive = (cp1 >= 0 && cp2 >= 0 && cp3 >= 0);
                boolean allNegative = (cp1 <= 0 && cp2 <= 0 && cp3 <= 0);

                //zBuffer testing with barycentric coordinates
                float area = Vector3.area(p1, p2, p3); //main triangle area
                Vector3 point = new Vector3(x, y, 1); 
                float w1 = Vector3.area(point, p2, p3) / area; //weight 1
                float w2 = Vector3.area(p1, point, p3) / area; //weight 2
                float w3 = Vector3.area(p1, p2, point) / area; //weight 3

                float pz = p1.getZ() * w1 + p2.getZ() * w2 + p3.getZ() * w3; //z depth

                //same sign = test point is in triangle
                if (allPositive || allNegative)
                    //make sure test point has a lesser z value than whats on the screen
                    if (zBuffer[y][x] > pz + 0.001f) // [y][x] 
                    {
                        buff.setRGB(x, y, color);
                        zBuffer[y][x] = pz;
                    }
                
            }
    }

    //ai generated to adjust color brightness
    public static int adjustBrightness(int color, float scale) 
    {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int a = (color >> 24) & 0xFF;
        boolean hasAlpha = (color >>> 24) != 0;

        r = Math.min(255, Math.max(0, Math.round(r * scale)));
        g = Math.min(255, Math.max(0, Math.round(g * scale)));
        b = Math.min(255, Math.max(0, Math.round(b * scale)));

        if (hasAlpha) {
            return (a << 24) | (r << 16) | (g << 8) | b;
        } else {
            return (r << 16) | (g << 8) | b;
        }
    }
}

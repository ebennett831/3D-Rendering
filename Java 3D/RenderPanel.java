import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.*;


public class RenderPanel extends JPanel implements KeyListener {
    
    private final int HEIGHT;
    private final int WIDTH;

    private int FPS = 60;
    private float renderDistance = 50.0f;

    private BufferedImage buff;
    private float[][] zBuffer;

    private Vector3 light = new Vector3(-0.5f, -1.0f, -0.8f);
    private float ambientLight = 0.3f;  
    private float lightIntensity = 1.2f; 
    private Camera3D camera = new Camera3D(new Vector3(0, 0, 0), 0, 0, 0, (float) Math.PI / 2);
    
    //track pressed keys
    private Set<Integer> pressedKeys = new HashSet<>();

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

        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
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

    public float getRenderDistance()
    {
        return renderDistance;
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
        c.translate(0, 3, 0);
        c2.setColor(Color.RED.getRGB());

        HashSet<Shape3D> set = new HashSet<>();
        set.add(c); set.add(c2); set.add(c3);
        cps = new ComplexShape3D(set);

        c3.setColor(Color.GREEN.getRGB());

        //c3.rotateX(1.3f, c3.getCenter());
        //c3.rotateY(0.4f, c3.getCenter());
    }

    //update pixels
    public void updateBuffer()
    {
        processInput();
        
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                buff.setRGB(x, y, Color.BLACK.getRGB());
        resetZBuffer();
        
        //for (Cube3D c : list) c.draw(this);

        //c3.rotateY(0.01f, c3.getCenter());
        c3.rotateX(0.01f, c3.getCenter());
        //c3.rotateY(0.01f);

        c.rotateX(0.02f, c3.getCenter());
        c2.rotateY(-0.03f, c3.getCenter());

        //cps.rotateZ(0.05f, cps.getCenter());

        cps.drawCamPOV(this);
        //cps.drawCamPOV(this);
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

    public void fillTriangle(Vector3 p1, Vector3 p2, Vector3 p3, float z1, float z2, float z3, int color)
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

                float pz = z1 * w1 + z2 * w2 + z3 * w3; //z depth

                //same sign = test point is in triangle
                if (allPositive || allNegative)
                    //make sure test point has a lesser z value than whats on the screen
                    if (pz < zBuffer[y][x] - 0.001f) // [y][x] 
                    {
                        buff.setRGB(x, y, color);
                        zBuffer[y][x] = pz;
                    }
                
            }
    }

    public int calculateLighting(int baseColor, Vector3 normal, Vector3 lightDirection) {

        //dot product between normal and light
        float dot = normal.dot(lightDirection);
        
        //apply lighting
        float lightingFactor = Math.min(1.0f, ambientLight + (Math.max(0, dot) * lightIntensity));
        
        return adjustBrightness(baseColor, lightingFactor);
    }

    public static int adjustBrightness(int color, float scale) 
    {
        return adjustBrightness(color, scale, 0.3f, 1.2f);
    }
    
    public static int adjustBrightness(int color, float scale, float ambient, float intensity) 
    {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int a = (color >> 24) & 0xFF;
        boolean hasAlpha = (color >>> 24) != 0;

        float lightingFactor = Math.min(1.0f, ambient + (scale * intensity));
        
        r = Math.min(255, Math.max(0, Math.round(r * lightingFactor)));
        g = Math.min(255, Math.max(0, Math.round(g * lightingFactor)));
        b = Math.min(255, Math.max(0, Math.round(b * lightingFactor)));

        if (hasAlpha) {
            return (a << 24) | (r << 16) | (g << 8) | b;
        } else {
            return (r << 16) | (g << 8) | b;
        }
    }

    //key events

    @Override public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            camera.lookAt(c3.getCenter());
            return;
        }
        pressedKeys.add(e.getKeyCode());
    }

    @Override public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override public void keyTyped(KeyEvent e) {}
    
    //process all currently pressed keys
    private void processInput() {
        float moveSpeed = 0.1f;
        float rotateSpeed = 0.05f;
        
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            camera.rotateX(rotateSpeed);
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            camera.rotateX(-rotateSpeed);
        }
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            camera.rotateY(-rotateSpeed);
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            camera.rotateY(rotateSpeed);
        }
        if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
            camera.tranlate(0, -moveSpeed, 0);
        }
        if (pressedKeys.contains(KeyEvent.VK_SHIFT)) {
            camera.tranlate(0, moveSpeed, 0);
        }
        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            camera.moveForward(moveSpeed);
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            camera.moveForward(-moveSpeed);
        }
    }


}

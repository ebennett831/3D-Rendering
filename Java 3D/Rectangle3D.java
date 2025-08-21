import java.awt.Color;

public class Rectangle3D extends Shape3D{
    
    private Triangle3D triangle1;
    private Triangle3D triangle2;

    /*
     * 0 - top left
     * 1 - top right
     * 2 - bottom right
     * 3 - bottom left
     */
    private Vector3[] corners = new Vector3[4];

    public Rectangle3D(Vector3 topLeft, Vector3 topRight, Vector3 bottomRight, Vector3 bottomLeft, int color)
    {
        triangle1 = new Triangle3D(topLeft, topRight, bottomRight, color);
        triangle2 = new Triangle3D(topLeft, bottomRight, bottomLeft, color);

        corners[0] = topLeft;
        corners[1] = topRight;
        corners[2] = bottomRight;
        corners[3] = bottomLeft;

        this.color = color;
    }

    private void updateCorners()
    {
        corners[0] = triangle1.getPoint1();
        corners[1] = triangle1.getPoint2();
        corners[2] = triangle1.getPoint3();
        corners[3] = triangle2.getPoint3();
    }

    public void translate(float x, float y, float z)
    {
        triangle1.translate(x, y, z);
        triangle2.translate(x, y, z);

        updateCorners();
    }

    public void scale(float x, float y, float z)
    {
        triangle1.scale(x, y, z);
        triangle2.scale(x, y, z);

        updateCorners();
    }

    public void scale(float scalar)
    {
        triangle1.scale(scalar, scalar, scalar);
        triangle2.scale(scalar, scalar, scalar);

        updateCorners();
    }    

    public Vector3 getCenter()
    {
        Vector3 sum = new Vector3(0, 0, 0);
        for (Vector3 corner : corners)
            sum = sum.add(corner);
        return sum.scale(0.25f);
    }

    public void rotateX(float angleRadians)
    {
        triangle1.rotateX(angleRadians);
        triangle2.rotateX(angleRadians);

        updateCorners();
    }

    public void rotateX(float angleRadians, Vector3 origin)
    {
        triangle1.rotateX(angleRadians, origin);
        triangle2.rotateX(angleRadians, origin);

        updateCorners();
    }

    public void rotateY(float angleRadians)
    {
        triangle1.rotateY(angleRadians);
        triangle2.rotateY(angleRadians);

        updateCorners();
    }

    public void rotateY(float angleRadians, Vector3 origin)
    {
        triangle1.rotateY(angleRadians, origin);
        triangle2.rotateY(angleRadians, origin);

        updateCorners();
    }

    public void rotateZ(float angleRadians)
    {
        triangle1.rotateZ(angleRadians);
        triangle2.rotateZ(angleRadians);

        updateCorners();
    }

    public void rotateZ(float angleRadians, Vector3 origin)
    {
        triangle1.rotateZ(angleRadians, origin);
        triangle2.rotateZ(angleRadians, origin);

        updateCorners();
    }

    public void draw(RenderPanel rp)
    {
        triangle1.draw(rp);
        triangle2.draw(rp);
    }

    public float width()
    {
        return corners[0].distance(corners[1]);
    }

    public float height()
    {
        return corners[0].distance(corners[3]);
    }

    public Rectangle3D copy()
    {
        return new Rectangle3D(corners[0], corners[1], corners[2], corners[3], getColor());
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
        triangle1.setColor(color);
        triangle2.setColor(color);
    }
}

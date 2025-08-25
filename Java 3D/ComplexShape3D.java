import java.util.*;

public class ComplexShape3D extends Shape3D{
    
    private HashSet<Shape3D> shapes;

    public ComplexShape3D()
    {

    }

    public ComplexShape3D(HashSet<Shape3D> shapes)
    {
        this.shapes = shapes;
    }

    public void translate(float x, float y, float z)
    {
        for (Shape3D s : shapes) s.translate(x, y, z);
    }

    public Vector3 getCenter()
    {
        Vector3 sum = new Vector3(0, 0, 0);
        for (Shape3D s : shapes) sum = sum.add(s.getCenter()); // sum all centers and average out
        return sum.scale(1.0f / shapes.size());
    }

    public void scale(float x, float y, float z)
    {
        for (Shape3D s : shapes) s.scale(x, y, z);
    }

    public void scale(float scalar)
    {
        for (Shape3D s : shapes) s.scale(scalar);
    }

    public void rotateX(float angleRadians)
    {
        for (Shape3D s : shapes) s.rotateX(angleRadians);
    }

    public void rotateX(float angleRadians, Vector3 origin)
    {
        for (Shape3D s : shapes) s.rotateX(angleRadians, origin);
    }

    public void rotateY(float angleRadians)
    {
        for (Shape3D s : shapes) s.rotateY(angleRadians);
    }

    public void rotateY(float angleRadians, Vector3 origin)
    {
        for (Shape3D s : shapes) s.rotateY(angleRadians, origin);
    }

    public void rotateZ(float angleRadians)
    {
        for (Shape3D s : shapes) s.rotateZ(angleRadians);
    }

    public void rotateZ(float angleRadians, Vector3 origin)
    {
        for (Shape3D s : shapes) s.rotateZ(angleRadians, origin);
    }

    public void draw(RenderPanel rp)
    {
        for (Shape3D s : shapes) s.draw(rp);
    }

    public void drawCamPOV(RenderPanel rp)
    {
        for (Shape3D s : shapes) s.drawCamPOV(rp);
    }

    public ComplexShape3D copy()
    {
        HashSet<Shape3D> tempSet = new HashSet<>();
        for (Shape3D s : shapes) tempSet.add(s.copy()); // copy shape and add to new hashset
        return new ComplexShape3D(tempSet);
    }

    public int getColor()
    {
        return -1;
    }

}

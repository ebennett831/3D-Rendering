import java.awt.Color;

public class Triangle3D extends Shape3D {
    
    private Vector3 point1;
    private Vector3 point2;
    private Vector3 point3;

    public Triangle3D(Vector3 point1, Vector3 point2, Vector3 point3, int color)
    {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;

        this.color = color;
    }

    public int getColor()
    {
        return color;
    }

    public Vector3 getPoint1()
    {
        return point1;
    }

    public Vector3 getPoint2()
    {
        return point2;
    }

    public Vector3 getPoint3()
    {
        return point3;
    }

    public void translate(float x, float y, float z)
    {
        Matrix4x4 translationMatrix = Matrix4x4.createTranslation(x, y, z);

        point1 = translationMatrix.transform(point1);
        point2 = translationMatrix.transform(point2);
        point3 = translationMatrix.transform(point3);

    }

    public void scale(float x, float y, float z)
    {
        Matrix4x4 translationMatrix = Matrix4x4.createScaling(x, y, z);

        point1 = translationMatrix.transform(point1);
        point2 = translationMatrix.transform(point2);
        point3 = translationMatrix.transform(point3);
        
    }

    public void scale(float scalar)
    {
        Matrix4x4 translationMatrix = Matrix4x4.createScaling(scalar, scalar, scalar);

        point1 = translationMatrix.transform(point1);
        point2 = translationMatrix.transform(point2);
        point3 = translationMatrix.transform(point3);
        
    }

    public Vector3 getCenter()
    {
        return (point1.add(point2).add(point3).scale(1.0f / 3.0f));
    }

    public void rotateX(float angleRadians)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationX(angleRadians);

        point1 = rotationMatrix.transform(point1);
        point2 = rotationMatrix.transform(point2);
        point3 = rotationMatrix.transform(point3);

    }

    public void rotateX(float angleRadians, Vector3 origin)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationX(angleRadians);

        point1 = point1.subtract(origin);
        point2 = point2.subtract(origin);
        point3 = point3.subtract(origin);

        point1 = rotationMatrix.transform(point1);
        point2 = rotationMatrix.transform(point2);
        point3 = rotationMatrix.transform(point3);

        point1 = point1.add(origin);
        point2 = point2.add(origin);
        point3 = point3.add(origin);

    }

    public void rotateY(float angleRadians)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationY(angleRadians);

        point1 = rotationMatrix.transform(point1);
        point2 = rotationMatrix.transform(point2);
        point3 = rotationMatrix.transform(point3);

    }

    public void rotateY(float angleRadians, Vector3 origin)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationY(angleRadians);

        point1 = point1.subtract(origin);
        point2 = point2.subtract(origin);
        point3 = point3.subtract(origin);

        point1 = rotationMatrix.transform(point1);
        point2 = rotationMatrix.transform(point2);
        point3 = rotationMatrix.transform(point3);

        point1 = point1.add(origin);
        point2 = point2.add(origin);
        point3 = point3.add(origin);

    }

    public void rotateZ(float angleRadians)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationZ(angleRadians);

        point1 = rotationMatrix.transform(point1);
        point2 = rotationMatrix.transform(point2);
        point3 = rotationMatrix.transform(point3);

    }

    public void rotateZ(float angleRadians, Vector3 origin)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationZ(angleRadians);

        point1 = point1.subtract(origin);
        point2 = point2.subtract(origin);
        point3 = point3.subtract(origin);

        point1 = rotationMatrix.transform(point1);
        point2 = rotationMatrix.transform(point2);
        point3 = rotationMatrix.transform(point3);

        point1 = point1.add(origin);
        point2 = point2.add(origin);
        point3 = point3.add(origin);

    }

    public void draw(RenderPanel rp)
    {
        Vector3 projectedPoint1 = Matrix4x4.projectToScreen(point1, rp.getWidth(), rp.getHeight());
        Vector3 projectedPoint2 = Matrix4x4.projectToScreen(point2, rp.getWidth(), rp.getHeight());
        Vector3 projectedPoint3 = Matrix4x4.projectToScreen(point3, rp.getWidth(), rp.getHeight());

        Vector3 normal = getNormal();
        Vector3 light = rp.getLight();

        //calculate dot product between normal vector and light source vector
        //1 = facing directly towards light
        //-1 = facing directly away from light
        float dot = normal.dot(light);

        //normalize dot to values between 0.2 & 1.0
        float dotNormalized = 0.2f + 0.8f * Math.max(0, dot);

        //calculate the new color based on the direction of the face of the triangle
        int adjustedColor = rp.adjustBrightness(color, dotNormalized);

        rp.fillTriangle(projectedPoint1, projectedPoint2, projectedPoint3, adjustedColor);
    }

    public Vector3 getNormal()
    {
        Vector3 edge1 = point2.subtract(point1);
        Vector3 edge2 = point3.subtract(point1);

        Vector3 cross = edge1.cross(edge2);
        return cross.normalize();
    }

    public Triangle3D copy()
    {
        return new Triangle3D(point1, point2, point3, color);
    }

}

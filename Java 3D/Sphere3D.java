import java.util.ArrayList;
import java.util.List;

public class Sphere3D extends Shape3D {

    private Vector3 center;
    private float radius;
    private int latitudeBands; //horizontal slices
    private int longitudeBands; //vertical slices
    private List<Triangle3D> triangles;

    public Sphere3D(Vector3 center, float radius, int latitudeBands, int longitudeBands, int color) 
    {
        this.center = center;
        this.radius = radius;
        this.latitudeBands = latitudeBands;
        this.longitudeBands = longitudeBands;
        this.color = color;
        generateMesh();
    }

    private void generateMesh() 
    {
        List<Vector3> vertices = new ArrayList<>();
        triangles = new ArrayList<>();

        for (int lat = 0; lat <= latitudeBands; lat++) 
        {
            double theta = lat * Math.PI / latitudeBands;
            double sinTheta = Math.sin(theta);
            double cosTheta = Math.cos(theta);

            for (int lon = 0; lon <= longitudeBands; lon++) 
            {
                double phi = lon * 2 * Math.PI / longitudeBands;
                double sinPhi = Math.sin(phi);
                double cosPhi = Math.cos(phi);

                float x = center.getX() + (float)(radius * sinTheta * cosPhi);
                float y = center.getY() + (float)(radius * cosTheta);
                float z = center.getZ() + (float)(radius * sinTheta * sinPhi);
                vertices.add(new Vector3(x, y, z));
            }
        }

        for (int lat = 0; lat < latitudeBands; lat++) 
        {
            for (int lon = 0; lon < longitudeBands; lon++) 
            {
                int first = (lat * (longitudeBands + 1)) + lon;
                int second = first + longitudeBands + 1;

                triangles.add(new Triangle3D(
                    vertices.get(first),
                    vertices.get(second),
                    vertices.get(first + 1),
                    color
                ));
                triangles.add(new Triangle3D(
                    vertices.get(second),
                    vertices.get(second + 1),
                    vertices.get(first + 1),
                    color
                ));
            }
        }
    }

    @Override public void translate(float x, float y, float z) 
    {
        center = center.add(new Vector3(x, y, z));
        for (Triangle3D tri : triangles) tri.translate(x, y, z);
    }

    @Override public void scale(float x, float y, float z) 
    {
        //uniform scaling for sphere
        float avgScale = (x + y + z) / 3.0f;
        radius *= avgScale;
        generateMesh();
    }

    @Override public void scale(float scalar) 
    {
        radius *= scalar;
        generateMesh();
    }

    @Override public void rotateX(float angleRadians) 
    {
        for (Triangle3D tri : triangles) tri.rotateX(angleRadians, center);
        
    }

    @Override public void rotateX(float angleRadians, Vector3 origin) 
    {
        for (Triangle3D tri : triangles) tri.rotateX(angleRadians, origin);
        
    }

    @Override public void rotateY(float angleRadians) 
    {
        for (Triangle3D tri : triangles) tri.rotateY(angleRadians, center);
        
    }

    @Override public void rotateY(float angleRadians, Vector3 origin) 
    {
        for (Triangle3D tri : triangles) tri.rotateY(angleRadians, origin);
        
    }

    @Override public void rotateZ(float angleRadians) 
    {
        for (Triangle3D tri : triangles) tri.rotateZ(angleRadians, center);
        
    }

    @Override public void rotateZ(float angleRadians, Vector3 origin) 
    {
        for (Triangle3D tri : triangles) tri.rotateZ(angleRadians, origin);
        
    }

    @Override public Vector3 getCenter() 
    {
        return center;
    }

    @Override public void draw(RenderPanel rp) 
    {
        for (Triangle3D tri : triangles) tri.draw(rp);
        
    }

    @Override public void drawCamPOV(RenderPanel rp) 
    {
        for (Triangle3D tri : triangles) tri.drawCamPOV(rp);
        
    }

    @Override public int getColor() 
    {
        return color;
    }

    @Override public Shape3D copy() 
    {
        return new Sphere3D(center, radius, latitudeBands, longitudeBands, color);
    }
}

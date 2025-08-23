import java.awt.Color;

public class Cube3D extends Shape3D{
    
    private Rectangle3D[] faces = new Rectangle3D[6];

    public Cube3D(float sideLength, int color)
    {
        float half = sideLength / 2;

        //define 8 vertices
        Vector3 v1 = new Vector3(-half, -half, -half); //back bottom left
        Vector3 v2 = new Vector3( half, -half, -half); //back bottom right
        Vector3 v3 = new Vector3( half,  half, -half); //back top ight
        Vector3 v4 = new Vector3(-half,  half, -half); //back top left
        Vector3 v5 = new Vector3(-half, -half,  half); //front bottom left
        Vector3 v6 = new Vector3( half, -half,  half); //front bottom right
        Vector3 v7 = new Vector3( half,  half,  half); //front top right
        Vector3 v8 = new Vector3(-half,  half,  half); //front top left

        //create 6 faces
        faces[0] = new Rectangle3D(v5, v6, v7, v8, color); //front face
        faces[1] = new Rectangle3D(v8, v7, v3, v4, color); //top face
        faces[2] = new Rectangle3D(v3, v2, v1, v4, color); //back face
        faces[3] = new Rectangle3D(v1, v2, v6, v5, color); //bottom face
        faces[4] = new Rectangle3D(v4, v1, v5, v8, color); //left face
        faces[5] = new Rectangle3D(v6, v2, v3, v7, color); //right face

        this.color = color;
    }

    //constructor that allows the cube to be instantiated at
    //any starting point by moving it after its created
    public Cube3D(Vector3 start, float sideLength, int color)
    {
        float half = sideLength / 2;

        //define 8 vertices
        Vector3 v1 = new Vector3(-half, -half, -half); //back bottom left
        Vector3 v2 = new Vector3( half, -half, -half); //back bottom right
        Vector3 v3 = new Vector3( half,  half, -half); //back top ight
        Vector3 v4 = new Vector3(-half,  half, -half); //back top left
        Vector3 v5 = new Vector3(-half, -half,  half); //front bottom left
        Vector3 v6 = new Vector3( half, -half,  half); //front bottom right
        Vector3 v7 = new Vector3( half,  half,  half); //front top right
        Vector3 v8 = new Vector3(-half,  half,  half); //front top left

        //create 6 faces
        faces[0] = new Rectangle3D(v5, v6, v7, v8, color); //front face
        faces[1] = new Rectangle3D(v8, v7, v3, v4, color); //top face
        faces[2] = new Rectangle3D(v3, v2, v1, v4, color); //back face
        faces[3] = new Rectangle3D(v1, v2, v6, v5, color); //bottom face
        faces[4] = new Rectangle3D(v4, v1, v5, v8, color); //left face
        faces[5] = new Rectangle3D(v6, v2, v3, v7, color); //right face

        translate(start.getX(), start.getY(), start.getZ());

        this.color = color;

    }

    private Cube3D(Rectangle3D[] faces, int color)
    {
        this.faces = faces;
        this.color = color;
    }

    public Cube3D copy()
    {
        Rectangle3D[] copyFaces = new Rectangle3D[6];

        for (int ind = 0; ind < 6; ind++) copyFaces[ind] = faces[ind].copy();

        return new Cube3D(copyFaces, color);
    }

    public void translate(float x, float y, float z)
    {
        for (Rectangle3D r : faces) r.translate(x, y, z);
    }

    public Vector3 getCenter()
    {
        Vector3 sum = new Vector3(0, 0, 0);
        for (Rectangle3D face : faces) {
            sum = sum.add(face.getCenter());
        }
        return sum.scale(1.0f / 6.0f);
    }

    public void scale(float x, float y, float z)
    {
        for (Rectangle3D r : faces) r.scale(x, y, z);
    }

    public void scale(float scalar)
    {
        for (Rectangle3D r : faces) r.scale(scalar, scalar, scalar);
    }

    public void rotateX(float angleRadians)
    {
        for (Rectangle3D r : faces) r.rotateX(angleRadians);
    }

    public void rotateX(float angleRadians, Vector3 origin)
    {
        for (Rectangle3D r : faces) r.rotateX(angleRadians, origin);
    }

    public void rotateY(float angleRadians)
    {
        for (Rectangle3D r : faces) r.rotateY(angleRadians);
    }

    public void rotateY(float angleRadians, Vector3 origin)
    {
        for (Rectangle3D r : faces) r.rotateY(angleRadians, origin);
    }

    public void rotateZ(float angleRadians)
    {
        for (Rectangle3D r : faces) r.rotateZ(angleRadians);
    }

    public void rotateZ(float angleRadians, Vector3 origin)
    {
        for (Rectangle3D r : faces) r.rotateZ(angleRadians, origin);
    }

    public void draw(RenderPanel rp)
    {
        for (Rectangle3D r : faces) r.draw(rp);
    }

    public void drawCamPOV(RenderPanel rp)
    {
        for (Rectangle3D r : faces) r.drawCamPOV(rp);
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
        for (Rectangle3D r : faces) r.setColor(color);
    }

}

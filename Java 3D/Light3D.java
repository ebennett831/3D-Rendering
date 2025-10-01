
public class Light3D {
    
    private Vector3 position;
    private float intensity;
    private int color;

    private static float ambient = 0.3f;

    public Light3D(Vector3 position, float intensity, int color)
    {
        this.position = position;
        this.intensity = intensity;
        this.color = color;
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public float getIntensity()
    {
        return intensity;
    }

    public int getColor()
    {
        return color;
    }

    public float getAmbient()
    {
        return ambient;
    }

    public Vector3 normalize()
    {
        return position.normalize();
    }

    public void translate(float x, float y, float z)
    {
        Matrix4x4 translationMatrix = Matrix4x4.createTranslation(x, y, z);
        position = translationMatrix.transform(position);
    }

    public void rotateX(float angleRadians)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationX(angleRadians);
        position = rotationMatrix.transform(position);
    }

    public void rotateX(float angleRadians, Vector3 origin)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationX(angleRadians);
        position = position.subtract(origin);
        position = rotationMatrix.transform(position);
        position = position.add(origin);
    }

    public void rotateY(float angleRadians)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationY(angleRadians);
        position = rotationMatrix.transform(position);
    }

    public void rotateY(float angleRadians, Vector3 origin)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationY(angleRadians);
        position = position.subtract(origin);
        position = rotationMatrix.transform(position);
        position = position.add(origin);
    }

    public void rotateZ(float angleRadians)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationZ(angleRadians);
        position = rotationMatrix.transform(position);
    }

    public void rotateZ(float angleRadians, Vector3 origin)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationZ(angleRadians);
        position = position.subtract(origin);
        position = rotationMatrix.transform(position);
        position = position.add(origin);
    }






}

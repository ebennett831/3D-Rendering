public class Vector3 {
    
    private float x, y, z;

    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getZ()
    {
        return z;
    }

    public Vector3 add(Vector3 v)
    {
        return new Vector3(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    public Vector3 subtract(Vector3 v)
    {
        return new Vector3(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    public Vector3 scale(float scalar)
    {
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    public float distance(Vector3 other)
    {
        return (float) Math.sqrt(Math.pow((double) other.getX() - x, 2) + Math.pow((double) other.getY() - y, 2));
    }

    //compute the Euclidean length of the vector
    //using the 3D Pythagorean theorem
    public float length()
    {
        float sum = 0;
        sum += x * x;
        sum += y * y;
        sum += z * z;
        return (float) Math.sqrt(sum);
    }

    //return a vector that points in the same direction
    //as the original but with a length of exactly 1
    public Vector3 normalize()
    {
        if (length() == 0) return new Vector3(0, 0, 0);
        return new Vector3(x / length(), y / length(), z / length());
    }

    //gives a single number that tells you
    //how similar the directions of two vectors are.
    //not normalized
    public float dot(Vector3 v)
    {
        return (x * v.getX()) + (y * v.getY()) + (z * v.getZ());
    }

    //return a new vector that is perpendicular
    //to the plane formed by the two input vectors
    public Vector3 cross(Vector3 v)
    {
        return new Vector3((y * v.getZ() - z * v.getY()), (z * v.getX() - x * v.getZ()), (x * v.getY() - y * v.getX()));
    }

    public Vector3 rotateX(float angleRadians)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationX(angleRadians);

        return rotationMatrix.transform(this);
    }

    public Vector3 rotateX(float angleRadians, Vector3 origin)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationX(angleRadians);

        Vector3 temp = subtract(origin);
        temp = rotationMatrix.transform(temp);
        return temp.add(origin);
    }

    public Vector3 rotateY(float angleRadians)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationY(angleRadians);

        return rotationMatrix.transform(this);
    }

    public Vector3 rotateY(float angleRadians, Vector3 origin)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationY(angleRadians);

        Vector3 temp = subtract(origin);
        temp = rotationMatrix.transform(temp);
        return temp.add(origin);
    }

    public Vector3 rotateZ(float angleRadians)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationZ(angleRadians);

        return rotationMatrix.transform(this);
    }

    public Vector3 rotateZ(float angleRadians, Vector3 origin)
    {
        Matrix4x4 rotationMatrix = Matrix4x4.createRotationZ(angleRadians);

        Vector3 temp = subtract(origin);
        temp = rotationMatrix.transform(temp);
        return temp.add(origin);
    }

    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\tX: " + x);
        sb.append("\tY: " + y);
        sb.append("\tZ: " + z);
        return sb.toString();
    }

    public static float area(Vector3 p1, Vector3 p2, Vector3 p3)
    {
        return p2.subtract(p1).cross(p3.subtract(p1)).length() / 2;
    }
}

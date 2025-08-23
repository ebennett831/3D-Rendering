import java.util.Arrays;

public class Matrix4x4 {

    private float[][] matrix;

    public Matrix4x4()
    {
        this.matrix = new float[4][4];
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                matrix[r][c] = 0;
    }

    public Matrix4x4(float[][] matrix)
    {
        this.matrix = matrix;
    }

    public float[][] getMatrixArray()
    {
        return matrix;
    }

    public Matrix4x4 multiply(Matrix4x4 otherMatrix)
    {
        float[][] other = otherMatrix.getMatrixArray();
        float[][] resultMatrix = new float[4][4];

        for (int row = 0; row < 4; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                float sum = 0;
                sum += this.matrix[row][0] * other[0][col];
                sum += this.matrix[row][1] * other[1][col];
                sum += this.matrix[row][2] * other[2][col];
                sum += this.matrix[row][3] * other[3][col];
                resultMatrix[row][col] = sum;
            }
        }
        return new Matrix4x4(resultMatrix);
    }

    public void setIdentityMatrix()
    {
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                matrix[r][c] = r == c ? 1 : 0;
    }

    public Vector3 transform(Vector3 point)
    {
        float x = point.getX();
        float y = point.getY();
        float z = point.getZ();
        float w = 1.0f;

        float newX = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2] * z + matrix[0][3] * w;
        float newY = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2] * z + matrix[1][3] * w;
        float newZ = matrix[2][0] * x + matrix[2][1] * y + matrix[2][2] * z + matrix[2][3] * w;

        return new Vector3(newX, newY, newZ);
    }

    public static Matrix4x4 createTranslation(float x, float y, float z)
    {
        float[][] m = new float[4][4];

        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                m[r][c] = c == 3 ? r == 0 ? x : r == 1 ? y : r == 2 ? z : 1 : r == c ? 1 : 0;

        return new Matrix4x4(m);
    }

    public static Matrix4x4 createScaling(float x, float y, float z)
    {
        float[][] m = new float[4][4];

        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                m[r][c] = r == c ? r == 0 ? x : r == 1 ? y : r == 2 ? z : 1 : 0;

        return new Matrix4x4(m);
    }

    public static Matrix4x4 createRotationX(float angleRadians)
    {
        float[][] m = new float[4][4];

        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                m[r][c] = r == c ? 1 : 0;

        m[1][1] = (float) Math.cos(angleRadians);
        m[2][2] = (float) Math.cos(angleRadians);
        m[1][2] = (float) -Math.sin(angleRadians);
        m[2][1] = (float) Math.sin(angleRadians);

        return new Matrix4x4(m);
    }

    public static Matrix4x4 createRotationY(float angleRadians)
    {
        float[][] m = new float[4][4];

        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                m[r][c] = r == c ? 1 : 0;

        m[0][0] = (float) Math.cos(angleRadians);
        m[2][2] = (float) Math.cos(angleRadians);
        m[0][2] = (float) Math.sin(angleRadians);
        m[2][0] = (float) -Math.sin(angleRadians);

        return new Matrix4x4(m);
    }

    public static Matrix4x4 createRotationZ(float angleRadians)
    {
        float[][] m = new float[4][4];

        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                m[r][c] = r == c ? 1 : 0;
        
        m[0][0] = (float) Math.cos(angleRadians);
        m[1][1] = (float) Math.cos(angleRadians);
        m[0][1] = (float) -Math.sin(angleRadians);
        m[1][0] = (float) Math.sin(angleRadians);

        return new Matrix4x4(m);
    }

    public static Vector3 projectToScreen(Vector3 point3D, int screenWidth, int screenHeight)
    {

        if (point3D.getZ() <= 0) return new Vector3(-1, -1, -1);

        float projectedX = point3D.getX() / point3D.getZ();
        float projectedY = point3D.getY() / point3D.getZ();

        float scale = 200;

        projectedX = projectedX * scale + screenWidth / 2;
        projectedY = projectedY * scale + screenHeight / 2;

        return new Vector3(projectedX, projectedY, point3D.getZ());
    }

    public static Vector3 camProjectToScreen(Vector3 projectedPoint, int screenWidth, int screenHeight)
    {

        float projectedX = projectedPoint.getX() / projectedPoint.getZ();
        float projectedY = projectedPoint.getY() / projectedPoint.getZ();

        float scale = 200;

        projectedX = projectedX * scale + screenWidth / 2;
        projectedY = projectedY * scale + screenHeight / 2;

        return new Vector3(projectedX, projectedY, projectedPoint.getZ());
    }

    @Override public String toString()
    {
        return Arrays.deepToString(matrix);
    }

}

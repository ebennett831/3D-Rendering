

public class Camera3D {
    

    Vector3 position;

    //angles in radians
    float pitch; //x axis
    float yaw; //y axis
    float roll; //z axis
    float fov; //usually 1 - 1.5


    public Camera3D(Vector3 position, float pitch, float yaw, float roll, float fov)
    {   
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.fov = fov;
    }

    public Matrix4x4 getViewMatrix()
    {
        Matrix4x4 pitchMatrix = Matrix4x4.createRotationX(pitch); 
        Matrix4x4 yawMatrix = Matrix4x4.createRotationY(yaw); 
        Matrix4x4 rollMatrix = Matrix4x4.createRotationZ(roll); 

        Matrix4x4 rotationMatrix = rollMatrix.multiply(pitchMatrix); //multiply the matricies
        rotationMatrix = rotationMatrix.multiply(yawMatrix); //roll * pitch * yaw

        Matrix4x4 translationMatrix = Matrix4x4.createTranslation(-position.getX(), -position.getY(), -position.getZ());

        return rotationMatrix.multiply(translationMatrix);

    }

    public Matrix4x4 getProjectionMatrix(int width, int height)
    {
        float aspectRatio = (float) height / width;
        float near = 0.1f;
        float far = 1000.0f;

        float tanHalfFov = (float) Math.tan(fov / 2.0f);

        float[][] projectionMatrixArray = new float[4][4];   

        //initialize all values in the matrix to zero
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++)
                projectionMatrixArray[row][col] = 0;

        //set elements in matrix according to perspective projeciton formula
        projectionMatrixArray[0][0] = 1 / (aspectRatio * tanHalfFov);
        projectionMatrixArray[1][1] = 1 / tanHalfFov;
        projectionMatrixArray[2][2] = -(far + near) / (far - near);
        projectionMatrixArray[2][3] = -(2 * far * near) / (far - near);
        projectionMatrixArray[3][2] = -1;

        return new Matrix4x4(projectionMatrixArray);
    }

    public void tranlate(float x, float y, float z)
    {
        position = position.add(new Vector3(-x, -y, -z));
    }


}

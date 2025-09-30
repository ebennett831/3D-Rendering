

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

    public void lookAt(Vector3 target)
    {
        Vector3 direction = target.subtract(position).normalize();
        
        //yaw 
        //use atan2 to get angle from x and z 
        float newYaw = (float) Math.atan2(-direction.getX(), direction.getZ());
        
        //pitch
        //use asin of y
        float newPitch = (float) Math.asin(direction.getY());
        
        yaw = newYaw;
        pitch = newPitch;
        
        //reseting the roll
        roll = 0;
    }
    
    public void tranlate(float x, float y, float z)
    {
        position = position.add(new Vector3(-x, -y, -z));
    }

    public void rotateX(float angleRadians)
    {
        pitch += angleRadians;
        
        //clamp pitch to prevent flipping over (gimbal lock)
        float maxPitch = (float) Math.PI / 2.0f - 0.1f; //just under 90 degrees
        float minPitch = -(float) Math.PI / 2.0f + 0.1f; //just over -90 degrees
        
        if (pitch > maxPitch) {
            pitch = maxPitch;
        } else if (pitch < minPitch) {
            pitch = minPitch;
        }
    }

    public void rotateX(float angleRadians, Vector3 origin)
    {
        position = position.rotateX(angleRadians, origin);
    }

    public void rotateY(float angleRadians)
    {
        yaw += angleRadians;
        
        //keep yaw within -PI to PI range to prevent issues
        while (yaw > Math.PI) {
            yaw -= 2 * Math.PI;
        }
        while (yaw < -Math.PI) {
            yaw += 2 * Math.PI;
        }
    }

    public void rotateY(float angleRadians, Vector3 origin)
    {
        position = position.rotateY(angleRadians, origin);
    }

    public void rotateZ(float angleRadians)
    {
        roll += angleRadians;
    }

    public void rotateZ(float angleRadians, Vector3 origin)
    {
        position = position.rotateZ(angleRadians, origin);
    }

    public void moveForward(float distance)
    {
        //calculate forward direction that matches exactly where camera is looking
        Vector3 forwardVector = new Vector3(
            (float) -Math.sin(yaw), //x
            (float) Math.sin(pitch), //y 
            (float) Math.cos(yaw) //z
        );
        
        position = position.add(forwardVector.scale(distance));
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public String positionToString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Camera X: " + position.getX() + " \n");
        sb.append("Camera Y: " + position.getY() + " \n");
        sb.append("Camera Z: " + position.getZ());
        return sb.toString();
    }

    public String rotationToString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Camera X Rotation: " + Math.toDegrees(pitch) + " \n");
        sb.append("Camera Y Rotation: " + Math.toDegrees(yaw) + " \n");
        sb.append("Camera Z Rotation: " + Math.toDegrees(roll));
        return sb.toString();
    }
}



public class Camera3D {
    

    Vector3 position;

    //angles in radians
    float pitch; //x axis
    float yaw; //y axis
    float roll; //z axis


    public Camera3D(Vector3 position, float pitch, float yaw, float roll)
    {   
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }
}

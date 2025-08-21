

//parent class for all shapes
public abstract class Shape3D {
    
    protected int color;

    public abstract void translate(float x, float y, float z);

    public abstract void scale(float x, float y, float z);
    public abstract void scale(float scalar);

    public abstract void rotateX(float angleRadians);
    public abstract void rotateX(float angleRadians, Vector3 origin);
    public abstract void rotateY(float angleRadians);
    public abstract void rotateY(float angleRadians, Vector3 origin);
    public abstract void rotateZ(float angleRadians);
    public abstract void rotateZ(float angleRadians, Vector3 origin);

    public void rotateXYZ(float x, float y, float z) 
    {
        rotateX(x);
        rotateY(y);
        rotateZ(z);
    }

    public void rotateXYZ(float x, float y, float z, Vector3 origin) 
    {
        rotateX(x, origin);
        rotateY(y, origin);
        rotateZ(z, origin);
    }

    public abstract Vector3 getCenter();
    public abstract void draw(RenderPanel rp);

    public abstract int getColor();
    public void setColor(int newColor) 
    {
        this.color = newColor;
    }

    public abstract Shape3D copy();

    public float distanceFrom(Vector3 point) 
    {
    return getCenter().distance(point);
    }

    public float distanceFrom(Shape3D other) 
    {
        return getCenter().distance(other.getCenter());
    }
}

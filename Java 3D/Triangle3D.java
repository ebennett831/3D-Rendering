
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
        if (!visible) return;

        Vector3 projectedPoint1 = Matrix4x4.projectToScreen(point1, rp.getWidth(), rp.getHeight());
        Vector3 projectedPoint2 = Matrix4x4.projectToScreen(point2, rp.getWidth(), rp.getHeight());
        Vector3 projectedPoint3 = Matrix4x4.projectToScreen(point3, rp.getWidth(), rp.getHeight());

        Vector3 normal = getNormal();
        Vector3 light = rp.getLight();

        int adjustedColor = rp.calculateLighting(color, normal, light);

        rp.fillTriangle(projectedPoint1, projectedPoint2, projectedPoint3, point1.getZ(), point2.getZ(), point3.getZ(), adjustedColor);
    }

    public void drawCamPOV(RenderPanel rp)
    {
        if (!visible) return;

        Camera3D cam = rp.getCamera();

        //early rejections
        Vector3 triangleCenter = getCenter();
        float distanceToCamera = triangleCenter.distance(cam.position);
        if (distanceToCamera > rp.getRenderDistance()) return; //skip far triangles

        //behind camera check
        Vector3 cameraToTriangle = triangleCenter.subtract(cam.position);
        Vector3 cameraForward = new Vector3(
            (float) -Math.sin(cam.yaw), 
            (float) Math.sin(cam.pitch), 
            (float) Math.cos(cam.yaw)
        );
        if (cameraToTriangle.dot(cameraForward) < 0) return; //triangle behind camera

        Matrix4x4 viewMatrix = cam.getViewMatrix();
        Matrix4x4 projectionMatrix = cam.getProjectionMatrix(rp.getWidth(), rp.getHeight());

        //transform using camera view matrix
        Vector3 camViewPoint1 = viewMatrix.transform(point1);
        Vector3 camViewPoint2 = viewMatrix.transform(point2);
        Vector3 camViewPoint3 = viewMatrix.transform(point3);

        //save z values for z buffer
        float z1 = camViewPoint1.getZ();
        float z2 = camViewPoint2.getZ();
        float z3 = camViewPoint3.getZ();

        // Don't render triangles behind the camera
        if (camViewPoint1.getZ() <= 0.5f || camViewPoint2.getZ() <= 0.5f || camViewPoint3.getZ() <= 0.5f) return; 

        //transform using camera projection matrix
        Vector3 projectedPoint1 = projectionMatrix.transform(camViewPoint1);
        Vector3 projectedPoint2 = projectionMatrix.transform(camViewPoint2);
        Vector3 projectedPoint3 = projectionMatrix.transform(camViewPoint3);

        //project to screen coordinates
        Vector3 screenPoint1 = Matrix4x4.camProjectToScreen(projectedPoint1, rp.getWidth(), rp.getHeight());
        Vector3 screenPoint2 = Matrix4x4.camProjectToScreen(projectedPoint2, rp.getWidth(), rp.getHeight());
        Vector3 screenPoint3 = Matrix4x4.camProjectToScreen(projectedPoint3, rp.getWidth(), rp.getHeight());

        //calculate lighting
        Vector3 camEdge1 = camViewPoint2.subtract(camViewPoint1);
        Vector3 camEdge2 = camViewPoint3.subtract(camViewPoint1);
        Vector3 normal = camEdge1.cross(camEdge2).normalize();

        //backface culling; don't render faces pointing away from camera
        if (normal.getZ() >= 0) return;

        Vector3 light = rp.getLight();
        light = viewMatrix.transform(light);

        int adjustedColor = rp.calculateLighting(color, normal, light);

        rp.fillTriangleScanLine(screenPoint1, screenPoint2, screenPoint3, z1, z2, z3, adjustedColor);
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

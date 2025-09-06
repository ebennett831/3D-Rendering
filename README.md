# 3D Rendering Engine (Java)

A custom 3D software renderer built from scratch in Java, featuring a complete graphics pipeline with camera controls, z-buffer depth testing, lighting, and real-time rendering.

## 🚧 Work in Progress

This project is currently under active development. The core rendering pipeline is functional, but the project is not yet ready for general use.

## Features

### Core Rendering Pipeline
- **Software Rasterization**: Custom triangle filling with z-buffer depth testing
- **3D Transformations**: Full matrix-based transformation pipeline (translation, rotation, scaling)
- **Camera System**: First-person camera with Euler angle rotation and movement controls
- **Projection**: Perspective projection with configurable field of view

### Graphics Features
- **Z-Buffer**: Accurate depth testing for proper occlusion
- **Lighting**: Phong lighting model with ambient and directional lighting
- **Backface Culling**: Automatic removal of faces pointing away from camera
- **Near-plane Clipping**: Prevents rendering artifacts for objects too close to camera

### Primitives & Shapes
- **Triangle3D**: Basic triangle primitive with lighting and transformations
- **Rectangle3D**: Quad primitive composed of triangles
- **Cube3D**: 3D cube with 6 faces
- **ComplexShape3D**: Container for combining multiple shapes

### Performance Optimizations
- **Early Triangle Rejection**: Distance and frustum culling
- **Optimized Matrix Operations**: Efficient transformation pipeline
- **Real-time Rendering**: 60 FPS target frame rate

## Project Structure

```
Java 3D/
├── Main.java              # Application entry point
├── RenderPanel.java       # Main rendering engine and game loop
├── Camera3D.java          # 3D camera with controls
├── Matrix4x4.java         # 4x4 matrix operations
├── Vector3.java           # 3D vector math
├── Shape3D.java           # Base shape class
├── Triangle3D.java        # Triangle primitive
├── Rectangle3D.java       # Rectangle primitive  
├── Cube3D.java           # Cube primitive
├── ComplexShape3D.java   # Multi-shape container
└── Test.java             # Testing utilities
```

## Controls (Planned)

- **WASD**: Camera rotation (pitch/yaw)
- **Arrow Keys**: Forward/backward movement
- **Space/Shift**: Up/down movement
- **Enter**: Look at target object

## Technical Details

### Rendering Pipeline
1. **Model Transformation**: Object-to-world space
2. **View Transformation**: World-to-camera space  
3. **Projection**: 3D-to-2D projection
4. **Screen Mapping**: 2D-to-screen coordinates
5. **Rasterization**: Triangle filling with z-testing

### Mathematics
- Custom Vector3 and Matrix4x4 implementations
- Perspective projection with configurable FOV
- Barycentric coordinate interpolation for z-buffer
- Cross product calculations for surface normals

## Development Status

### ✅ Completed
- [x] Core rendering pipeline
- [x] Camera system with proper controls
- [x] Z-buffer depth testing
- [x] Basic lighting model
- [x] Primitive shape rendering
- [x] Matrix transformation system

### 🔄 In Progress
- [ ] Performance optimizations
- [ ] Enhanced lighting models
- [ ] Texture mapping
- [ ] Input handling improvements

### 📋 Planned Features
- [ ] Scene management system
- [ ] Model loading (OBJ files)
- [ ] Advanced lighting (multiple light sources)
- [ ] Shadow mapping
- [ ] Wireframe rendering mode
- [ ] Animation system

## Requirements

- Java 8 or higher
- No external dependencies (pure Java implementation)

## Installation & Running

⚠️ **Not yet ready for distribution**

The project is currently in development and doesn't have a standardized build/run process yet.

## Contributing

This is a personal learning project, but feedback and suggestions are welcome!

## License

This project is for educational purposes. Feel free to use the code for learning about 3D graphics programming.

---

*Building a 3D renderer from scratch to understand the fundamentals of computer graphics and 3D mathematics.*
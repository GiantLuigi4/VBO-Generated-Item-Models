#version 150

#moj_import <light.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler0;
uniform sampler2D Sampler2;
uniform vec2 LightCoord;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;
out vec4 normal;

vec4 sample_lightmap(sampler2D lightMap, vec2 uv) {
    return texture(lightMap, clamp(uv / 15.0, vec2(0.5 / 16.0), vec2(15.5 / 16.0)));
}

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexDistance = length((ModelViewMat * vec4(Position, 1.0)).xyz);
    //vertexColor = Color * sample_lightmap(Sampler2, LightCoord);
    vertexColor = sample_lightmap(Sampler2, LightCoord);
    texCoord0 = UV0;
    normal = vec4(normalize(Normal), 0.0);
    //normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}

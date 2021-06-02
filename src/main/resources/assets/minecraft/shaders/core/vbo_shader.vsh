#version 150

#moj_import <light.glsl>

in vec3 Position;
in vec2 UV0;
in vec3 Normal;

uniform sampler2D Sampler2;
uniform vec2 LightCoord;
uniform vec4 MinMaxUV;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat4 TextureMat;

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
    vertexColor = sample_lightmap(Sampler2, LightCoord);
    vec2 texCoord = (TextureMat * vec4(UV0, 0, 1)).xy;
    float tx = mix(MinMaxUV.y, MinMaxUV.x, texCoord.x);
    float ty = mix(MinMaxUV.w, MinMaxUV.z, texCoord.y);
    texCoord0 = vec2(tx, ty);
    normal = vec4(normalize(Normal), 0.0);
}

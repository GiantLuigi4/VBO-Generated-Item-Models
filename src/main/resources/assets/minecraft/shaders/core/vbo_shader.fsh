#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler2;

uniform vec4 ColorMultiplier;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform vec3 Normal; //TODO
uniform mat4 TextureMat;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 normal;

out vec4 fragColor;

void main() {
    vec4 vertCol = vertexColor;
    vertCol.a = 1;
    vec4 color = texture(Sampler0, texCoord0) * vertCol;
    if (color.a < 0.1) {
        discard;
    }
    color *= vec4(ColorMultiplier.xyz, 1);
    color *= vec4(vec3(abs(dot(normal.xyz, vec3(0, 1, 0)))), 1);
    if (TextureMat != mat4(1)) {
        float fade = linear_fog_fade(vertexDistance, FogStart, FogEnd);
        fragColor = vec4(color.rgb * fade, color.a);
    } else {
        fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
    }
}

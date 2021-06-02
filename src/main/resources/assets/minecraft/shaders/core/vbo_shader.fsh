#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler2;

uniform vec4 ColorModulator;
uniform vec4 ColorMultiplier;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform vec4 MinMaxUV;
uniform vec2 LightCoord;
uniform vec3 Normal;

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
    fragColor = color;
    fragColor *= vec4(ColorMultiplier.xyz, 1);
    fragColor *= vec4(vec3(abs(dot(normal.xyz, vec3(0, 1, 0)))), 1);
}

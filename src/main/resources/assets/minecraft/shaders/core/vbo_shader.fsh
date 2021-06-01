#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler2;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform vec4 MinMaxUV;
uniform vec2 LightCoord;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 normal;

out vec4 fragColor;

float lerp(float pct,float start,float end) {return pct*start+((1.-pct)*end);}

//TODO: clean up the large amount of comment code here
void main() {
    //vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    float tx = lerp(texCoord0.x, MinMaxUV.x, MinMaxUV.y);
    float ty = lerp(texCoord0.y, MinMaxUV.z, MinMaxUV.w);
    //vec4 color = texture(Sampler0, texCoord0);
    //vec4 color = texture(Sampler0, texCoord0);
    //vec4 color = texture(Sampler0, (texCoord0 / 64) + 26./128);
    //vec4 color = texture(Sampler0, (texCoord0 / 4) - (MinMaxUV.xz));
    vec4 vertCol = vertexColor;
    vertCol.a = 1;
    vec4 color = texture(Sampler0, vec2(tx/* + (1./64)*/, ty)) * vertCol;
    //vec4 color = texture(Sampler0, vec2(tx/* + (1./64)*/, ty));
    if (color.a < 0.1) {
        discard;
    }
    fragColor = color;
    //fragColor *= texture(Sampler2, (LightCoord / 15.) + (0.5 / 15));
    //fragColor = vec4(LightCoord/15.,0,1);
    fragColor *= abs(dot(normal.xyz, normalize(vec3(0, 1, 0))));
    //fragColor = vec4(vec2(tx, ty), 1, 1);
    //fragColor = vec4(vec2(texCoord0.x, texCoord0.y), 1, 1);
    //fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}

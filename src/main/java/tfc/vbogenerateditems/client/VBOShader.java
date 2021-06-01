package tfc.vbogenerateditems.client;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;

import java.io.IOException;

public class VBOShader extends Shader {
	public final GlUniform vectorUV;
	public final GlUniform lightCoord;
	
	public VBOShader(ResourceFactory factory, String name, VertexFormat format) throws IOException {
		super(factory, name, format);
		this.vectorUV = this.getUniform("MinMaxUV");
		this.lightCoord = this.getUniform("LightCoord");
	}
}

package de.kania.shader;


import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform3fv;
import static org.lwjgl.opengl.GL20.glUniform4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

public class ShaderProgram {

	private static final Logger LOG = Logger.getLogger(ShaderProgram.class.getName());
	
    private Path vertexShaderFile;

    private Path fragmentShaderFile;

    private int shaderProgramId;

    public ShaderProgram(String vertexShaderFile, String fragmentShaderFile) {
        this.vertexShaderFile = Paths.get(vertexShaderFile);
        this.fragmentShaderFile = Paths.get(fragmentShaderFile);
        LOG.info("Using Vertex-Shader: " +  this.vertexShaderFile);
        LOG.info("Using Fragment-Shader: " +  this.fragmentShaderFile );
    }

    public void compile() {
        int vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
        String vertexShaderSource = null;
        try {
            vertexShaderSource = Files.readString(vertexShaderFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not read vertex shader.", e);
        }
        glShaderSource(vertexShaderId, vertexShaderSource);
        glCompileShader(vertexShaderId);
        if(glGetShaderi(vertexShaderId, GL_COMPILE_STATUS) == GL_FALSE){
            String log = glGetShaderInfoLog(vertexShaderId);
            glDeleteShader(vertexShaderId);
            throw new RuntimeException("Could not compile vertex shader: " + log);
        }

        int fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
        String fragmentShaderSource = null;
        try {
            fragmentShaderSource = Files.readString(fragmentShaderFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not read vertex shader.", e);
        }
        glShaderSource(fragmentShaderId, fragmentShaderSource);
        glCompileShader(fragmentShaderId);
        if(glGetShaderi(fragmentShaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            String log = glGetShaderInfoLog(fragmentShaderId);
            glDeleteShader(fragmentShaderId);
            throw new RuntimeException("Could not compile fragment shader: " + log);
        }

        shaderProgramId = glCreateProgram();
        glAttachShader(shaderProgramId, vertexShaderId);
        glAttachShader(shaderProgramId, fragmentShaderId);
        glLinkProgram(shaderProgramId);
        glValidateProgram(shaderProgramId);
        if(glGetProgrami(shaderProgramId, GL_LINK_STATUS) == GL_FALSE) {
            String log = glGetProgramInfoLog(shaderProgramId);
            glDeleteProgram(shaderProgramId);
            throw new RuntimeException("Could not link program: " + log);
        }

        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
    }

    public void setFloat(String uniformName, float value) {
    	glUniform1f(glGetUniformLocation(shaderProgramId, uniformName), value);
    }
    
    public void setVector3f(String uniformName, Vector3f vector) {
    	FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
    	vector.get(buffer);
    	glUniform3fv(glGetUniformLocation(shaderProgramId, uniformName), buffer);
    }
    
    public void setVector4f(String uniformName, Vector4f vector) {
    	FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
    	vector.get(buffer);
    	glUniform4fv(glGetUniformLocation(shaderProgramId, uniformName), buffer);
    }
    
    public void setMatrix4f(String uniformName, Matrix4fc matrix) {
    	FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    	matrix.get(buffer);
    	glUniformMatrix4fv(glGetUniformLocation(shaderProgramId, uniformName), false, buffer);
    }
    
    public void use() {
    	glUseProgram(shaderProgramId);
    }
    
    public void unuse() {
    	glUseProgram(0);
    }

    public void destroy() {
        glDeleteProgram(shaderProgramId);
    }

}

package de.kania;


public interface OpenGLProgram {

    public void defineGeometries();

    public void initializeState();

    public void renderGeometries();

    public void destroy();

}

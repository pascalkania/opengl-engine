#version 430 core

in vec4 color;
out vec4 fragColor;

uniform vec4 uniformColor;

void main(void) {
    if(color.r >= 0.5 && color.g >= 0.5 && color.b >= 0.5){
    	fragColor = uniformColor;
    }else{
    	fragColor = color;
    }
}

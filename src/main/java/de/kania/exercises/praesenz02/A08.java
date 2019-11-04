package de.kania.exercises.praesenz02;

import org.joml.Matrix3f;
import org.joml.Vector3f;

public class A08 {

	public static void main(String[] args) {
		Vector3f x = new Vector3f(1,3,4);
		
		Matrix3f A = new Matrix3f(2, -3, 4, 1, 1, 0, -1, 2, 3) ;
		Matrix3f B = new Matrix3f(2, 1, 0, 4, 3, -2, -1, 5, 5) ;
		
		Vector3f y = x.mul(A.mul(B));
		System.out.println(y);
	}
	
}

import java.util.*;
import java.io.*;

/**
 * Created by nikhilranjan on 12/3/16.
 * Assignment #5
 * Vector.java
 * This program represents the abstract data of a Vector. Using mathematical methods and classes
 * I was able to calculate methods of vectors using different basic vector formulas. This program uses
 * a test class to test my methods. The test class passes vector objects to the vector class.
 *
 * Authors: Nikhil Ranjan
 *
 * RESOURCES USED:
 * Complex.java, used to create test file, provided on Bailey's website,
 * https://classes.soe.ucsc.edu/cmps012a/Fall16/SECURE/PAs/pa5.html
 * Delbert Bailey (
 */


    class Vector {
        private double x; //real part
        private double y; //imaginary part

    //constructors
        public Vector(){}

        public Vector(double x, double y){
            this.x = x;
            this.y = y;
        }

        public double getRealPart(double x){
            return x;
        }

        public double getImagPart(double y){
            return y;
        }

    /**
     * USed to format the vectors
     * @return string statement for formatting
     */
    public String toString(){
            if (y < 0.0){
                return (x + "," + y );
            }
            else{
                return (x + "," + y);
            }
        }

    /**
     * Add vectors with passing one vector object
     * @param b, object vector
     * @return resulting vector
     */
    public Vector add(Vector b){
            return( new Vector(x + b.x, y + b.y));
        }

    /**
     * Subtracting vectors with passing one vector object
     * @param b vector object b
     * @return
     */
    public Vector sub(Vector b){
            return( new Vector(x - b.x, y - b.y));
        }

    /**
     * Finding magnitude of 2 vectors
     * @return magnitude of vector
     */
    public double mag() {
            return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));

        }

    /**
     * Dot product of 2 vectors
     * @param a vector a object
     * @return dot product of vector
     */
    public double dot(Vector a){
            return((a.x*x + a.y*y));
        }

    /**
     * Angle in between two vectors
     * @param a vector a object
     * @return degrees in between vector
     */
    public double angle(Vector a){
            double oneVector = Math.sqrt((Math.pow(a.x,2) + Math.pow(a.y,2)));
            double twoVector = Math.sqrt((Math.pow(x,2) + Math.pow(y,2)));
            double angle = ((a.x*x + a.y*y)/(oneVector*twoVector));
            double radian = Math.acos(angle);
            double degrees = Math.toDegrees(radian);
            return degrees;
        }

    /**
     * Vector created from the multiplied vector
     * @param a Vector a object
     * @param s scalar integer
     * @return new vector coordinates
     */
        public Vector scalar(Vector a, double s){
            return(new Vector(a.x*s, a.y*s));

        }

    }



Raw
 VectorTest.java
/**
 * Created by nikhilranjan on 12/3/16.
 *
 * VectorTest file to test methods in Vector class
 * Passes vector objects, instance vectors
 * New vector objects
 */
public class VectorTest {
    public static void main(String[] args){
        Vector a = new Vector(8.0,13.0);
        Vector b = new Vector(26.0,7.0);
        System.out.print("The sum of the vectors <8,13> and <26,7> is ");
        System.out.println(a.add(b)+"\n");

        Vector c = new Vector(12.0,2.0);
        Vector d = new Vector(4.0,5.0);
        System.out.print("The difference of the vectors <12, 2> and <4,5> is ");
        System.out.println(c.sub(d)+"\n");

        System.out.print("The magnitude of vector <-8,6> ");
        System.out.println(new Vector(-8.0,6.0).mag());
        System.out.print("\n");

        Vector e = new Vector(-6.0,8.0);
        Vector f = new Vector(5.0,12.0);
        System.out.print("The dot product of <-6,8> and <5,12> is ");
        System.out.println(e.dot(f)+"\n");

        Vector h = new Vector(3.0,4.0);
        Vector i = new Vector(-8.0,6.0);
        System.out.print("The angle in between the vectors <3,4> and <-8,6> is ");
        System.out.println(h.angle(i)+"\n");

        Vector j = new Vector(7.0,3.0);
        System.out.print("The vector <7,3> multiplied by scalar 3 ");
        System.out.println(j.scalar(j,3.0)+"\n");
    }
}

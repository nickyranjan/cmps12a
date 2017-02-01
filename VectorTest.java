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

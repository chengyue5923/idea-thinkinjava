package src;

/**
 * Created by lenovo on 2016/9/28.
 */
public class VarargType {
    static void f(Character... args){
      System.out.println(args.getClass());
        System.out.println("length"+args.length);

    }
    static void g(int... arg){
        System.out.println("args"+arg.getClass());
        System.out.println("length"+arg.length);
    }
    public static void main(String args[]){
        f('a');
        g(1);
        g(1,2,3,4,5,6,7,8);
        f('q','e','r','e','r','t');
    }
}

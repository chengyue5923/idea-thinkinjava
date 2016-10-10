package src;

/**
 * Created by lenovo on 2016/9/28.
 */
public class Varargs {
    static void printArrays(Object[] args){
        for(Object s:args)
            System.out.println("Object="+s);
        System.out.println();
    }
    static class A{
        public A(){
            System.out.println("A");
        }
    }
    public static void main(String args[]){

        printArrays(new Object[]{new Integer(47),new Float(3.14),new Double(11.33)});
        printArrays(new Object[]{"one","two","three"});
        printArrays(new Object[]{new A(),new A(),new A(),new A()});
    }
}

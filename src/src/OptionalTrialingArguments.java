package src;

/**
 *
 * 可变参数列表
 * Created by lenovo on 2016/9/28.
 */
public class OptionalTrialingArguments {
     static void f(int requment,String... trailing){
         System.out.println("requment"+requment);
         for(String s:trailing){
             System.out.println("traling"+s);
         }
         System.out.println();
     }
    public static void main(String args[]){
        f(1);
        f(2,"cheng","yue");
        f(3,"chengy","chengy","chengy","chengy");
    }
}

package src;

import java.util.Random;

/**
 * Created by lenovo on 2016/9/23.
 */
public class PassObject {
    static void f(Letter f){
        f.c='z';
    }
    public static void main(String args[]){
        Letter l=new Letter();
        l.c='a';
        System.out.println("lc"+l.c);
        f(l);
        System.out.println("lcx"+l.c);
        Random r=new Random(47);
        for(int i=0;i<50;i++){
            int number=r.nextInt(10)+2;
            System.out.println("第"+i+"Random:"+number);
        }

    }
}

package src;

import static net.mindview.util.Range.range;

/**
 * Created by lenovo on 2016/9/23.
 */
public class RoundingNumber {
    public static void main(String args[]){
        double above=0.7,below=0.4;
        float fabove=0.7f,fblow=0.4f;
        System.out.println("above"+Math.round(above)+"--"+above);
        System.out.println("above"+Math.round(below)+"--"+below);
        System.out.println("above"+Math.round(fabove)+"--"+fabove);
        System.out.println("above"+Math.round(fblow)+"--"+fblow);


        for(int i:range(10)){
           System.out.println("i="+i);
        }
        for(int i:range(5,15)){
            System.out.println("i=s"+i);
        }
        for(int i:range(5,15,3)){
            System.out.println("i=b"+i);
        }
    }
}

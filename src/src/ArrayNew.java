package src;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by lenovo on 2016/9/27.
 */
public class ArrayNew {
    public static void main(String args[]){
        int[] a;
        Random random=new Random(47);
        a=new int[random.nextInt(40)];
        int[][] b={{1,2,3,4},{11,22,33,44},{55,55,55,22,22},{33,11,44}};
        System.out.println("a.length"+a.length);
        for(int i=0;i<b.length;i++){
            for(int j=0;j<b[i].length;j++){
                System.out.print(b[i][j]+"      ");
            }
            System.out.println();
        }
        for(int i=0;i<b.length;i++){
//            for(int j=0;j<b[i].length;j++){
//                System.out.print(b[i][j]);
//            }
//            System.out.println();
            System.out.println(Arrays.toString(b[i]));
        }
        System.out.println(Arrays.toString(b));
        for(int s:a){
            System.out.println("a.value"+a[s]);
        }
    }
}

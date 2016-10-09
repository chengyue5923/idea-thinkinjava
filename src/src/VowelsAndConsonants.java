package src;

import java.util.Random;

/**
 * Created by lenovo on 2016/9/23.
 */
public class VowelsAndConsonants {
    public static void main(String args[]){
        int ibs = 0;
        System.out.println("logger"+ibs);
        Random rand=new Random(47);
        for(int i=0;i<100;i++){
            int c=rand.nextInt(26)+'a';
            System.out.println((char)c+","+c+":");
            switch (c){
                case 'a':
                    System.out.println("char"+c);
                case 'b':
                    System.out.println("char"+c);
                case 'c':
                    System.out.println("char"+c);
                case 'd':
                    System.out.println("char"+c);
                case 'e':
                    System.out.println("char"+c);
                case 'f':
                    System.out.println("char"+c);
                break;
                case 'm':
                case 'z':
                    System.out.println("char"+c);
                default: System.out.println("error--"+c);
            }


        }








    }


}

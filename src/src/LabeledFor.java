package src;

/**
 * Created by lenovo on 2016/9/23.
 */
public class LabeledFor {
    public static void main(String args[]){
        int i=0;
        outer:
        for(;true;){
            inner:
            for(;i<10;i++){
                System.out.println("i="+i);
                if(i==2){
                    System.out.println("i="+i+"continue");
                    continue ;
                }
                if(i==3){
                    System.out.println("i="+i+"break");
                    i++;
                    break;
                }
                if(i==7){
                    System.out.println("i="+i+"continue outer");
                    i++;
                    continue  outer;
                }
                if(i==8){
                    System.out.println("i="+i+"break outer");
                    break outer;
                }
                for(int k=0;k<5;k++){
                    if(k==3){
                        System.out.println("i="+i+"continue inner");
                        continue  inner;
                    }

                }
            }
        }
    }
}

package src;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //假设有i个鸡蛋
        for(int i=1;true;i++){
            if(i%1==0&&i%2==1&&i%3==0&&i%4==1&&i%5==1&&i%6==3&&i%7==0&&i%8==1&&i%9==0){
                System.out.println("筐里共有"+i+"个鸡蛋");
            }
        }
    }
}
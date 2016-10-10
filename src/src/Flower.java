package src;

/**
 * 构造方法中调用构造方法。调用构造方法是使用this返回当前对象。
 * Created by lenovo on 2016/9/27.
 */
public class Flower {
    int petalCount=0;
    String s="initial value";
    Flower(int petals){
        petalCount=petals;
        System.out.println("Constructor w/ int arg only petalCount="+petalCount);
    }
    Flower(String s){
        System.out.println("Strings"+s);
    }
    Flower(String s,int palets){
        this(palets);
        this.s=s;
//        this(s);
        System.out.println("this.palets+Strings");
    }
    Flower(){
        this("Hello",57);
        System.out.println("this is not have Constructor");
    }
    public void init(){
        System.out.println("this values"+petalCount+"-----"+s);
    }
    public static void main(String args[]){

        Flower flower=new Flower();
        flower.init();

    }
}

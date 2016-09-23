package src;

/**
 * Created by lenovo on 2016/9/8.
 */
public class Assignment {
    public static  void main(String args[]){
      //  System.out.print(new Date());
      Tank t1=new Tank();
      Tank t2=new Tank();
        t1.level=9;
        t2.level=47;
        System.out.println("print"+t1.level+"--"+t2.level);
//        t1=t2;
        t1.level=t2.level;
        System.out.println("println"+t1.level+"--"+t2.level);
        t1.level=27;
        System.out.println("printlnd"+t1.level+"--"+t2.level);



    }
}


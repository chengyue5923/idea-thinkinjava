package src;

/**
 * Created by Administrator on 2016/10/17.
 */
public class Insect {

    private int i=9;
    protected int j;
    Insect(){
        System.out.println(i+"----"+j);
        j=39;
    }
    private static int x1=printInit("static Insect x2 initialized");
    static int  printInit(String s){
        System.out.println(s);
        return 47;
    }
}

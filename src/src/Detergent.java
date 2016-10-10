package src;

/**
 * Created by lenovo on 2016/10/10.
 */
public class Detergent extends Cleanser {
    public void srcub(){
        super.srcub();
    }
    public void diulut(){
        super.diulut();
    }
    public static void main(String args[]){
        Detergent d=new Detergent();
        d.srcub();
        d.diulut();
        System.out.println(d);
        Cleanser.main(args);
    }

}

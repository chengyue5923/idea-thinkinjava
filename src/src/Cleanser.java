package src;

/**
 * Created by lenovo on 2016/10/10.
 */
public class Cleanser {
    private String s="Cleanser";
    public void append(String a){
        s+=a;
    }
    public void diulut(){
        append("diulut");
    }
    public void apply(){
        append("apply");
    }
    public void srcub(){
        append("srcub");
    }
    public String toString(){
        return s;
    }
    public static void main(String args[]){
        Cleanser cleanser=new Cleanser();
        cleanser.apply();
        cleanser.diulut();
        System.out.println(cleanser);
    }
}

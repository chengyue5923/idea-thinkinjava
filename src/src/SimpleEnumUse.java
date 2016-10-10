package src;

/**
 * Created by lenovo on 2016/9/29.
 */
public class SimpleEnumUse {
    public static void main(String args[]){
        Spiciness spiciness=Spiciness.HOT;
        System.out.println(spiciness);
        for(Spiciness s:Spiciness.values());
    }
}

package src;

/**
 * Created by lenovo on 2016/10/10.
 */
public class SprinklerSystem {
    private String value1,value2,value3,value4;
    private int l;
    private float f;
    private Water water=new Water();
    public String toString(){
        return value1+value2+value3+value4+l+f+water;
    }
    public static void main(String args[]){
        SprinklerSystem sprinklerSystem=new SprinklerSystem();
        System.out.println(sprinklerSystem);
    }
}

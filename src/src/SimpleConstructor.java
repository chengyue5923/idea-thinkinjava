package src;

/**
 * Created by lenovo on 2016/9/27.
 */
public class SimpleConstructor {
    public static void main(String args[]){
        for(int i=0;i<10;i++){
            new Rock();
        }
    }
    static class Rock{
        public Rock(){
            System.out.println("Rock");
        }
    }
}

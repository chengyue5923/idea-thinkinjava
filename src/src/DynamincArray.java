package src;

/**
 * Created by lenovo on 2016/9/28.
 */
public class DynamincArray {
    public static void main(String args[]){
        Other.main(new String[]{"cheng","yue","ll"});
    }
    static class Other{
        public static void main(String args[]){
            for(String s:args){
                System.out.println("Logger"+s);
            }
        }
    }
}

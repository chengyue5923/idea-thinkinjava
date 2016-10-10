package src;

/**
 * Created by lenovo on 2016/9/27.
 */
public class Passingthis {
    static class Person{
        public void eat(Apple apple){
            Apple peeled =apple.getPeeled();
            System.out.println("Yummy");
        }
    }
    static class Peeler{
         static Apple peel(Apple apple){
            return apple;
        }
    }
    static class Apple {
        Apple getPeeled(){
            return Peeler.peel(this);
        }
    }
    public static void main(String args[]){
        new Person().eat(new Apple());
    }
}

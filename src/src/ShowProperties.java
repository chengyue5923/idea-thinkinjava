package src;

import java.util.Date;

/**
 * Created by lenovo on 2016/9/8.
 */
public class ShowProperties {
    public  static void main(String args[]){
        System.out.println(new Date());
        System.getProperties().list(System.out);
        System.out.println(System.getProperty("User.name"));
        System.out.println(System.getProperty("java.library.path"));
    }
}

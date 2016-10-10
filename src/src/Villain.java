package src;

/**
 * Created by lenovo on 2016/10/10.
 */
public class Villain {
    private String name;
    protected void set(String nm){
        name=nm;
    }
    public Villain(String name){
        this.name=name;
    }
    public String toString(){
        return name;
    }
}

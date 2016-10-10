package src;

/**
 * Created by lenovo on 2016/10/10.
 */
public class Orc extends  Villain {
    private int orcNumber;
    public Orc(String name) {
        super(name);
    }
    public Orc(String name,int orcNumber){
        super(name);
        this.orcNumber=orcNumber;
    }
    public void change(String name,int orcNumber){
        set(name);
        this.orcNumber=orcNumber;
    }
    public String toString(){
        return "Orc+"+orcNumber+super.toString();
    }
    public static void main(String args[]){
        Orc orc=new Orc("chengyue",23);
        System.out.println(orc);
        orc.change("lim",24);
        System.out.println(orc);

    }

}

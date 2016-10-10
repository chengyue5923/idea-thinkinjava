package src;

/**
 * Created by lenovo on 2016/10/10.
 */
public class Bath {
    private String s1="happy",s2="Happy",s3,s4;
    private Soap castaic;
    private int l;
    private float f;
    public Bath() {
        System.out.println("Inside Bath()");
        s3 = "joy";
        f = 3.14f;
        castaic = new Soap();
    }
    {l=47;}
    public String toString(){
        return s1+s2+s3+s4+l+f+castaic;
    }
    public static void main(String args[]){
        System.out.println(new Bath());
    }
}

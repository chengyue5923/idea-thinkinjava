package src;

/**
 * Created by lenovo on 2016/10/10.
 */
public class FinalArgument {
    void with(final Gizom gizom){
    }
    void without(Gizom gizom){
        gizom=new Gizom();
        gizom.spin();
    }
    int g(final int i){
        return i+1;
    }
    public static void main(String args[]){
        FinalArgument bf=new FinalArgument();
        bf.with(null);
        bf.without(null);
    }
}

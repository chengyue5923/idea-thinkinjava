package src;

/**
 *
 * this 关键字返回对对象的引用。实现最统一对象进行多次操作
 * Created by lenovo on 2016/9/27.
 */
public class Leaf {
    int i=0;
     public Leaf increment(){
        i++;
        return this;
    }
    public void printWord(){
        System.out.println("PrintWord"+i);
    }
    public  static void main(String args[]){
        Leaf leaf=new Leaf();
        leaf.increment().increment().increment().printWord();
    }
}

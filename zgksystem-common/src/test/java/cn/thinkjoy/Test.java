package cn.thinkjoy;

/**
 * Created by wangyongqiang on 15/9/4.
 */
public class Test {
    public static void main(String args[]){
        int temp=0;
        for(int i=1;i<=100;i++){
            System.out.println(".progressbar-"+i+"{background-position: -"+temp+"px 0px;}");
            temp+=130;
        }
    }
}

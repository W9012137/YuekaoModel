package chencheng.bwie.com.yuekaomodel.net;

public interface NetListenter<T> {
    //成功
    public void onSccess(T t);
    //失败
    public void onFailuer(Exception e);
}

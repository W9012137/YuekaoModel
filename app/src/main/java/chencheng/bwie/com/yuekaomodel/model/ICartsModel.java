package chencheng.bwie.com.yuekaomodel.model;

import chencheng.bwie.com.yuekaomodel.bean.CartsBean;
import chencheng.bwie.com.yuekaomodel.bean.DeleteBean;
import chencheng.bwie.com.yuekaomodel.net.NetListenter;

public interface ICartsModel {
    void GetCarts(String uid,NetListenter<CartsBean> netListenter);
    void getdelete(String uid, String pid, NetListenter<DeleteBean> onNetListner);
}

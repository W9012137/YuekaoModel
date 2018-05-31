package chencheng.bwie.com.yuekaomodel.view;

import java.util.List;

import chencheng.bwie.com.yuekaomodel.bean.CartsBean;
import chencheng.bwie.com.yuekaomodel.bean.DeleteBean;

public interface ICartsView {
    void showList(List<CartsBean.DataBean> groupList, List<List<CartsBean.DataBean.ListBean>> childList , String uid);
    void showDelete(DeleteBean deleteBean);
}

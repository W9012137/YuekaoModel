package chencheng.bwie.com.yuekaomodel.model;

import java.util.HashMap;

import chencheng.bwie.com.yuekaomodel.bean.CartsBean;
import chencheng.bwie.com.yuekaomodel.bean.DeleteBean;
import chencheng.bwie.com.yuekaomodel.net.NetListenter;
import chencheng.bwie.com.yuekaomodel.net.RetrofitUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartsModel implements ICartsModel {
    @Override
    public void GetCarts(String uid, final NetListenter<CartsBean> netListenter) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("uid",uid);
        Observable observable= RetrofitUtils.getServerApi().carts("product/getCarts",map,"android");
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<CartsBean>() {

                    @Override
                    public void accept(CartsBean cartsBean) throws Exception {
                        netListenter.onSccess(cartsBean);
                    }


                });
    }

    @Override
    public void getdelete(String uid, String pid, final NetListenter<DeleteBean> onNetListner) {
        Observable delete = RetrofitUtils.getServerApi().delete(uid, pid);
        delete.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<DeleteBean>() {

                    @Override
                    public void accept(DeleteBean deleteBean) throws Exception {
                        onNetListner.onSccess(deleteBean);
                    }


                });

    }
}

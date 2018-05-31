package chencheng.bwie.com.yuekaomodel.net;

import java.util.Map;

import chencheng.bwie.com.yuekaomodel.bean.CartsBean;
import chencheng.bwie.com.yuekaomodel.bean.DeleteBean;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ServerApi {
    //查询购物车
  @POST
  Observable<CartsBean> carts(@Url String url, @QueryMap Map<String ,String> map, @Query("source") String source);
  //删除购物车
  @POST("product/deleteCart")
  Observable<DeleteBean> delete(@Query("uid") String uid, @Query("pid") String pid);
}

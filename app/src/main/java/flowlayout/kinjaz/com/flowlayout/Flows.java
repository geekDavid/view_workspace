package flowlayout.kinjaz.com.flowlayout;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator
 * 创建时间：2018/5/2
 * 更新时间：
 * 更新人：
 * 描述：FLow就是布局适配器顶层接口
 */

public interface Flows {

    /**
     * getView实现获取用户自定义View
     *
     * @return 对应的View
     */
    View getView(View convertView, int position, ViewGroup parent);

    /**
     * 获取View的数量，由用户控制显示数量
     *
     * @return 返回数量
     */
    int getCount();

    /**
     * 刷新整个列表
     */
    void notifyDataSetChange();

//    /**
//     * 刷新某个item
//     *
//     * @param position 刷新item的位置
//     */
//    void notifyItemChange(int position);
//
//    /**
//     * 添加某个item到某个位置
//     *
//     * @param position 添加新item到哪个位置
//     */
//    void notifyAddItem(int position);
//
//    /**
//     * 添加某个item到末尾位置
//     */
//    void notifyAddItem();
//
//    /**
//     * 刷新整个列表
//     *
//     * @param position 添加新item到哪个位置
//     */
//    void notifyDeleteItem(int position);


}

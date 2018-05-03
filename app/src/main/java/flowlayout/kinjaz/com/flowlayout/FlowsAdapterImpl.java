package flowlayout.kinjaz.com.flowlayout;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator
 * 创建时间：2018/5/2
 * 更新时间：
 * 更新人：
 * 描述：用于扩展的实现类，后期会增加基于实现类，实现不同功能的适配器
 */

public abstract class FlowsAdapterImpl implements Flows {
    protected ArrayList<View> mViews = new ArrayList<>();

    @Override
    public void notifyDataSetChange() {

    }

    /**
     * 获取对应位置的View
     *
     * @param position
     * @return 对应位置的View
     */
    public View getItemView(int position) {
        if (getCount() <= 0) {
            throw new IndexOutOfBoundsException("Count is 0 ,but you position is" + position);
        }
        return mViews.get(position);
    }
}

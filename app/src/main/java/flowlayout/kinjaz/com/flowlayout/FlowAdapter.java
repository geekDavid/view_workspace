package flowlayout.kinjaz.com.flowlayout;

/**
 * Created by KinJaz David
 * 创建时间：2018/5/2
 * 更新时间：
 * 更新人：
 * 描述：Flow适配器
 */

public abstract class FlowAdapter extends FlowsAdapterImpl {

    private final FlowObservable mFlowObservable = new FlowObservable();

    public void registerDataSetObserver(FlowObserver observer) {
        mFlowObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(FlowObserver observer) {
        mFlowObservable.unregisterObserver(observer);
    }

    @Override
    public void notifyDataSetChange() {
        super.notifyDataSetChange();
        mFlowObservable.notifyChanged();
    }


    public boolean isEmpty() {
        return getCount() == 0;
    }


}

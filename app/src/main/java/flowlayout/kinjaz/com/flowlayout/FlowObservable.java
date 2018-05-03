package flowlayout.kinjaz.com.flowlayout;

import android.database.Observable;

/**
 * Created by  Kinjaz David
 * 创建时间：2018/5/3
 * 更新时间：
 * 更新人：
 * 描述：
 */

public class FlowObservable extends Observable<FlowObserver> {

    public void notifyChanged() {
        synchronized (mObservers) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }
    }

    /**
     * 当数据集不再有效且无法再次查询时调用，*例如当数据集已关闭时
     */
    public void notifyInvalidated() {
        synchronized (mObservers) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onInvalidated();
            }
        }
    }
}

package flowlayout.kinjaz.com.flowlayout;

/**
 * Created by  Kinjaz David
 * 创建时间：2018/5/3
 * 更新时间：
 * 更新人：
 * 描述：
 */

public abstract class FlowObserver {

    /**
     * This method is called when the entire data set has changed
     */
    public void onChanged() {
        // Do nothing
    }

    /**
     * This method is called when the entire data becomes invalid
     */
    public void onInvalidated() {
        // Do nothing
    }
}

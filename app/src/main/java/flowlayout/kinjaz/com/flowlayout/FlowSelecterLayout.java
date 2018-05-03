package flowlayout.kinjaz.com.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kinjaz David
 * 创建时间：2018/5/2
 * 更新时间：
 * 更新人：
 * 描述：跟随的布局，布局主要实现流式标签布局
 */

public class FlowSelecterLayout extends ViewGroup {

    //行间距
    private int verticalSpace = dip2px(getContext(), 5);
    //标签间距
    private int horizontalSpace = dip2px(getContext(), 5);


    public FlowSelecterLayout(Context context) {
        super(context);
    }

    public FlowSelecterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取控件宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //通知子控件测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //获取内容的宽度
        int contentWidth = width - getPaddingLeft() - getPaddingRight();
        //获取子控件数量
        int childCount = getChildCount();
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (childCount > 0) {
            int currentWidth = 0;
            for (int i = 0; i < childCount; i++) {
                //拿到对应顺序的子View
                View childView = getChildAt(i);
                if (childView == null) continue;
                //获取子View的高度
                int childWidth = childView.getMeasuredWidth();
                Log.e("flowLayout", "childWidth: " + childWidth);

                if (i == 0) {
                    //高度设置为第一个子View的高度
                    height = childView.getMeasuredHeight();
                }
                //如果当前的子View的宽度大于了Group的宽度
                if (childWidth > contentWidth) {
                    //设置当前的宽度为子View的宽度
                    currentWidth = contentWidth;
                    //如果大于则需要另起一行，高度需要改变
                    height += childView.getMeasuredHeight() + verticalSpace;
                    continue;
                }


                //判断当前宽度加上当前子View的宽度+行间距是否大于Content宽度
                if ((currentWidth + childWidth + horizontalSpace) > contentWidth) {
                    currentWidth = childWidth + getPaddingLeft();
                    //如果大于则需要另起一行，高度需要改变
                    height += childView.getMeasuredHeight() + verticalSpace;
                    Log.e("flowLayout", "height: " + height);
                } else {
                    //如果未大于，添加到右边，当前宽度+上当前子控件宽度加标签间距
                    currentWidth += childWidth + horizontalSpace;
                }
                Log.e("flowLayout", "currentWidth: " + currentWidth);
                Log.e("flowLayout", "*---------------------");
            }
            height += getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(width, height);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //获取宽度
        int width = getMeasuredWidth();
        //获取paddingLeft
        int left = getPaddingLeft();
        //获取paddingTop
        int top = getPaddingTop();
        //获取子View的数量
        int childCount = getChildCount();
        //内容的宽度
        int contentWidth = width - getPaddingLeft() - getPaddingRight();

        if (childCount > 0) {
            int childHeight = 0;
            for (int i = 0; i < childCount; i++) {
                //拿到对应顺序的子View
                View childView = getChildAt(i);
                if (childView == null) continue;
                //获取子View的宽度
                int childWidth = childView.getMeasuredWidth();
                //如果当前的子View的宽度大于了Group的宽度
                if (childWidth > contentWidth) {
                    //将Group的宽度赋值给该子View
                    childWidth = contentWidth;
                }
                if (i == 0) {
                    //获取第一个View的高度
                    childHeight = childView.getMeasuredHeight();
                    //设置布局
                    childView.layout(left, top, childWidth + left, childHeight + top);
                    //左边的距离加上子宽度+标签布局
                    left += childWidth + horizontalSpace;
                    continue;
                }
                //如果左边+子View的宽度+标签间距大于内容宽度
                if ((left + childWidth + horizontalSpace) > contentWidth) {
                    //如果超出了内容边界，则让左边重新赋值为Group的paddingLeft
                    left = getPaddingLeft();
                    //top则要加上一个控件高度及行间距
                    top += childHeight + verticalSpace;
                }
                childView.layout(left, top, childWidth + left, childHeight + top);
                left += childWidth + horizontalSpace;


            }
        }
    }

    /**
     * dip转px
     *
     * @param context  上下文
     * @param dipValue 转换的值
     * @return 转换后的结果值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dip
     *
     * @param context 上下文
     * @param pxValue 转换的值
     * @return 转换后的结果值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

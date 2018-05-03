package flowlayout.kinjaz.com.flowlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kinjaz David
 * 创建时间：2018/5/2
 * 更新时间：
 * 更新人：
 * 描述：跟随的布局，布局主要实现流式标签布局
 */

public class FlowLayout extends ViewGroup {
    //适配器
    private FlowAdapter mAdapter;
    //观察者
    private AdapterDataObserver mAdapterDataObserver;
    private OnItemClickLinstener mItemClickLinstener;
    private OnItemLongClickLinstener mItemLongClickLinstener;
    //适配器设定的条目数
    private int itemCount;

    private boolean isClick = true;
    //行间距
    private int verticalSpace = dip2px(getContext(), 5);
    //标签间距
    private int horizontalSpace = dip2px(getContext(), 5);


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        verticalSpace = (int) typedArray.getDimension(R.styleable.FlowLayout_vertical_szie, dip2px(getContext(), 5));
        horizontalSpace = (int) typedArray.getDimension(R.styleable.FlowLayout_horizontal_szie, dip2px(getContext(), 5));

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
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (itemCount > 0) {
            int currentWidth = 0;
            for (int i = 0; i < itemCount; i++) {
                //拿到对应顺序的子View
                View childView = getChildAt(i);
                if (childView == null) continue;
                //获取子View的高度
                int childWidth = childView.getMeasuredWidth();

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
                } else {
                    //如果未大于，添加到右边，当前宽度+上当前子控件宽度加标签间距
                    currentWidth += childWidth + horizontalSpace;
                }
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
        //内容的宽度
        int contentWidth = width - getPaddingLeft() - getPaddingRight();

        if (itemCount > 0) {
            int childHeight = 0;
            for (int i = 0; i < itemCount; i++) {
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
                if ((left + childWidth + horizontalSpace) > width) {
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
     * 设置适配器
     *
     * @param adapter 适配器
     */
    public void setAdapter(FlowAdapter adapter) {
        if (adapter == null) return;
        mAdapter = adapter;
        itemCount = adapter.getCount();
        mAdapterDataObserver = new AdapterDataObserver();
        mAdapter.registerDataSetObserver(mAdapterDataObserver);
        addContentView();
        requestLayout();
    }

    /**
     * 添加View到内容中，该View为用户设置的布局
     */
    private void addContentView() {
        for (int i = 0; i < itemCount; i++) {
            View childView = mAdapter.getView(null, i, this);
            if (childView == null) {
                throw new NullPointerException("child view is null ! index = " + i);
            }
            clickItem(childView, i);
            addView(childView);
        }
    }

    /**
     * item点击行为的设置
     *
     * @param childView 最顶层子View
     * @param i         指针坐标
     */
    private void clickItem(View childView, final int i) {
        childView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickLinstener != null)
                    mItemClickLinstener.onItemClick(i, v);
            }
        });
        childView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemLongClickLinstener != null)
                    mItemLongClickLinstener.onItemLongClick(i, v);
                return false;
            }
        });
    }

    /**
     * 是否可以点击
     *
     * @return true为可以点击，false为不可点击
     */
    public boolean isClick() {
        return isClick;
    }

    /**
     * 设置控件的所有子View与子ViewGroup是否可以点击
     *
     * @param click 标志
     */
    public void setClick(boolean click) {
        isClick = click;
    }


    /**
     * 去获取当前的适配器
     *
     * @return 当前的适配器
     */
    public FlowAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAdapter != null && mAdapterDataObserver != null) {
            mAdapter.unregisterDataSetObserver(mAdapterDataObserver);
            mAdapterDataObserver = null;
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

    public class AdapterDataObserver extends FlowObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            if (getAdapter() != null) {
                removeAllViews();
                itemCount = getAdapter().getCount();
                addContentView();
                requestLayout();
            }
        }
    }

    public void setItemClickLinstener(OnItemClickLinstener itemClickLinstener) {
        mItemClickLinstener = itemClickLinstener;
    }

    public interface OnItemClickLinstener {
        void onItemClick(int position, View view);
    }

    public void setItemLongClickLinstener(OnItemLongClickLinstener itemLongClickLinstener) {
        mItemLongClickLinstener = itemLongClickLinstener;
    }

    public interface OnItemLongClickLinstener {
        void onItemLongClick(int position, View view);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /*
        * 根据拦截事件判断，如果需要拦截的话返回false则
        * 不拦截，flowlayout就会分发给子类这里会返回true。
        * onInterceptTouchEvent(ev)返回的是false，不会进
        * if语句，直接调用super.dispatchTouchEvent(ev)
        * */
        if (onInterceptTouchEvent(ev)) {
            return !onInterceptTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*
        * 拦截事件，用于根据是否触摸事件判断，
        * 如果要是触摸事件返回true则表示要消费，
        * 这里就返回true就是要拦截。
        * */
        return onTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*
        * 这里用于判断是否让全部的item等点击，
        * 如果为false说明flowlayout的item不能点击，
        * 则说明flowlayout要自己消费触摸时间，则返
        * 回的是非false就是true。
        * */
        return !isClick;
    }
}

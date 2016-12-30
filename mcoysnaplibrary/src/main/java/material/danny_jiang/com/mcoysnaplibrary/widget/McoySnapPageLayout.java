package material.danny_jiang.com.mcoysnaplibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

import material.danny_jiang.com.mcoysnaplibrary.util.LogUtil;

/**
 * @author jiangxinxing---mcoy in English
 * 
 * 了解此ViewGroup之前， 有两点一定要做到心中有数
 * 一个是对Scroller的使用， 另一个是对onInterceptTouchEvent和onTouchEvent要做到很熟悉
 * 以下几个网站可以做参考用
 * http://blog.csdn.net/bigconvience/article/details/26697645
 * http://blog.csdn.net/androiddevelop/article/details/8373782
 * http://blog.csdn.net/xujainxing/article/details/8985063
 */
public class McoySnapPageLayout extends ViewGroup {
	private final String TAG = "McoySnapPageLayout";

	private VelocityTracker mVelocityTracker;
    private int mMaximumVelocity;
    private static final int SNAP_VELOCITY = 1000;
    
    public static final int FLIP_DIRECTION_CUR = 0;
	public static final int FLIP_DIRECTION_UP = -1;
	public static final int FLIP_DIRECTION_DOWN = 1;
	
	private int mFlipDrection = FLIP_DIRECTION_CUR;
	
	private int mCurrentScreen = 0;

	private float mLastMotionY;

	// 记录触摸状态
	private final static int TOUCH_STATE_REST = 0;
	private final static int TOUCH_STATE_SCROLLING = 1;
	private int mTouchState = TOUCH_STATE_REST;
	
	private Scroller mScroller;  //mcoy add view滑动的矢量， 并没有真正滑动的功能
	
	private McoySnapPage mPageTop, mPageBottom;

	//记录所有被添加到此Layout中的McoySnapPage
	private List<McoySnapPage> allPages;

	private PageSnapedListener mPageSnapedListener;
	
	//这个值表示需要第一页和第二页之间的鸿沟
	private int gapBetweenTopAndBottom;

	public interface McoySnapPage {
		/**
		 * 返回page根节点
		 * 
		 * @return
		 */
		View getRootView();

		/**
		 * 是否滑动到最顶端
		 * 第二页必须自己实现此方法，来判断是否已经滑动到第二页的顶部
		 * 并决定是否要继续滑动到第一页
		 */
		boolean isAtTop();

		/**
		 * 是否滑动到最底部
		 * 第一页必须自己实现此方法，来判断是否已经滑动到第二页的底部
		 * 并决定是否要继续滑动到第二页
		 */
		boolean isAtBottom();
	}

	public interface PageSnapedListener {

		/**
		 * @mcoy
		 * 当从某一页滑动到另一页完成时的回调函数
		 */
		void onSnapedCompleted(int derection);
	}
	
	public void setPageSnapListener(PageSnapedListener listener){
		mPageSnapedListener = listener;
    }

	public McoySnapPageLayout(Context context, AttributeSet att) {
		this(context, att, 0);
	}
	
	public McoySnapPageLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initViews();
	}

	private void initViews() {
		allPages = new ArrayList<>();

		mScroller = new Scroller(getContext());
		
		gapBetweenTopAndBottom = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		mMaximumVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// The children are given the same width and height as the workspace
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			try {
				getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		int childTop = 0;
		int count = getChildCount();
		// 设置布局，将子视图顺序竖屏排列
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				final int childWidth = child.getMeasuredWidth();
				final int childHeight = child.getMeasuredHeight();
				childTop = childHeight * i;
				child.layout(0, childTop, childWidth,
						childTop + childHeight);
			}
		}
		if(count > 0){
			snapToScreen(mCurrentScreen);
		}
	}

	/**
	 * 添加一页可滑动的页面，并刷新
	 * @param mcoySnapPage
     */
	public void addSnapPage(McoySnapPage mcoySnapPage){
		allPages.add(mcoySnapPage);

		addView(mcoySnapPage.getRootView());

		postInvalidate();
	}

	/**
	 * @mcoy add
	 * computeScroll方法会调用postInvalidate()方法， 而postInvalidate()方法中系统
	 * 又会调用computeScroll方法， 因此会一直在循环互相调用， 循环的终结点是在computeScrollOffset()
	 * 当computeScrollOffset这个方法返回false时，说明已经结束滚动。
	 * 
	 * 重要：真正的实现此view的滚动是调用scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
	 */
	@Override
	public void computeScroll() {
		//先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {
			if (mScroller.getCurrY() == (mScroller.getFinalY())) {

				if(mPageSnapedListener != null){
					mPageSnapedListener.onSnapedCompleted(mFlipDrection);
				}
			}
			//这里调用View的scrollTo()完成实际的滚动
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			//必须调用该方法，否则不一定能看到滚动效果
			postInvalidate();
		}
	}

	public McoySnapPage getCurrentSnapPage() {
		return allPages.get(mCurrentScreen);
	}

	public View getCurrentView() {
		return getChildAt(mCurrentScreen);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.ViewGroup#onInterceptTouchEvent(android.view.MotionEvent)
	 * 重写了父类的onInterceptTouchEvent()，主要功能是在onTouchEvent()方法之前处理
	 * touch事件。包括：down、up、move事件。
	 * 当onInterceptTouchEvent()返回true时进入onTouchEvent()。
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		LogUtil.e("McoySnapPageLayout--onInterceptTouchEvent state is " + mTouchState);

		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}
		final float x = ev.getX();
		final float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_MOVE:
			LogUtil.e("MOVING-currentView is " + mCurrentScreen);
			// 记录y与mLastMotionY差值的绝对值。
            // yDiff大于gapBetweenTopAndBottom时就认为界面拖动了足够大的距离，屏幕就可以移动了。
			final int yDiff = (int)(y - mLastMotionY);
			boolean yMoved = Math.abs(yDiff) > gapBetweenTopAndBottom;
			if (yMoved) {
				boolean atBottom = allPages.get(mCurrentScreen).isAtBottom();
				boolean atTop = allPages.get(mCurrentScreen).isAtTop();
				LogUtil.e("atBottom is " + atBottom + " atTop is " + atTop);
				boolean shouldUp = yDiff < 0 && atBottom;
				boolean shouldDown = yDiff > 0 && atTop;
				LogUtil.e("shouldUp is " + shouldUp + " shouldDown is " + shouldDown);
				if(shouldUp || shouldDown){
					LogUtil.e("should trigger scroll to another page");
					mTouchState = TOUCH_STATE_SCROLLING;
				}
			}
			break;
		case MotionEvent.ACTION_DOWN:
			// Remember location of down touch
			mLastMotionY = y;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// Release the drag-
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		boolean intercept = mTouchState != TOUCH_STATE_REST;
		LogUtil.e("McoySnapPageLayout---onInterceptTouchEvent return " + intercept);
		return intercept;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 * 主要功能是处理onInterceptTouchEvent()返回值为true时传递过来的touch事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.e("mcoy", "onTouchEvent--" + System.currentTimeMillis());
	    if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        
		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			break;
		case MotionEvent.ACTION_MOVE:
		    if(mTouchState != TOUCH_STATE_SCROLLING){
                 // 记录y与mLastMotionY差值的绝对值。
                 // yDiff大于gapBetweenTopAndBottom时就认为界面拖动了足够大的距离，屏幕就可以移动了。
                final int yDiff = (int) Math.abs(y - mLastMotionY);
                boolean yMoved = yDiff > gapBetweenTopAndBottom;
                if (yMoved) {
                	mTouchState = TOUCH_STATE_SCROLLING;
                }
            }
            // 手指拖动屏幕的处理
            if ((mTouchState == TOUCH_STATE_SCROLLING)) {
                // Scroll to follow the motion event
                final int deltaY = (int) (mLastMotionY - y);
                mLastMotionY = y;
                final int scrollY = getScrollY();

				if (getCurrentSnapPage() != null && getCurrentSnapPage().isAtBottom()) {
					scrollBy(0, Math.max(-1 * scrollY, deltaY));
				} else if (getCurrentSnapPage() != null && getCurrentSnapPage().isAtTop()) {
					scrollBy(0, deltaY);
				}
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// 弹起手指后，切换屏幕的处理
			if (mTouchState == TOUCH_STATE_SCROLLING) {
			    final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) velocityTracker.getYVelocity();
                if (Math.abs(velocityY) > SNAP_VELOCITY) {
					if( velocityY > 0 && allPages.get(mCurrentScreen).isAtTop()
							&& mCurrentScreen > 0){
                        snapToScreen(mCurrentScreen - 1);
                    }else if(velocityY < 0 && getCurrentSnapPage().isAtBottom()
							&& mCurrentScreen < allPages.size() - 1){
                        snapToScreen(mCurrentScreen + 1);
                    }else{
                        snapToScreen(mCurrentScreen);
                    }
                } else {
                    snapToDestination();
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
			}else{
			}
			mTouchState = TOUCH_STATE_REST;
			break;
		
		default:
			break;
		}
		return true;
	}
	
	private void clearOnTouchEvents(){
		mTouchState = TOUCH_STATE_REST;
		 if (mVelocityTracker != null) {
             mVelocityTracker.recycle();
             mVelocityTracker = null;
         }
	}

    private void snapToDestination() {
		// 计算应该去哪个屏
		final int flipHeight = getHeight() / 8;
        
        int whichScreen = -1;
        final int topEdge = getCurrentView().getTop();

        if(topEdge < getScrollY() && (getScrollY()-topEdge) >= flipHeight
				&& mCurrentScreen < allPages.size() - 1){
            //向下滑动    
            whichScreen = mCurrentScreen + 1;
        }else if(topEdge > getScrollY() && (topEdge - getScrollY()) >= flipHeight
				&& mCurrentScreen > 0){
            //向上滑动
            whichScreen = mCurrentScreen - 1;
        }else{
            whichScreen = mCurrentScreen;
        }
        Log.e("zxc", "snapToDestination mCurrentScreen = " + mCurrentScreen);
    	Log.e("zxc", "snapToDestination whichScreen = " + whichScreen);
        snapToScreen(whichScreen);
	}
	
	private void snapToScreen(int pageIndex) {
        if (!mScroller.isFinished())
            return;

		boolean changingScreens = mCurrentScreen != pageIndex;
		View focusedChild = getFocusedChild();
		if (focusedChild != null && changingScreens) {
			focusedChild.clearFocus();
		}

        final int direction = pageIndex - mCurrentScreen;

		//在这里判断是否已到目标位置~
        int newY = 0;
		switch (direction) {
		case 1:  //需要滑动到第二页
			Log.e(TAG, "the direction is 1");
			newY = getCurrentView().getBottom(); // 最终停留的位置
			break;
		case -1:  //需要滑动到第一页
			Log.e(TAG, "the direction is -1");
			Log.e(TAG, "getCurrentView().getTop() is "
					+ getCurrentView().getTop() + " getHeight() is "
					+ getHeight());
			newY = getCurrentView().getTop() - getHeight(); // 最终停留的位置
			break;
		case 0:  //滑动距离不够， 因此不造成换页，回到滑动之前的位置
			Log.e(TAG, "the direction is 0");
			newY = getCurrentView().getTop(); //第一页的top是0， 第二页的top应该是第一页的高度
			break;
		default:
			break;
		}
        final int cy = getScrollY(); // 启动的位置
        Log.e(TAG, "the newY is " + newY + " cy is " + cy);
        final int delta = newY - cy; // 滑动的距离，正值是往左滑<—，负值是往右滑—>
        mScroller.startScroll(0, cy, 0, delta, Math.abs(delta));

		if (pageIndex >= 0 && pageIndex < allPages.size()) {
			mCurrentScreen = pageIndex;
		}

        invalidate();
    }
	
}

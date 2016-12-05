
package com.arkitvora.categorizedgalleryview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;


public class StepPagerStrip extends View {
    private static final int[] ATTRS = new int[]{
            android.R.attr.gravity
    };
    private int mPageCount;
    private int mCurrentPage;

    private int mGravity = Gravity.LEFT | Gravity.TOP;
    private float mTabWidth;
    private float mTabHeight;
    private float mTabSpacing;

    private Paint mPrevTabPaint;
    private Paint mSelectedTabPaint;
    private Paint mSelectedLastTabPaint;
    private Paint mNextTabPaint;

    private RectF mTempRectF = new RectF();

    private OnPageSelectedListener mOnPageSelectedListener;

    public StepPagerStrip(Context context) {
        this(context, null, 0);
    }

    public StepPagerStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepPagerStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        final TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        mGravity = a.getInteger(0, mGravity);
        a.recycle();

        final Resources res = getResources();
        mTabWidth = res.getDimensionPixelSize(R.dimen.step_pager_tab_width);
        mTabHeight = res.getDimensionPixelSize(R.dimen.step_pager_tab_height);
        mTabSpacing = res.getDimensionPixelSize(R.dimen.step_pager_tab_spacing);

        mPrevTabPaint = new Paint();
        mPrevTabPaint.setStyle(Paint.Style.FILL);
        mPrevTabPaint.setColor(res.getColor(R.color.step_pager_previous_tab_color));

        mSelectedTabPaint = new Paint();
        mSelectedTabPaint.setStyle(Paint.Style.FILL);
        mSelectedTabPaint.setColor(res.getColor(R.color.step_pager_selected_tab_color));

        mSelectedLastTabPaint = new Paint();
        mSelectedLastTabPaint.setStyle(Paint.Style.FILL);
        mSelectedLastTabPaint.setColor(res.getColor(R.color.step_pager_selected_last_tab_color));

        mNextTabPaint = new Paint();
        mNextTabPaint.setStyle(Paint.Style.FILL);
        mNextTabPaint.setColor(res.getColor(R.color.step_pager_next_tab_color));
    }

    public void setDotColors(int prev, int current, int last, int next) {
        mPrevTabPaint.setColor(getResources().getColor(prev));
        mSelectedTabPaint.setColor(getResources().getColor(current));
        mNextTabPaint.setColor(getResources().getColor(next));
        mSelectedLastTabPaint.setColor(getResources().getColor(last));
    }

    public void setOnPageSelectedListener(OnPageSelectedListener onPageSelectedListener) {
        mOnPageSelectedListener = onPageSelectedListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPageCount == 0) {
            return;
        }

        float totalWidth = mPageCount * (mTabWidth + mTabSpacing) - mTabSpacing;
        float totalLeft;
        boolean fillHorizontal = false;

        switch (mGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.CENTER_HORIZONTAL:
                totalLeft = (getWidth() - totalWidth) / 2;
                break;
            case Gravity.RIGHT:
                totalLeft = getWidth() - getPaddingRight() - totalWidth;
                break;
            case Gravity.FILL_HORIZONTAL:
                totalLeft = getPaddingLeft();
                fillHorizontal = true;
                break;
            default:
                totalLeft = getPaddingLeft();
        }

        switch (mGravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.CENTER_VERTICAL:
                mTempRectF.top = (int) (getHeight() - mTabHeight) / 2;
                break;
            case Gravity.BOTTOM:
                mTempRectF.top = getHeight() - getPaddingBottom() - mTabHeight;
                break;
            default:
                mTempRectF.top = getPaddingTop();
        }

        mTempRectF.bottom = mTempRectF.top + mTabHeight;

        float tabWidth = mTabWidth;
        if (fillHorizontal) {
            tabWidth = (getWidth() - getPaddingRight() - getPaddingLeft()
                    - (mPageCount - 1) * mTabSpacing) / mPageCount;
        }

        for (int i = 0; i < mPageCount; i++) {
            mTempRectF.left = totalLeft + (i * (tabWidth + mTabSpacing));
            mTempRectF.right = mTempRectF.left + tabWidth;
            canvas.drawRoundRect(mTempRectF, dpToPx(20), dpToPx(20), i < mCurrentPage
                    ? mPrevTabPaint
                    : (i > mCurrentPage
                    ? mNextTabPaint
                    : (i == mPageCount - 1
                    ? mSelectedLastTabPaint
                    : mSelectedTabPaint)));

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                View.resolveSize(
                        (int) (mPageCount * (mTabWidth + mTabSpacing) - mTabSpacing)
                                + getPaddingLeft() + getPaddingRight(),
                        widthMeasureSpec),
                View.resolveSize(
                        (int) mTabHeight
                                + getPaddingTop() + getPaddingBottom(),
                        heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        scrollCurrentPageIntoView();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mOnPageSelectedListener != null) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    int position = hitTest(event.getX());
                    if (position >= 0) {
                        mOnPageSelectedListener.onPageStripSelected(position);
                    }
                    return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private int hitTest(float x) {
        if (mPageCount == 0) {
            return -1;
        }

        float totalWidth = mPageCount * (mTabWidth + mTabSpacing) - mTabSpacing;
        float totalLeft;
        boolean fillHorizontal = false;

        switch (mGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.CENTER_HORIZONTAL:
                totalLeft = (getWidth() - totalWidth) / 2;
                break;
            case Gravity.RIGHT:
                totalLeft = getWidth() - getPaddingRight() - totalWidth;
                break;
            case Gravity.FILL_HORIZONTAL:
                totalLeft = getPaddingLeft();
                fillHorizontal = true;
                break;
            default:
                totalLeft = getPaddingLeft();
        }

        float tabWidth = mTabWidth;
        if (fillHorizontal) {
            tabWidth = (getWidth() - getPaddingRight() - getPaddingLeft()
                    - (mPageCount - 1) * mTabSpacing) / mPageCount;
        }

        float totalRight = totalLeft + (mPageCount * (tabWidth + mTabSpacing));
        if (x >= totalLeft && x <= totalRight && totalRight > totalLeft) {
            return (int) (((x - totalLeft) / (totalRight - totalLeft)) * mPageCount);
        } else {
            return -1;
        }
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
        invalidate();
        scrollCurrentPageIntoView();

        // TODO: Set content description appropriately
    }

    private void scrollCurrentPageIntoView() {
        // TODO: only works with left gravity for now

    }

    public void setPageCount(int count) {
        mPageCount = count;
        invalidate();

        // TODO: Set content description appropriately
    }

    public interface OnPageSelectedListener {
        void onPageStripSelected(int position);
    }

    public int dpToPx(float dp) {
        DisplayMetrics displayMetrics = MyApplication.getAppContext().getResources().getDisplayMetrics();
        int px = (int) (dp * (displayMetrics.densityDpi / 160f));
        return px;
    }
}

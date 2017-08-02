package cx.com.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xinchang on 2017/7/27.
 */

public class FuckedProgress extends View {
    private Paint mPaint;
    private String mProgress;
    private int mColor = Color.BLUE;
    private float mCircleRadius;
    private float mDistance;
    private float mTextSize;
    private int progress;
    private Canvas mCanvas;

    public FuckedProgress(Context context) {
        super(context);
    }

    public FuckedProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.FuckedProgress);
        mProgress = mTypedArray.getText(R.styleable.FuckedProgress_progress) + "";
        mColor = mTypedArray.getColor(R.styleable.FuckedProgress_circleColor, 0);
        mCircleRadius = mTypedArray.getFloat(R.styleable.FuckedProgress_circleRadius, 0);
        mTextSize = mTypedArray.getFloat(R.styleable.FuckedProgress_textSize, 40f);
        mDistance = mTypedArray.getFloat(R.styleable.FuckedProgress_distance, 40);
        mTypedArray.recycle();
        init();
    }

    public String getmProgress() {
        return mProgress;
    }

    public int getmColor() {
        return mColor;
    }

    public void setmProgress(String mProgress) {
        this.mProgress = mProgress;
        progress = Integer.parseInt(mProgress);
        if (!(progress < 0 || progress > 100)) {
            postInvalidate();
        }

    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setColor(mColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.STROKE);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mFullCircleNumber = 0;
        int mHalfCircleNumber = 0;
        int height = getHeight();
        float y = height / 2;
        float x = mDistance + mCircleRadius;

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#FF9547"));
        //画5个空心圆
        for (int i = 1; i <= 5; i++) {
            canvas.drawCircle(x, y, mCircleRadius, mPaint);
            x = x + mDistance + mCircleRadius * 2;
        }
        //画文字
        {
            float baseX = x - mCircleRadius;
            float baseY = (int) ((getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2));
            canvas.drawText(getmProgress(), baseX, baseY, mPaint);
            float baseX_ = x - mCircleRadius + mPaint.measureText(getmProgress());
            float baseY_ = (int) ((getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2));
            canvas.drawText("%", baseX_, baseY_, mPaint);
        }

        x = mDistance + mCircleRadius;

        if (!TextUtils.isEmpty(mProgress)) {
            mFullCircleNumber = Integer.parseInt(mProgress) / 20;
            mHalfCircleNumber = Integer.parseInt(mProgress) % 20;
        }
        //画实心圆
        {
            if (mFullCircleNumber != 0) {
                mPaint.setColor(Color.parseColor("#FF9547"));
                mPaint.setStyle(Paint.Style.FILL);
                for (int i = 1; i <= mFullCircleNumber; i++) {
                    canvas.drawCircle(x, y, mCircleRadius, mPaint);
                    x = x + mDistance + mCircleRadius * 2;
                }
            }
        }
        //画一部分区域
        if (mHalfCircleNumber != 0) {
            mPaint.setStyle(Paint.Style.FILL);
            if (mHalfCircleNumber < 10) {
                float a = mCircleRadius - ((float) mHalfCircleNumber / 10) * mCircleRadius;
                float v = (float) (Math.sqrt(Math.pow(mCircleRadius, 2) - Math.pow(a, 2)));
                float d = a / mCircleRadius;
                double angel = (Math.acos(d)) / Math.PI * 180;
                canvas.drawArc(x - mCircleRadius, y - mCircleRadius, x + mCircleRadius, y + mCircleRadius, 180 - (float) angel, (float) (2 * angel), true, mPaint);
                mPaint.setColor(Color.WHITE);
                canvas.drawRect(x - a, y - v, x, y + v, mPaint);
            } else if (mHalfCircleNumber == 10) {
                canvas.drawArc(x - mCircleRadius, y - mCircleRadius, x + mCircleRadius, y + mCircleRadius, 90, 180, true, mPaint);
            } else if (mHalfCircleNumber > 10) {
                float a = ((float) mHalfCircleNumber - 10) / 10 * mCircleRadius;
                float v = (float) (Math.sqrt(Math.pow(mCircleRadius, 2) - Math.pow(a, 2)));
                double angle = (Math.acos(a / mCircleRadius)) / Math.PI * 180;
                canvas.drawArc(x - mCircleRadius, y - mCircleRadius, x + mCircleRadius, y + mCircleRadius, (float) angle, (float) ((180 - angle) * 2), true, mPaint);
                canvas.drawRect(x, y - v, x + a, y + v, mPaint);
            }
        }

    }

}

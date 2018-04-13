package me.machao.lightframeview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;


/**
 * Created by charliema on 04/04/2018.
 */
public class LightFrameView extends ViewGroup {

    private boolean isEffectsEnabled = true;

    private int width;
    private int height;

    private Paint paintSweep;
    private Paint paintLinear;

    private static final int DEFAULT_STROKE_WIDTH = 2;

    private float strokeWidth;

    private static final int DEFAULT_SWEEP_GRADIENT_START_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_SWEEP_GRADIENT_END_COLOR = 0x00FFFFFF;
    private static final int DEFAULT_LINEAR_GRADIENT_START_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_LINEAR_GRADIENT_END_COLOR = 0x00FFFFFF;

    private int sweepGradientStartColor;
    private int sweepGradientEndColor;
    private int linearGradientStartColor;
    private int linearGradientEndColor;

    private static final int DEFAULT_MAX_ALPHA_RATE = 0x20;
    private static final int DEFAULT_MAX_ROUND_RECT_RADIUS_RATE = 4;

    private int maxAlphaRate;
    private float maxRoundRectRadiusRate;

    private RoundRect[] sweepRects;
    private RoundRect[] linearRects;

    private static final int DEFAULT_SWEEP_RECTS_COUNT = 1;
    private static final int DEFAULT_LINEAR_RECTS_COUNT = 1;

    private int sweepRectsCount;
    private int linearRectsCount;

    private ValueAnimator valueAnimator;
    private float degreeSweep = 0;
    private float degreeLinear = 0;
    private Matrix matrix;


    public LightFrameView(Context context) {
        super(context);
        init(context, null);
    }

    public LightFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LightFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public LightFrameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("LightFrameView", "onSizeChanged w:" + w + "  h:" + h);

        width = getWidth();
        height = getHeight();
        reset();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        valueAnimator.cancel();
        Log.e("LightFrameView", "onDetachedFromWindow  :");
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // only deal with first child
        View child = getChildAt(0);
        measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
        setMeasuredDimension(resolveSize(child.getMeasuredWidth(), widthMeasureSpec), resolveSize(child.getMeasuredHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // only deal with first child
        View child = getChildAt(0);
        if (child == null) {
            return;
        }
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        Log.e("LightFrameView", "onLayout Measured w:" + getMeasuredWidth() + "  h:" + getMeasuredHeight());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if(!isEffectsEnabled){
            return;
        }

        Log.e("LightFrameView", "dispatchDraw w:" + width + "  h:" + height);

        canvas.save();
        for (RoundRect roundRect : sweepRects) {
            matrix.setRotate(degreeSweep, width / 2, height / 2);
            paintSweep.getShader().setLocalMatrix(matrix);
            roundRect.draw(canvas, paintSweep);
        }
        for (RoundRect roundRect : linearRects) {
            matrix.setRotate(degreeLinear, width / 2, height / 2);
            paintLinear.getShader().setLocalMatrix(matrix);
            roundRect.draw(canvas, paintLinear);
        }
        canvas.restore();
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LightFrameView);
        sweepRectsCount = typedArray.getInt(R.styleable.LightFrameView_sweepRectsCount, DEFAULT_SWEEP_RECTS_COUNT);
        linearRectsCount = typedArray.getInt(R.styleable.LightFrameView_sweepRectsCount, DEFAULT_LINEAR_RECTS_COUNT);

        strokeWidth = typedArray.getDimension(R.styleable.LightFrameView_strokeWidth, DEFAULT_STROKE_WIDTH);
        sweepGradientStartColor = typedArray.getColor(R.styleable.LightFrameView_sweepGradientStartColor, DEFAULT_SWEEP_GRADIENT_START_COLOR);
        sweepGradientEndColor = typedArray.getColor(R.styleable.LightFrameView_sweepGradientEndColor, DEFAULT_SWEEP_GRADIENT_END_COLOR);
        linearGradientStartColor = typedArray.getColor(R.styleable.LightFrameView_linearGradientStartColor, DEFAULT_LINEAR_GRADIENT_START_COLOR);
        linearGradientEndColor = typedArray.getColor(R.styleable.LightFrameView_linearGradientStartColor, DEFAULT_LINEAR_GRADIENT_END_COLOR);

        maxAlphaRate = typedArray.getInt(R.styleable.LightFrameView_maxAlphaRate, DEFAULT_MAX_ALPHA_RATE);
        maxRoundRectRadiusRate = typedArray.getDimension(R.styleable.LightFrameView_maxRoundRectRadiusRate, DEFAULT_MAX_ROUND_RECT_RADIUS_RATE);

        typedArray.recycle();

        setWillNotDraw(false);

        paintSweep = new Paint();
        paintSweep.setAntiAlias(true);
        paintSweep.setStyle(Paint.Style.STROKE);
        paintSweep.setStrokeCap(Paint.Cap.ROUND);

        paintLinear = new Paint();
        paintLinear.setAntiAlias(true);
        paintLinear.setStyle(Paint.Style.STROKE);
        paintLinear.setStrokeCap(Paint.Cap.ROUND);

        matrix = new Matrix();

        initAnim();
        valueAnimator.start();
    }

    private void initAnim() {
        ValueAnimator.setFrameDelay(16);
        valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int degree = (int) animation.getAnimatedValue();
//                Log.e("LightFrameView", "onAnimationUpdate :" + degree);
                degreeSweep = degree * 3;
                degreeLinear = degree;
                postInvalidate();

            }
        });
    }

    private void reset() {

        paintSweep.setStrokeWidth(strokeWidth);
        paintLinear.setStrokeWidth(strokeWidth);

        sweepRects = new RoundRect[sweepRectsCount];
        linearRects = new RoundRect[linearRectsCount];

        for (int i = 0; i < sweepRects.length; i = i + 1) {
            sweepRects[i] = new RoundRect(0, 0, width, height, maxAlphaRate, maxRoundRectRadiusRate, strokeWidth);
        }
        for (int i = 0; i < linearRects.length; i = i + 1) {
            linearRects[i] = new RoundRect(0, 0, width, height, maxAlphaRate, maxRoundRectRadiusRate, strokeWidth);
        }
        paintSweep.setShader(new SweepGradient(
                width / 2,
                height / 2,
                sweepGradientStartColor,
                sweepGradientEndColor));
        paintLinear.setShader(new LinearGradient(
                0,
                0,
                width / 2,
                height / 2,
                linearGradientStartColor,
                linearGradientEndColor,
                Shader.TileMode.CLAMP
        ));

        postInvalidate();
    }

    @Keep
    public void setEffectsEnable(boolean enable) {
        this.isEffectsEnabled = enable;
        reset();
    }

    @Keep
    public boolean isEffectsEnabled() {
        return this.isEffectsEnabled;
    }

    @Keep
    public void setSweepRectsCount(int sweepRectsCount) {
        this.sweepRectsCount = sweepRectsCount;
        reset();
    }

    @Keep
    public void setLinearRectsCount(int linearRectsCount) {
        this.linearRectsCount = linearRectsCount;
        reset();
    }

    @Keep
    void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        reset();
    }

}
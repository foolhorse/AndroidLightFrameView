package me.machao.lightframeview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by charliema on 2018/4/04.
 */

class RoundRect {

    private Random random;
    private float left;
    private float top;
    private float right;
    private float bottom;

    private int alpha;
    private float radius;
    private float strokeWidth;

    private RectF rect;

    private int alphaMaxRate;
    private float radiusMaxRate;

    private int alphaMaxRange;
    private float radiusMaxRange;

    private boolean needReset = true;

    // +1 or -1 ,direction to center of rect is positive
    private int leftDirection = 1;
    private int topDirection = 1;
    private int rightDirection = 1;
    private int bottomDirection = 1;

    private float strokeCenter;

    private float sizeMaxRate;
    private float sizeMaxRange;

    RoundRect(int alphaMaxRate, float radiusMaxRate, float strokeWidth) {

        this.alphaMaxRate = alphaMaxRate;
        this.radiusMaxRate = radiusMaxRate;

        this.strokeWidth = strokeWidth;

        rect = new RectF();

        random = new Random(new SecureRandom().nextInt());
    }

    void draw(Canvas canvas, Paint paint) {
        if (needReset) {

            left = strokeCenter;
            top = strokeCenter;
            right = canvas.getWidth() - left - strokeCenter;
            bottom = canvas.getHeight() - top - strokeCenter;

            strokeCenter = strokeWidth / 2;
            sizeMaxRange = strokeWidth;
            sizeMaxRate = strokeWidth / 32;
            alphaMaxRange = 0xff;
            radiusMaxRange = strokeWidth * 2;

            needReset = false;
        }

        if (left > strokeCenter + sizeMaxRange) {
            leftDirection = -1;
        } else if (left < strokeCenter) {
            leftDirection = 1;
        }
        left = (float) (left + Math.random() * sizeMaxRate * leftDirection);

        if (top > strokeCenter + sizeMaxRange) {
            topDirection = -1;
        } else if (top < strokeCenter) {
            topDirection = 1;
        }
        top = (float) (top + Math.random() * sizeMaxRate * topDirection);

        if (right < canvas.getWidth() - strokeCenter - sizeMaxRange) {
            rightDirection = -1;
        } else if (right > canvas.getWidth() - strokeCenter) {
            rightDirection = 1;
        }
        right = (float) (right - Math.random() * sizeMaxRate * rightDirection);

        if (bottom < canvas.getHeight() - strokeCenter - sizeMaxRange) {
            bottomDirection = -1;
        } else if (bottom > canvas.getHeight() - strokeCenter) {
            bottomDirection = 1;
        }
        bottom = bottom - random.nextFloat() % sizeMaxRate * bottomDirection;

        if (alpha > alphaMaxRange || alpha < 0) {
            alpha = alphaMaxRange / 2;
        } else {
            alpha = alpha + (random.nextInt() % alphaMaxRate) * (random.nextBoolean() ? 1 : -1);
        }
        if (radius > radiusMaxRange || radius < 0) {
            radius = radiusMaxRange / 2;
        } else {
            radius = radius + (random.nextInt() % radiusMaxRate) * (random.nextBoolean() ? 1 : -1);
        }
        rect.set(left, top, right, bottom);

        paint.setAlpha(alpha);

        canvas.drawRoundRect(rect, radius, radius, paint);
    }

}
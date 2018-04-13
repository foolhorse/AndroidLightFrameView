package me.machao.lightframeview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by charliema on 2018/4/04.
 */

class RoundRect {

    private float left;
    private float top;
    private float right;
    private float bottom;

    private int alpha;
    private float radius;
    private float strokeWidth;

    private RectF rect ;

    private int alphaMaxRate = 0x40;
    private float radiusMaxRate = 4;


    RoundRect(int left, int top, int right, int bottom, int alphaMaxRate, float radiusMaxRate, float strokeWidth) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        this.alphaMaxRate = alphaMaxRate;
        this.radiusMaxRate = radiusMaxRate;

        this.strokeWidth = strokeWidth;

        this.rect = new RectF();
    }

    void draw(Canvas canvas, Paint paint){
        if (this.left > strokeWidth) {
            this.left = strokeWidth / 2;
        } else {
            this.left = (float) (this.left + Math.random() * strokeWidth);
        }
        if (this.top > strokeWidth) {
            this.top = strokeWidth / 2;
        } else {
            this.top = (float) (this.top + Math.random() * strokeWidth);
        }
        if (this.right > strokeWidth) {
            this.right = canvas.getWidth() - this.left - strokeWidth / 2;
        } else {
            this.right = (float) (this.right - this.left - Math.random() * strokeWidth);
        }
        if (this.bottom > strokeWidth) {
            this.bottom = canvas.getHeight() - this.top - strokeWidth / 2;
        } else {
            this.bottom = this.bottom - this.top - new Random().nextInt() % strokeWidth;
        }
        if (this.alpha > 0xff || this.alpha < 0) {
            this.alpha = 0xff;
        } else {
            this.alpha = this.alpha + (new Random().nextInt() % alphaMaxRate - alphaMaxRate / 2);
        }
        if (this.radius > 32 || this.radius < 0) {
            this.radius = 16;
        } else {
            this.radius = this.radius + (new Random().nextInt() % radiusMaxRate - radiusMaxRate / 2);
        }
        rect.set(this.left, this.top, this.right, this.bottom);

        paint.setAlpha(this.alpha);

        canvas.drawRoundRect(rect, radius, radius, paint);
    }

}
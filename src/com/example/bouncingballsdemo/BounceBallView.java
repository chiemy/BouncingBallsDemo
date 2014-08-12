package com.example.bouncingballsdemo;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class BounceBallView extends View {
	private BallShape newBall;
	public BounceBallView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", Color.RED, Color.BLUE);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		if(newBall != null){
			canvas.translate(newBall.getX(), newBall.getY());
			newBall.getShape().draw(canvas);
		}
		canvas.restore();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN &&
                event.getAction() != MotionEvent.ACTION_MOVE) {
            return false;
        }
		newBall = getBallShape(event.getX(),event.getY());
		float startY = newBall.getY();
        float endY = getHeight() - 50f;
        float h = (float)getHeight();
        float eventY = event.getY();
        int duration = (int)(500 * ((h - eventY)/h));
        ValueAnimator bounceAnim = ObjectAnimator.ofFloat(newBall, "y", startY, endY);
        bounceAnim.setDuration(duration);
        bounceAnim.setInterpolator(new AccelerateInterpolator());
        ValueAnimator squashAnim1 = ObjectAnimator.ofFloat(newBall, "x", newBall.getX(),
                newBall.getX() - 25f);
        squashAnim1.setDuration(duration/4);
        squashAnim1.setRepeatCount(1);
        squashAnim1.setRepeatMode(ValueAnimator.REVERSE);
        squashAnim1.setInterpolator(new DecelerateInterpolator());
        ValueAnimator squashAnim2 = ObjectAnimator.ofFloat(newBall, "width", newBall.getWidth(),
                newBall.getWidth() + 50);
        squashAnim2.setDuration(duration/4);
        squashAnim2.setRepeatCount(1);
        squashAnim2.setRepeatMode(ValueAnimator.REVERSE);
        squashAnim2.setInterpolator(new DecelerateInterpolator());
        ValueAnimator stretchAnim1 = ObjectAnimator.ofFloat(newBall, "y", endY,
                endY + 25f);
        stretchAnim1.setDuration(duration/4);
        stretchAnim1.setRepeatCount(1);
        stretchAnim1.setInterpolator(new DecelerateInterpolator());
        stretchAnim1.setRepeatMode(ValueAnimator.REVERSE);
        ValueAnimator stretchAnim2 = ObjectAnimator.ofFloat(newBall, "height",
                newBall.getHeight(), newBall.getHeight() - 25);
        stretchAnim2.setDuration(duration/4);
        stretchAnim2.setRepeatCount(1);
        stretchAnim2.setInterpolator(new DecelerateInterpolator());
        stretchAnim2.setRepeatMode(ValueAnimator.REVERSE);
        ValueAnimator bounceBackAnim = ObjectAnimator.ofFloat(newBall, "y", endY,
                startY);
        bounceBackAnim.setDuration(duration);
        bounceBackAnim.setInterpolator(new DecelerateInterpolator());
        // Sequence the down/squash&stretch/up animations
        AnimatorSet bouncer = new AnimatorSet();
        bouncer.play(bounceAnim).before(squashAnim1);
        bouncer.play(squashAnim1).with(squashAnim2).with(stretchAnim1).with(stretchAnim2);
        bouncer.play(bounceBackAnim).after(stretchAnim2);
        bouncer.start();
		return true;
	}
	private BallShape getBallShape(float x,float y){
		BallShape shape = new BallShape();
		shape.setX(x);
		shape.setY(y);
		return shape;
	}
}

package com.example.bouncingballsdemo;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class BallShape {
	private final static int DEFUALT_RADIUS = 50;
	private ShapeDrawable shape;
	private float x,y;
	private float width,height;
	private final static int DEFAULT_COLOR = 0x34673334;
	public BallShape(){
		OvalShape oval = new OvalShape();
		oval.resize(DEFUALT_RADIUS, DEFUALT_RADIUS);
		shape = new ShapeDrawable(oval);
		shape.getPaint().setColor(DEFAULT_COLOR);
	}
	public void setX(float x) {
		this.x = x - getWidth()/2;
	}
	public float getX() {
		return x;
	}
	public void setY(float y) {
		this.y = y - getHeight()/2;
	}
	public float getY() {
		return y;
	}
	public void setShape(ShapeDrawable shape) {
		this.shape = shape;
	}
	public ShapeDrawable getShape() {
		return shape;
	}
	
	public void setWidth(float width) {
		if(width < 1){
			return;
		}
		this.width = width;
		shape.getShape().resize(width, height == 0 ? DEFUALT_RADIUS : height);
	}
	public float getWidth() {
		return width == 0 ? DEFUALT_RADIUS : width;
	}
	public void setHeight(float height) {
		if(height < 1){
			return;
		}
		this.height = height;
		shape.getShape().resize(width, height == 0 ? DEFUALT_RADIUS : height);
	}
	public float getHeight() {
		return height == 0 ? DEFUALT_RADIUS : height;
	}
	
}

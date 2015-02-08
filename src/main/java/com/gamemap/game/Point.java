package com.gamemap.game;

import java.util.List;

public class Point {

	private Float x;
	private Float y;
	private Float z;
	
	public Point(Float x, Float y, Float z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Float getX() {
		return x;
	}
	public void setX(Float x) {
		this.x = x;
	}
	public Float getY() {
		return y;
	}
	public void setY(Float y) {
		this.y = y;
	}
	public Float getZ() {
		return z;
	}
	public void setZ(Float z) {
		this.z = z;
	}
	
	public boolean equals(Point p){
		if(Math.round(x) != Math.round(p.getX())){
			return false;
		}
		if(Math.round(y) != Math.round(p.getY())){
			return false;
		}
		return true;
	}
	
	public boolean alreadyInList(List<Point> list){
		for (Point point : list) {
			if(point.equals(this)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Point [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", z=");
		builder.append(z);
		builder.append("]");
		return builder.toString();
	}
	
	
}

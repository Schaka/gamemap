package com.gamemap.game;

public class Location extends Point{

	public Location(Float x, Float y, Float z) {
		super(x, y, z);
	}
	
	public Location(Point p){
		super(p.getX(), p.getY(), p.getZ());
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

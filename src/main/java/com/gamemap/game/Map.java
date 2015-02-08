package com.gamemap.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Map extends Observable{

	private Point currentPoint;
	private List<Point> coordinates;
	private List<Location> locations;
	private Float highestX = 0f;
	private Float highestY = 0f;
	private Float lowestX = 0f;
	private Float lowestY = 0f;
	
	public Map(){
		currentPoint = new Point(0f, 0f, 0f);
		locations = new ArrayList<Location>();
		coordinates = new ArrayList<Point>();
	}

	public List<Point> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Point> coordinates) {
		this.coordinates = coordinates;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(Point currentPoint) {
		this.currentPoint = currentPoint;
	}
	
	public void addPoint(Point point){
		coordinates.add(point);
		
		if(lowestX > point.getX()){
			lowestX = point.getX();
			setChanged();
		}
		
		if(lowestY > point.getY()){
			lowestY = point.getY();
			setChanged();
		}
		
		if(highestX < point.getX()){
			highestX = point.getX();
			setChanged();
		}
		if(highestY < point.getY()){
			highestY = point.getY();
			setChanged();
		}
		
		notifyObservers();
	}

	public Float getHighestX() {
		return highestX;
	}

	public void setHighestX(Float highestX) {
		this.highestX = highestX;
	}

	public Float getHighestY() {
		return highestY;
	}

	public void setHighestY(Float highestY) {
		this.highestY = highestY;
	}

	public Float getLowestX() {
		return lowestX;
	}

	public void setLowestX(Float lowestX) {
		this.lowestX = lowestX;
	}

	public Float getLowestY() {
		return lowestY;
	}

	public void setLowestY(Float lowestY) {
		this.lowestY = lowestY;
	}
	
	public Float getWidth(){
		return (lowestX - highestX)*-1;
	}
	
	public Float getHeight(){
		return (lowestY - highestY)*-1;
	}
	
}

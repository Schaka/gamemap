package com.gamemap.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamemap.game.Location;
import com.gamemap.game.Map;
import com.gamemap.game.Point;

@Component
public class ViewPanel extends JPanel{

	private Graphics2D graphics;
	
	@Autowired
	private Map map;
	
	public ViewPanel(){
		super();
		if(getGraphics() != null){
			graphics = (Graphics2D)getGraphics();			
		}
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(g != null){
			graphics = (Graphics2D)g;
		}
		repaintAllPoints();
	}
		
	public void paintLastPoint(){
		Point p = map.getCoordinates().get(map.getCoordinates().size()-1);
		paintPoint(p);
		//log.info(getX(p.getX()));
		//log.info(getY(p.getY()));
        repaint();
	}
	
	private void paintPoint(Point p){
		graphics.setColor(Color.blue);
		Integer x = getDrawableX(p.getX());
		Integer y = getDrawableY(p.getY());
		if (p instanceof Location){
			Location loc = (Location)p;
			graphics.setColor(Color.black);
			graphics.drawString(((Location) p).getName(), x, y);
			graphics.setColor(Color.red);
		}
		graphics.drawOval(x, y, 3, 3);
	}
	
	private Integer getDrawableX(Float x){
		Dimension size = getSize();
		Insets insets = getInsets();
	    Integer w = size.width - insets.left - insets.right;
	    Float mapWidth = map.getWidth();
	    Float offset = map.getLowestX() == 0 ? 0 : ((map.getLowestX()*-1)/mapWidth)*w;
	    Float percent = x/mapWidth;
	    return Math.round((w*percent)+offset);
	}
	
	private Integer getDrawableY(Float y){
		Dimension size = getSize();
		Insets insets = getInsets();
	    Integer h = size.height - insets.top - insets.bottom;
	    Float mapHeight = map.getHeight();
	    Float offset =  map.getLowestY() == 0 ? 0 : ((map.getLowestY()*-1)/mapHeight)*h;
	    Float percent = y/mapHeight;
	    return Math.round((h*percent)+offset);
	     
	}
	
	private void paintAllPoints(){
		List<Point> coordinates = new ArrayList<Point>(map.getCoordinates());
		for (Point p  : coordinates) {
			paintPoint(p);
		}
	}
	
	public void repaintAllPoints(){
		paintAllPoints();
		repaint();
	}
}

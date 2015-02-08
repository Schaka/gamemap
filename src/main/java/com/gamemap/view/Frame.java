package com.gamemap.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamemap.game.Map;

@Component
public class Frame extends JFrame implements Observer{

	private Logger log = Logger.getLogger(Frame.class);
	@Autowired
	private Map map;
	private ViewPanel panel;
	
	public Frame(){
		super("Frame");
		this.setBounds(50, 50, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	public void test(){
		log.info(map.getLocations().get(0).getName());
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
	public void paintLastPoint(){
		panel.paintLastPoint();        
	}
	
	public void repaintAllPoints(){
		panel.repaintAllPoints();
	}

	public void update(Observable map, Object arg1) {
		repaintAllPoints();
	}
	
	@Autowired
	public void setViewPanel(ViewPanel panel){
		this.panel = panel;
		setContentPane(panel);
		panel.setVisible(true);
	}
}

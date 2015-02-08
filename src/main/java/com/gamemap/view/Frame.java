package com.gamemap.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
		this.setBounds(50, 50, 640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	@Value("${view.width},${view.height}")
	private void setSize(String[] param){
		this.setSize(Integer.parseInt(param[0]), Integer.parseInt(param[1]));
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
		panel.setVisible(true);
		Dimension size = getSize();
		Insets insets = getInsets();
	    Integer h = size.height - insets.top - insets.bottom;
	    Integer w = size.width - insets.left - insets.right;
		panel.setSize(new Dimension(w, h));
		setContentPane(panel);
	}
}

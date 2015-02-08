package com.gamemap;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.gamemap.config.ApplicationConfig;
import com.gamemap.game.Game;

public class GameMap 
{
	private static Logger log = Logger.getLogger(GameMap.class);
    public static void main( String[] args )
    {
    	
    	AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    	log.info("Starting GameMap - Application context loaded");
    	Game game = beanFactory.getBean(Game.class);
    	game.test();
    }
}

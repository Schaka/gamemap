package com.gamemap.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gamemap.memory.Module;
import com.gamemap.memory.PsapiTools;
import com.gamemap.util.MemoryTool;
import com.gamemap.view.Frame;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT.HANDLE;

@Component
@Scope("singleton")
public class Game {

	private Logger log = Logger.getLogger(Game.class);
	@Autowired
	private Map currentMap;
	@Autowired
	private Frame frame;
	private List<Map> maps;
	private Integer pId = MemoryTool.getProcessIdByWindow("The Long Dark");
	private Point currentPoint;
	private Integer baseAddress;
	
	@Value("${game.memory.x}")
	private Integer xAddress;
	
	@Value("${game.memory.y}")
	private Integer yAddress;
	
	@Value("${game.memory.z}")
	private Integer zAddress;
	
	
	public Game(){
		maps = new ArrayList<Map>();
		maps.add(currentMap);
	}
	
	public void test(){
		Location loc = new Location(0f, 0f, 0f);
		loc.setName("Test");
		ArrayList<Location> locs = new ArrayList<Location>();
		locs.add(loc);
		currentMap.setLocations(locs);
		frame.test();
		log.info("Process ID:" + pId);
	}
	
	@Scheduled(fixedDelay=1000, initialDelay = 1200)
	public void updatePosition(){
		
		findBaseAddress();	
		Float x = MemoryTool.readMemory(MemoryTool.openProcess(MemoryTool.READ_RIGHT, pId), baseAddress+xAddress, 4).getFloat(0);
		Float y = MemoryTool.readMemory(MemoryTool.openProcess(MemoryTool.READ_RIGHT, pId), baseAddress+yAddress, 4).getFloat(0);
		Float z = MemoryTool.readMemory(MemoryTool.openProcess(MemoryTool.READ_RIGHT, pId), baseAddress+zAddress, 4).getFloat(0);
		
		
		
		
		if(z > -100){
			Point point = new Point(x, y, z);
			if(!isDuplicatePoint(point)){
				currentMap.addPoint(point);
				setCurrentPoint(point);
				frame.paintLastPoint();
			}
			
		}else{
			Location loc = new Location(currentPoint == null ? new Point(0f, 0f, 0f) : currentPoint);
			loc.setName("Building"); //TODO: read name from current "point" in memory
			currentMap.addPoint(loc);
			frame.paintLastPoint();
			
		}
		
		currentMap.addObserver(frame);
		//log.info(currentPoint);
	}

	private void findBaseAddress() {
		if(baseAddress == null){
			try {
				HANDLE game = MemoryTool.openProcess(MemoryTool.PROCESS_ALL_ACCESS, pId);
				List<Module> modules = PsapiTools.getInstance().EnumProcessModulesEx(game, 0x01);
				for (Module module : modules) {
					if(module.getBaseName().equals("AkSoundEngine.dll")){		
						if(module.getLpBaseOfDll() != null){
							log.info(module.getBaseName() + " found at 0x" + Long.toHexString(Pointer.nativeValue(module.getLpBaseOfDll().getPointer())));
							baseAddress = ((Long)Pointer.nativeValue(module.getLpBaseOfDll().getPointer())).intValue();
						}
					}
				}
			} catch (Exception e) {
				log.error("Error finding base address");
			}			
		}
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(Point currentPoint) {
		currentMap.setCurrentPoint(currentPoint);
		this.currentPoint = currentPoint;
	}
	
	private boolean isDuplicatePoint(Point p){
		if(currentPoint == null){
			return false;
		}
		if(p.equals(currentPoint) || p.alreadyInList(currentMap.getCoordinates())){
			return true;
		}
		return false;
	}
}

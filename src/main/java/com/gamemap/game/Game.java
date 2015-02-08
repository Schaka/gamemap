package com.gamemap.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gamemap.memory.Module;
import com.gamemap.memory.MyKernel32;
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
	private Location currentLocation;
	
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
		
//		Float x = MemoryTool.readMemory(MemoryTool.openProcess(MemoryTool.READ_RIGHT, pId), 0x5AAF8F68, 4).getFloat(0);
//		Float y = MemoryTool.readMemory(MemoryTool.openProcess(MemoryTool.READ_RIGHT, pId), 0x5AAF8F70, 4).getFloat(0);
//		Float z = MemoryTool.readMemory(MemoryTool.openProcess(MemoryTool.READ_RIGHT, pId), 0x5AAF8F6C, 4).getFloat(0);
		
		try {
			HANDLE game = MemoryTool.openProcess(MemoryTool.PROCESS_ALL_ACCESS, pId);
			List<Module> modules = PsapiTools.getInstance().EnumProcessModules(game);
			//Pointer p = MemoryTool.openProcess(MemoryTool.PROCESS_ALL_ACCESS, pId).getPointer();
			
			for (Module module : modules) {
				//log.info(module.getFileName());
				log.info(module.getBaseName());
				if(module.getFileName().contains("tld.exe")){		
					if(module.getEntryPoint() != null){
						log.info(module.getBaseName() + " 0x" + Long.toHexString(Pointer.nativeValue(module.getEntryPoint().getPointer())));
					}
					if(module.getLpBaseOfDll() != null){
						log.info(module.getBaseName() + " 0x" + Long.toHexString(Pointer.nativeValue(module.getLpBaseOfDll().getPointer())));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Float x = MemoryTool.readMemory(MemoryTool.openProcess(MemoryTool.READ_RIGHT, pId), 0x5A7E8F68, 4).getFloat(0);
		Float y = MemoryTool.readMemory(MemoryTool.openProcess(MemoryTool.READ_RIGHT, pId), 0x5A7E8F70, 4).getFloat(0);
		Float z = MemoryTool.readMemory(MemoryTool.openProcess(MemoryTool.READ_RIGHT, pId), 0x5A7E8F6C, 4).getFloat(0);
		
		
		
		
//		if(z > -100){
//			Point point = new Point(x, y, z);
//			currentMap.addPoint(point);
//			setCurrentPoint(point);
//			frame.paintLastPoint();
//		}else{
//			Location loc = new Location(currentPoint == null ? new Point(0f, 0f, 0f) : currentPoint);
//			loc.setName("Building"); //TODO: read name from current "point" in memory
//			currentMap.addPoint(loc);
//			frame.paintLastPoint();
//		}
		
		currentMap.addObserver(frame);
		//log.info(currentPoint);
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(Point currentPoint) {
		currentMap.setCurrentPoint(currentPoint);
		this.currentPoint = currentPoint;
	}
	
}

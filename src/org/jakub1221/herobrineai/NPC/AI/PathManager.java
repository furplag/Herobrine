package org.jakub1221.herobrineai.NPC.AI;

public class PathManager {

	Path pathNow = null;
	
	public void setPath(Path path){
		pathNow=path;
	}
	
	public void update(){
		if (pathNow!=null){
		pathNow.update();
		}
	}
	
	public Path getPath(){
		return pathNow;
	}
	
}

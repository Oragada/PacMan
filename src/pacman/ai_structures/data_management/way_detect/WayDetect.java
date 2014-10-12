package pacman.ai_structures.data_management.way_detect;

import java.util.ArrayList;
import java.util.HashMap;

import pacman.game.Constants.GHOST;
import pacman.game.Game;
import pacman.game.Constants.MOVE;

public class WayDetect {
	
	
	public static final int MAX = 200;
	public int enemy;
	public int prey;
	public int pellet;
	public int spellet;
	
	public boolean chosen;
	
	
	private WayDetect(){
		
	}
	
	public static WayDetect[] getWayDetect(Game game, MOVE move){
		int pacPos = game.getPacmanCurrentNodeIndex();
		MOVE[] pm = game.getPossibleMoves(pacPos);
		
		//HashMap<MOVE, WayDetect> wds = new HashMap<MOVE, WayDetect>();
		
		WayDetect[] wds = new WayDetect[pm.length];
		
		for(int i = 0; i<pm.length;i++){
			WayDetect wd = new WayDetect();

			int nPos = game.getNeighbour(pacPos, pm[i]);
			
			//Chosen
			if(pm[i] == move){
				wd.chosen = true;
			}
			else
			{
				wd.chosen = false;
			}
			
			//Pellets
			if(game.getActivePillsIndices().length > 0){
				wd.pellet = Cap(findNearest(nPos, pm[i], game.getActivePillsIndices(), game));
			}
			else{
				wd.pellet = MAX;
			}
			
			
			//SuperPellets
			if(game.getActivePowerPillsIndices().length > 0){
				wd.spellet = Cap(findNearest(nPos, pm[i], game.getActivePowerPillsIndices(), game));
			}
			else{
				wd.spellet = MAX;
			}
			
			//GHOSTS
			ArrayList<Integer> gPrey = new ArrayList<Integer>();
			ArrayList<Integer> gEnemy = new ArrayList<Integer>();
			
			GHOST[] gs = GHOST.values();
			for(GHOST g : gs){
				if (game.getGhostEdibleTime(g) > 0){
					gPrey.add(game.getGhostCurrentNodeIndex(g));
				}
				else if(game.getGhostLairTime(g) == 0)
				{
					gEnemy.add(game.getGhostCurrentNodeIndex(g));
				}
			}
			
			//Prey & Enemies
				
			if(gPrey.size() > 0){
				int[] preyIs = new int[gPrey.size()];
				for(int j = 0; j<preyIs.length;j++)
				{
					preyIs[j] = gPrey.get(j);
				}
				
				wd.prey = Cap(findNearest(nPos, pm[i], preyIs, game));
			}
			else{
				wd.prey = MAX;
			}
			
			
			//Enemies
			if(gEnemy.size() > 0){
				int[] enemyIs = new int[gEnemy.size()];
				for(int j = 0; j<enemyIs.length;j++)
				{
					enemyIs[j] = gEnemy.get(j);
				}
				
				wd.enemy = Cap(findNearest(nPos, pm[i], enemyIs, game));
			}
			else{
				wd.enemy = MAX;
			}
			//wds.put(pm[i], wd);
			wds[i] = wd;
		}
		
		return wds;
	}
	
	public static WayDetect recrtWD(String wString){
		WayDetect newWD = new WayDetect();
		String[] kv = wString.split(":");
		String[] dataPoints = kv[0].split(";");
		newWD.chosen = GetChosen(kv[1]);
		newWD.pellet = Integer.parseInt(dataPoints[0]);
		newWD.spellet =Integer.parseInt(dataPoints[1]);
		newWD.enemy = Integer.parseInt(dataPoints[2]);
		newWD.prey =Integer.parseInt(dataPoints[3]);
		
		return newWD;
	}
	
	private static int Cap(int val) {
		if(val > MAX ) return MAX;
		return val;
	}

	public static int findNearest(int nowPos, MOVE lMove, int[] indices, Game g){
		int shortest = Integer.MAX_VALUE;
		for(int i : indices){
			int dist = g.getShortestPathDistance(nowPos, i, lMove);
			if(shortest > dist) shortest = dist;
		}
		return shortest;
	}

	public String getSaveString() {
		StringBuilder strB = new StringBuilder();
		
		strB.append(pellet+";");
		strB.append(spellet+";");
		strB.append(enemy+";");
		strB.append(prey+";");
		strB.append(":"+GetChosen());
		
		return strB.toString();
	}
	
	private String GetChosen() {
		if(chosen) return "T";
		return "F";
	}
	
	private static boolean GetChosen(String sMove){
		if(sMove.equals("T")) return true;
		return false;
	}

	public String toString(){
		StringBuilder strB = new StringBuilder();
		
		strB.append("P: " + pellet);
		strB.append(" | ");
		strB.append("S: " + spellet);
		strB.append(" | ");
		strB.append("E: " + enemy);
		strB.append(" | ");
		strB.append("P: " + prey);
		strB.append(" # ");
		strB.append("ch: "+ GetChosen());
		strB.append("\n");
		
		return strB.toString();
	}
	
	/*public int GetDist(DISTTYPE dt, MOVE dir){
		switch (dt){
			case PELLET:
				if(!pellets.containsKey(dir)){
					return MAX;
				}
				return pellets.get(dir);
		case SPELLET:
				if(!sPellets.containsKey(dir)){
					return MAX;
				}
				return sPellets.get(dir);
			case ENEMY:
				if(!enemies.containsKey(dir)){
					return MAX;
				}
				return enemies.get(dir);
			case PREY:
				if(!prey.containsKey(dir)){
					return MAX;
				}
				return prey.get(dir);
			default:
				return MAX;
				
		}
			
	}*/
}

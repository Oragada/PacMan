package pacman.ai_structures.data_management.dir_detect;

import java.util.ArrayList;
import java.util.HashMap;

import pacman.game.Constants.GHOST;
import pacman.game.Game;
import pacman.game.Constants.MOVE;
import pacman.ai_structures.data_management.*;

public class DirectionDetect {
	
	
	public static final int MAX = 1000;
	public HashMap<MOVE, Integer> enemies;
	public HashMap<MOVE, Integer> prey;
	public HashMap<MOVE, Integer> pellets;
	public HashMap<MOVE, Integer> sPellets;
	
	public MOVE chosenMove;
	
	
	private DirectionDetect(){
		enemies = new HashMap<MOVE, Integer>();
		prey = new HashMap<MOVE, Integer>();
		pellets = new HashMap<MOVE, Integer>();
		sPellets = new HashMap<MOVE, Integer>();
	}
	
	public static DirectionDetect getDirDetect(Game game, MOVE move){
		DirectionDetect d = new DirectionDetect();
		if(move == MOVE.NEUTRAL)
		{
			move = game.getPacmanLastMoveMade();
		}
		d.chosenMove = move;
		
		
		int pacPos = game.getPacmanCurrentNodeIndex();
		MOVE[] pm = game.getPossibleMoves(pacPos);
		MOVE[] m = new MOVE[]{MOVE.UP,MOVE.DOWN,MOVE.LEFT, MOVE.RIGHT};
		//boolean junc = game.isJunction(pacPos);
		for(int i = 0; i<m.length;i++){
			if(!Contains(pm,m[i])){
				//TODO update
				d.pellets.put(m[i], MAX);
				d.sPellets.put(m[i], MAX);
				d.enemies.put(m[i], MAX);
				d.prey.put(m[i], MAX);
				continue;
			}

			int nPos = game.getNeighbour(pacPos, m[i]);
			
			//Pellets
			if(game.getActivePillsIndices().length > 0){
				d.pellets.put(m[i], Cap(findNearest(nPos, m[i], game.getActivePillsIndices(), game)));
			}
			else{
				d.pellets.put(m[i], Cap(MAX));
			}
			
			
			//SuperPellets
			if(game.getActivePowerPillsIndices().length > 0){
				d.sPellets.put(m[i], Cap(findNearest(nPos, m[i], game.getActivePowerPillsIndices(), game)));
			}
			else{
				d.sPellets.put(m[i], Cap(MAX));
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
				
				d.prey.put(m[i], Cap(findNearest(nPos, m[i], preyIs, game)));
			}
			else{
				d.prey.put(m[i], Cap(MAX));
			}
			
			
			//Enemies
			if(gEnemy.size() > 0){
				int[] enemyIs = new int[gEnemy.size()];
				for(int j = 0; j<enemyIs.length;j++){
					enemyIs[j] = gEnemy.get(j);
				}
				
				d.enemies.put(m[i], Cap(findNearest(nPos, m[i], enemyIs, game)));
			}
			else{
				d.enemies.put(m[i], Cap(MAX));
			}
			
		}

		assert d.enemies.size()+d.prey.size()+d.pellets.size()+d.sPellets.size() == 16;
		
		return d;
	}
	
	private static boolean Contains(MOVE[] l, MOVE e) {
		for(int i = 0; i<l.length; i++){
			if(l[i] == e) return true;
		}
		return false;
	}

	public static DirectionDetect recrtDD(String dString){
		DirectionDetect newDD = new DirectionDetect();
		String[] kv = dString.split(":");
		String[] dataPoints = kv[0].split(";");
		newDD.chosenMove = GetMove(kv[1]);
		newDD.pellets.put(MOVE.UP, Integer.parseInt(dataPoints[0]));
		newDD.pellets.put(MOVE.DOWN, Integer.parseInt(dataPoints[1]));
		newDD.pellets.put(MOVE.LEFT, Integer.parseInt(dataPoints[2]));
		newDD.pellets.put(MOVE.RIGHT, Integer.parseInt(dataPoints[3]));
		newDD.sPellets.put(MOVE.UP, Integer.parseInt(dataPoints[4]));
		newDD.sPellets.put(MOVE.DOWN, Integer.parseInt(dataPoints[5]));
		newDD.sPellets.put(MOVE.LEFT, Integer.parseInt(dataPoints[6]));
		newDD.sPellets.put(MOVE.RIGHT, Integer.parseInt(dataPoints[7]));
		newDD.enemies.put(MOVE.UP, Integer.parseInt(dataPoints[8]));
		newDD.enemies.put(MOVE.DOWN, Integer.parseInt(dataPoints[9]));
		newDD.enemies.put(MOVE.LEFT, Integer.parseInt(dataPoints[10]));
		newDD.enemies.put(MOVE.RIGHT, Integer.parseInt(dataPoints[11]));
		newDD.prey.put(MOVE.UP, Integer.parseInt(dataPoints[12]));
		newDD.prey.put(MOVE.DOWN, Integer.parseInt(dataPoints[13]));
		newDD.prey.put(MOVE.LEFT, Integer.parseInt(dataPoints[14]));
		newDD.prey.put(MOVE.RIGHT, Integer.parseInt(dataPoints[15]));
		
		return newDD;
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
		
		strB.append(GetDist(DISTTYPE.PELLET,MOVE.UP)+";");
		strB.append(GetDist(DISTTYPE.PELLET,MOVE.DOWN)+";");
		strB.append(GetDist(DISTTYPE.PELLET,MOVE.LEFT)+";");
		strB.append(GetDist(DISTTYPE.PELLET,MOVE.RIGHT)+";");
		strB.append(GetDist(DISTTYPE.SPELLET,MOVE.UP)+";");
		strB.append(GetDist(DISTTYPE.SPELLET,MOVE.DOWN)+";");
		strB.append(GetDist(DISTTYPE.SPELLET,MOVE.LEFT)+";");
		strB.append(GetDist(DISTTYPE.SPELLET,MOVE.RIGHT)+";");
		strB.append(GetDist(DISTTYPE.ENEMY,MOVE.UP)+";");
		strB.append(GetDist(DISTTYPE.ENEMY,MOVE.DOWN)+";");
		strB.append(GetDist(DISTTYPE.ENEMY,MOVE.LEFT)+";");
		strB.append(GetDist(DISTTYPE.ENEMY,MOVE.RIGHT)+";");
		strB.append(GetDist(DISTTYPE.PREY,MOVE.UP)+";");
		strB.append(GetDist(DISTTYPE.PREY,MOVE.DOWN)+";");
		strB.append(GetDist(DISTTYPE.PREY,MOVE.LEFT)+";");
		strB.append(GetDist(DISTTYPE.PREY,MOVE.RIGHT));
		strB.append(":"+GetMove(chosenMove));
		
		return strB.toString();
	}
	
	private String GetMove(MOVE cMove) {
		switch (cMove){
		case UP:
			return "U";
		case DOWN:
			return "D";
		case LEFT:
			return "L";
		case RIGHT:
			return "R";
		default:
			return "N";
		}
	}
	
	private static MOVE GetMove(String sMove){
		if(sMove.equals("U")) return MOVE.UP;
		if(sMove.equals("D")) return MOVE.DOWN;
		if(sMove.equals("L")) return MOVE.LEFT;
		if(sMove.equals("R")) return MOVE.RIGHT;
		return MOVE.NEUTRAL;
	}

	public String toString(){
		StringBuilder strB = new StringBuilder();

		strB.append("Pellets:  ");
		strB.append("U:"+GetDist(DISTTYPE.PELLET,MOVE.UP)+";  ");
		strB.append("D:"+GetDist(DISTTYPE.PELLET,MOVE.DOWN)+";  ");
		strB.append("L:"+GetDist(DISTTYPE.PELLET,MOVE.LEFT)+";  ");
		strB.append("R:"+GetDist(DISTTYPE.PELLET,MOVE.RIGHT)+"");
		strB.append("\n");
		strB.append("SPellets: ");
		strB.append("U:"+GetDist(DISTTYPE.SPELLET,MOVE.UP)+";  ");
		strB.append("D:"+GetDist(DISTTYPE.SPELLET,MOVE.DOWN)+";  ");
		strB.append("L:"+GetDist(DISTTYPE.SPELLET,MOVE.LEFT)+";  ");
		strB.append("R:"+GetDist(DISTTYPE.SPELLET,MOVE.RIGHT)+"");
		strB.append("\n");
		strB.append("Enemies:  ");
		strB.append("U:"+GetDist(DISTTYPE.ENEMY,MOVE.UP)+";  ");
		strB.append("D:"+GetDist(DISTTYPE.ENEMY,MOVE.DOWN)+";  ");
		strB.append("L:"+GetDist(DISTTYPE.ENEMY,MOVE.LEFT)+";  ");
		strB.append("R:"+GetDist(DISTTYPE.ENEMY,MOVE.RIGHT)+"");
		strB.append("\n");
		strB.append("Prey:     ");
		strB.append("U:"+GetDist(DISTTYPE.PREY,MOVE.UP)+";  ");
		strB.append("D:"+GetDist(DISTTYPE.PREY,MOVE.DOWN)+";  ");
		strB.append("L:"+GetDist(DISTTYPE.PREY,MOVE.LEFT)+";  ");
		strB.append("R:"+GetDist(DISTTYPE.PREY,MOVE.RIGHT)+"");
		strB.append("\n");
		strB.append("MOVE: " + chosenMove);
		strB.append("\n");
		
		return strB.toString();
	}
	
	public int GetDist(DISTTYPE dt, MOVE dir){
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
			
	}
}

package pacman.ai_structures.data_management.way_detect;

import pacman.game.util.IO;

public class WayDetectSaverLoader {

	private static String FileName = "tDataWD.txt";
	
	public static void SavePacManData(WayDetect[] data)
	{
		for(int i = 0; i<data.length; i++){
			IO.saveFile(FileName, data[i].getSaveString(), true);
		}
	}
	
	public static WayDetect[] LoadPacManData()
	{
		String data = IO.loadFile(FileName);
		String[] dataLine = data.split("\n");
		WayDetect[] WDdataTuples = new WayDetect[dataLine.length];
		
		for(int i = 0; i < dataLine.length; i++)
		{
			WDdataTuples[i] = WayDetect.recrtWD(dataLine[i]);
		}
		
		return WDdataTuples;
	}
}

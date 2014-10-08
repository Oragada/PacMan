package pacman.ai_structures.data_management;

import pacman.ai_structures.dataRecording.DataTuple;
import pacman.game.util.IO;

public class DirDetectSaverLoader {

	private static String FileName = "tDataDD.txt";
	
	public static void SavePacManData(DirectionDetect data)
	{
		IO.saveFile(FileName, data.getSaveString(), true);
	}
	
	public static DirectionDetect[] LoadPacManData()
	{
		String data = IO.loadFile(FileName);
		String[] dataLine = data.split("\n");
		DirectionDetect[] DDdataTuples = new DirectionDetect[dataLine.length];
		
		for(int i = 0; i < dataLine.length; i++)
		{
			DDdataTuples[i] = DirectionDetect.recrtDD(dataLine[i]);
		}
		
		return DDdataTuples;
	}
}

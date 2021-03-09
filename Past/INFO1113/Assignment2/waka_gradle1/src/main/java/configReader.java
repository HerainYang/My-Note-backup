import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;

import static processing.core.PApplet.loadJSONObject;

public class configReader {
    JSONObject configFile;
    public configReader(){
        configFile = loadJSONObject(new File("config.json"));
    }

    public String getMapFile(){
        return configFile.getString("map");
    }

    public int getLives(){
        return configFile.getInt("lives");
    }

    public int getSpeed(){
        return configFile.getInt("speed");
    }

    public int[] getGhostMode(){
        JSONArray jsonArray = configFile.getJSONArray("modeLengths");
        System.out.println(jsonArray.size());
        int[] ghostMode = new int[jsonArray.size()];
        for(int i = 0; i < jsonArray.size(); i ++){
            ghostMode[i] = jsonArray.getInt(i);
        }
        return ghostMode;
    }
}

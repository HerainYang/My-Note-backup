package ghost;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is used to read the configuration file and return the attribute
 */
public class ConfigReader {
    private JSONObject configFile;
    private JSONParser parser;

    /**
     * read the file as .json
     * @throws IOException file doesn't exist
     * @throws ParseException file isn't json file
     */
    public ConfigReader() throws IOException, ParseException {
        FileReader reader = new FileReader("config.json");
        parser = new JSONParser();
        Object object = parser.parse(reader);
        configFile = (JSONObject) object;
    }

    /**
     * @return file name of the map
     */
    public String getMapFile(){
        return (String) configFile.get("map");
    }

    /**
     * @return the number of live of the player
     */
    public long getLives(){
        return (long) configFile.get("lives");
    }

    /**
     * @return the speed of the ghosts and player
     */
    public long getSpeed(){
        return (Long) configFile.get("speed");
    }

    /**
     * @return the length of each mode (scatter mode and chase mode)
     */
    public long[] getGhostMode(){
        JSONArray jsonArray = (JSONArray) configFile.get("modeLengths");
        long[] ghostMode = new long[jsonArray.size()];
        for(int i = 0; i < jsonArray.size(); i ++){
            ghostMode[i] = (long) jsonArray.get(i);
        }
        return ghostMode;
    }

    /**
     * return the length of frightened mode
     * @return
     */
    public long getFrightenedLength(){
        return (long) configFile.get("frightenedLength");
    }

    /**
     * return the length of soda mode
     * @return
     */
    public long getSodaLength(){
        return (long) configFile.get("sodaLength");
    }
}


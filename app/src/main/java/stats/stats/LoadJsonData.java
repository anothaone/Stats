package stats.stats;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Mike on 2/15/2017.
 */
public class LoadJsonData {


    public String loadJSONFromAsset(Context c) {
        String json;
        //   c = getApplicationContext();
        try {
            //  Log.d("test", "t");
            InputStream is = c.getAssets().open("building_json.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}

package ext;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import play.templates.BaseTemplate;
import play.templates.JavaExtensions;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class OxirasJavaExtensions extends JavaExtensions {

    private final static Gson gson = new GsonBuilder().create();

    public static BaseTemplate.RawData asJSON(Map map) {
        return new BaseTemplate.RawData(gson.toJson(map));
    }

}

package ksike.plugin;
import java.util.HashMap;
import java.util.Map;
/**
 * @author Antonio Membrides Espinosa
 * @made 19/04/2019
 * @version 1.0
 */
public class KsHelper {
    //... Singleton ...........................................
    private static KsHelper _obj = null;
    private final static Object _lock = new Object();

    public static KsHelper self() {
        synchronized (_lock) {
            if (_obj == null) {
                _obj = new KsHelper();
            }
        }
        return _obj;
    }
    //.........................................................
    public KsLoader loader;
    public Map<String, Object> lib;
    
    public KsHelper(){
        this.lib = new HashMap<String, Object>();
        this.lib.put("loader", KsLoader.self());
        this.loader = KsLoader.self();
    }
}

package ksike.plugin;

import ksike.mvc.KsModule;

/**
 * @author Antonio Membrides Espinosa
 * @made 19/04/2019
 * @version 1.0
 */
public interface KsLoaderSubscribtor {

    public void onEvent(String event, KsModule subject, Object handler, Object[] params);

    public boolean checkPlugin(String name, KsModule subject, KsLoader handler);
}

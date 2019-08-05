package ksike.mvc;

import ksike.plugin.KsHelper;
import ksike.plugin.KsMetadata;

/**
 * @author Antonio Membrides Espinosa
 * @made 19/04/2019
 * @version 1.0
 */
public abstract class KsModule {

    public KsHelper helper;
    
    public void onEvent(String event, Object handler, Object[] params){
        if(event.equals("load")){
            this.onLoad();
        }
        
        if(event.equals("init")){
            this.onInit();
        }
    }

    public abstract void onInit();

    public abstract void onLoad();

    public abstract KsMetadata getMetadata();

    public void setDependency(Object subjet) {
        if(subjet instanceof KsHelper){
            this.helper = (KsHelper) subjet;
        }
    }
    
    public Object render() {
        return null;
    }
}

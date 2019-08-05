package ksike.plugin;

import ksike.mvc.KsModule;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antonio Membrides Espinosa
 * @made 19/04/2019
 * @version 1.0
 */
public class KsLoader {

    //... Singleton ...........................................
    private static KsLoader _obj = null;
    private final static Object _lock = new Object();

    public static KsLoader self() {
        synchronized (_lock) {
            if (_obj == null) {
                _obj = new KsLoader();
            }
        }
        return _obj;
    }
    //.........................................................

    protected Map<String, KsModule> plugin;
    protected List<KsLoaderSubscribtor> subscriptor;
    protected String path;
    protected int min;
    protected String mainclass;
    protected String extension;

    private void initialice() {
        this.plugin = new HashMap<String, KsModule>();
        this.subscriptor = new ArrayList<KsLoaderSubscribtor>();
        this.path = "";
        this.extension = "jar";
        this.mainclass = "Main";
        this.min = 0;
    }

    public KsLoader() {
        this.initialice();
    }

    public KsLoader(String path) {
        this.initialice();
        this.path = path;
    }

    public Class<?> getClass(String main) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
        Class<?> myc = Class.forName(main);
        return myc;
    }

    public Class<?> getClass(String dir, String main) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
        Class<?> myc = Class.forName(main, false, new URLClassLoader(new URL[]{new File(dir).toURI().toURL()}));
        return myc;
    }

    public KsModule getObject(Class<?> myc) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //Interface obj = (KsModule) myc.asSubclass(KsModule.class).getConstructor(String.class).newInstance("Mamama");
        KsModule obj = (KsModule) myc.newInstance();
        if (obj != null) {
            obj.setDependency(KsHelper.self());
        }
        return obj;
    }

    public KsModule getPlugin(String name) {
        try {
            return this.getObject(this.getClass(name));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | MalformedURLException ex) {
            Logger.getLogger(KsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public KsModule getPlugin(String path, String name, String main, String type) throws IOException {
        try {
            String file = KsFodm.self().getPath(path + "./" + name + "." + type);
            String modn = name + "." + main;
            return this.getPlugin(file, modn);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | MalformedURLException ex) {
            Logger.getLogger(KsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public KsModule getPlugin(String dir, String main) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, MalformedURLException {
        return this.getObject(this.getClass(dir, main));
    }

    public Map<String, KsModule> getPlugis() {
        return this.plugin;
    }

    public void setPath(String vpath) {
        this.path = vpath;
    }

    public String getMainclass() {
        return mainclass;
    }

    public void setMainclass(String mainclass) {
        this.mainclass = mainclass;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean exist(String name) {
        return this.plugin.containsKey(name);
    }

    public KsModule load(String main, String name) throws KsPluginException {
        KsModule mod = this.getPlugin(main + "." + this.getMainclass());
        if (mod != null && this.checkPlugin(name, mod)) {
            this.plugin.put(name, mod);
            try {
                this.send("load", mod);
            } catch (Exception e) {
                this.notifyEvent("exception", mod, this, new Object[]{e});
            }
        }
        return mod;
    }

    public KsModule load(String path, String name, String main, String type) throws KsPluginException {
        KsModule mod = null;
        try {
            mod = this.getPlugin(path, name, main, type);
            if (mod != null && this.checkPlugin(name, mod)) {
                this.plugin.put(name, mod);
                try {
                    this.send("load", mod);
                } catch (Exception e) {
                    this.notifyEvent("exception", mod, this, new Object[]{e});
                }
            }
        } catch (IOException ex) {
            this.notifyEvent("exception", null, this, new Object[]{ex, name, path, main, type});
        }
        return mod;
    }

    public void load(String path) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, MalformedURLException, IOException, KsPluginException {
        File[] list = new File(path).listFiles();
        if (list != null) {
            if (list.length > 0) {
                for (File rsc : list) {
                    this.load(path, KsFodm.self().getFileName(rsc), this.mainclass, this.extension);
                }
            }
        }
    }

    public void load() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, IOException, KsPluginException {
        this.load(this.path);
    }

    public void send(String action, String path, String name, String main, String type) throws KsPluginException {
        this.send(action, this.get(path, name, main, type));
    }

    public void send(String action) throws KsPluginException {
        if (this.plugin.isEmpty() || this.plugin.size() < this.min) {
            try {
                this.load(this.path);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | IOException ex) {
                Logger.getLogger(KsLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Map.Entry<String, KsModule> i : this.plugin.entrySet()) {
            this.send(action, i.getValue());
        }
    }

    public void send(String action, String mod, String name) throws KsPluginException {
        this.send(action, this.load(mod, name));
    }

    public void send(String action, KsModule mod) throws KsPluginException {
        try {
            this.notifyEvent("pre" + action, mod);
            mod.onEvent(action, null, null);
            this.notifyEvent("pos" + action, mod);
        } catch (Exception e) {
            this.notifyEvent("exception", mod, this, new Object[]{e});
            //throw new KsPluginException(2, mod.getMetadata().getName(), e.getMessage());
        }
    }

    public KsModule get(String path, String name, String main, String type) throws KsPluginException {
        if (!this.plugin.containsKey(name)) {
            try {
                this.load(path, name, main, type);
            } catch (KsPluginException ex) {
                Logger.getLogger(KsLoader.class.getName()).log(Level.SEVERE, null, ex);
                throw new KsPluginException(1, name, ex.getMessage());
            }
        }
        return this.plugin.get(name);
    }

    public KsModule get(String name) throws KsPluginException {
        if (!this.plugin.containsKey(name)) {
            try {
                this.load(this.path, name, this.mainclass, this.extension);
            } catch (KsPluginException ex) {
                Logger.getLogger(KsLoader.class.getName()).log(Level.SEVERE, null, ex);
                throw new KsPluginException(1, name, ex.getMessage());
            }
        }
        return this.plugin.get(name);
    }

    public KsModule del(String name) {
        KsModule remove = this.plugin.remove(name);
        return remove;
    }

    public void init() throws KsPluginException {
        this.send("init");
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void subscribe(KsLoaderSubscribtor subject) {
        this.subscriptor.add(subject);
    }

    public void notifyEvent(String event, KsModule subject) {
        this.notifyEvent(event, subject, this, null);
    }

    public void notifyEvent(String event, KsModule subject, Object handler, Object[] params) {
        for (KsLoaderSubscribtor obj : this.subscriptor) {
            obj.onEvent(event, subject, handler, params);
        }
    }

    public boolean checkPlugin(String name, KsModule subject) {
        boolean result = true;
        for (KsLoaderSubscribtor obj : this.subscriptor) {
            result = result && obj.checkPlugin(name, subject, this);
        }
        return result;
    }
}

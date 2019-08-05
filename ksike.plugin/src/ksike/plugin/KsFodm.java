package ksike.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Antonio Membrides Espinosa
 * @made 19/04/2019
 * @version 1.0
 */
public class KsFodm {

    //... Singleton ...........................................
    private static KsFodm _obj = null;

    public static KsFodm self() {
        if (_obj == null) {
            _obj = new KsFodm();
        }
        return _obj;
    }

    //.........................................................
    public String getUserPath() {
        return System.getProperty("user.dir");
    }

    public String getPath() throws IOException {
        return this.getPath("");
    }

    public String getPath(URI path) throws IOException {
        File file = new File(path);
        return file.getCanonicalPath();
    }

    public String getPath(String path) throws IOException {
        File file = new File(path);
        //System.out.println("Canonical Path => " + file.getCanonicalPath());
        return file.getCanonicalPath();
    }

    public void getListPath(String path) {
        File[] list = new File(path).listFiles();
        for (File rsc : list) {
            System.out.println(rsc.getName());
            System.out.println(this.getFileExtension(rsc));
            System.out.println(this.getFileName(rsc));
        }
    }

    public String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    public String getFilePath(String file) {
        return this.getFilePath(new File(file));
    }

   
    public String getFilePath(Class file){
        URL uri = file.getProtectionDomain().getCodeSource().getLocation();
        try {
            return this.getFilePath(uri.toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(KsFodm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String getFilePath(URI file) {
        return this.getFilePath(new File(file));
    }

    public String getFilePath(File file) {
        String fileName = "";
        try {
            if (file != null && file.exists()) {
                fileName = file.getParentFile().getCanonicalPath();
            }
        } catch (IOException e) {
            fileName = "";
        }
        return fileName;
    }

    public String getFileName(File file) {
        String fileName = "";
        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "");
            }
        } catch (Exception e) {
            fileName = "";
        }
        return fileName;
    }
}

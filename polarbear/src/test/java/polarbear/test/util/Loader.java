package polarbear.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PropertyResourceBundle;

public class Loader {

    private PropertyResourceBundle bundle;

    private static Loader loader = new Loader();

    private Loader() {
        InputStream in = null;
        InputStreamReader r = null;
        try {
            in = Loader.class.getResourceAsStream("/urlconfig.properties");
            r = new InputStreamReader(in, "UTF-8");
            bundle = new PropertyResourceBundle(r);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final static Loader getInstance() {
        return loader;
    }

    public String getProps(String key) {
        return bundle.getString(key);
    }

    public static void main(String[] args) {
         System.out.println(System.getProperty("java.class.path"));
    }

}

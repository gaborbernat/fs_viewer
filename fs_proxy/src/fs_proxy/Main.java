package fs_proxy;

import java.io.File;

/**
 * The Main entry point for the proxy class.
 */
public class Main {
    /**
     * Return the current users home directory.
     * If doesn't exists yet, create it.
     *
     * @return
     */
    public static File getSettingsDirectory() {
        String userHome = System.getProperty("user.home");
        if (userHome == null) {
            throw new IllegalStateException("user.home==null");
        }
        File home = new File(userHome);
        File settingsDirectory = new File(home, ".fs_proxy");
        if (!settingsDirectory.exists()) {
            if (!settingsDirectory.mkdir()) {
                throw new IllegalStateException(settingsDirectory.toString());
            }
        }
        return settingsDirectory;
    }


    public static void main(String s[]) {
        new FS_Proxy().run(1000);
    }
}
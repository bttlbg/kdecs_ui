package org.ichilabs;

import java.io.*;

public class ReadConfig {
    public static String[] GetConfig() {

        String[] configs = {"wal", "1", "true"};

        String home = System.getenv("HOME");
        File configFile = new File(home + "/.config/kdecs/conf.ini");

        try {
            BufferedReader br = new BufferedReader(new FileReader(configFile));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("backend=")) {
                    configs[0] = line.substring(8);
                }
                if (line.contains("accent=")) {
                    configs[1] = line.substring(7);
                }
                if (line.contains("lightMode=")) {
                    configs[2] = line.substring(10);
                }
            }
            br.close();

            return configs;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

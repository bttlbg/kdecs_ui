package org.ichilabs;

import java.io.*;

public class WriteConfig {
    public WriteConfig(String engine, String accent, String darkMode) {
        String home = System.getenv("HOME");
        File configFile = new File(home + "/.config/kdecs/conf.ini");
        try {
            BufferedReader br = new BufferedReader(new FileReader(configFile));
            String line;
            StringBuilder oldText = new StringBuilder();
            while ((line = br.readLine()) != null) {
                oldText.append(line).append("\n");
            }
            br.close();

            String newText = oldText.toString().replaceAll("backend=.*", "backend=" + engine);
            newText = newText.replaceAll("accent=.*", "accent=" + accent);
            newText = newText.replaceAll("lightMode=.*", "lightMode=" + darkMode);

            FileWriter fileWriter = new FileWriter(home + "/.config/kdecs/conf.ini");
            fileWriter.write(newText);
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

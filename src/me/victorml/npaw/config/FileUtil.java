package me.victorml.npaw.config;

import java.io.*;

public class FileUtil {

    private File file;

    /**
     * Creates a new File Util from a File
     * @param file File to handle
     */
    public FileUtil(File file) {
        this.file = file;
    }

    /**
     * Creates a new FileUtil from a filename
     * @param filename filename
     */
    public FileUtil(String filename) {
        file = new File(filename + ".json");
    }


    private void createFile() {
        if (!file.exists()) {
            System.out.println("[INFO] " + this.file.getName() + ".json was not found. Creating a new one!");
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                System.out.println("[INFO] Error while creating" + this.file.getName() + ".json");
                e.printStackTrace();
            }
        }
    }

    /**
     * Save Json content to the file
     * @param content json info
     */
    public void save(String content) {
        final FileWriter fw;
        try {
            createFile();
            fw = new FileWriter(this.file);
            fw.write(content);
            fw.flush();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the data from json if the file does not exists it will get the default config
     * @return String config
     */
    public String load() {
        BufferedReader reader;
        String data = "";
        if (file.exists()) {
            System.out.println("[INFO] " + this.file.getName() + ".json was found. Loading information");
            try {
                reader = new BufferedReader((new FileReader(file)));
                data = readFile(reader);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("[INFO] " + this.file.getName() + ".json was not found. Using default config");
            InputStream configStream = getClass().getResourceAsStream("/config.json");
            reader = new BufferedReader(new InputStreamReader(configStream));
            data = readFile(reader);
        }
        return data;
    }

    /**
     * Read the file that contains the json information
     * @param reader Reader from we want to get the data
     * @return String
     */
    public String readFile(BufferedReader reader) {
        StringBuilder text = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

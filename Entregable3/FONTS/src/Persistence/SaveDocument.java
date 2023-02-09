package Persistence;

import java.io.File;
import java.util.ArrayList;

public abstract class SaveDocument {
    public ArrayList<String> ReadDocument(String path) {
        return null;
    }

    public void WriteDocument(String path, ArrayList<String> data) {

    }

    public void deleteFile(String path) {
        File f = new File(path);
        try {
            f.delete();
        } catch (Exception ignored) {
        }
    }
}

package Persistence;

import java.io.*;
import java.util.ArrayList;

public class SaveSerialized extends SaveDocument {
    public ArrayList<String> ReadDocument(String path) {
        ArrayList<String> result = new ArrayList<>();
        try {
            File f = new File(path);

            if (f.exists()) {
                FileInputStream fileIn = new FileInputStream(f);
                ObjectInputStream in = new ObjectInputStream(fileIn);

                while (fileIn.available() > 0) {
                    result = (ArrayList<String>) in.readObject();
                }

                in.close();
                fileIn.close();
            }
        } catch (ClassNotFoundException e) {
            //throw new RuntimeException(e);
            return null;
        } catch (IOException ignored) {
            //throw new RuntimeException(e);
        }
        return result;
    }

    public void WriteDocument(String path, ArrayList<String> data) {
        try {
            File d = new File("docs");
            if (!d.exists()) d.mkdir();
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(data);

            out.close();
            fileOut.close();

        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}

package Persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveData {

    public void saveData(String dir, String name, ArrayList<String> data) {
        try {
            File d = new File(dir);
            if (!d.exists()) d.mkdir();

            File f = new File(dir + '/' + name);
            f.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(dir + '/' + name);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(data);

            out.close();
            fileOut.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void deleteDataFile(String path) {
        File f = new File(path);
        f.delete();
    }

    public ArrayList<String> loadData(String path) {
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
            System.out.println("The file '" + path + "' data seems to be corrupt, it will be erased");
            deleteDataFile(path);
        } catch (IOException ignored) {
            //throw new RuntimeException(e);
        }
        return result;
    }
}

package Persistence;

import java.io.*;
import java.util.ArrayList;

public class SavePlainText extends SaveDocument {
    public ArrayList<String> ReadDocument(String path) {
        ArrayList<String> result = new ArrayList<>();
        try {
            File f = new File(path);
            if (f.exists()) {
                InputStream is = new FileInputStream(f);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                while (reader.ready()) {
                    result.add(reader.readLine());
                }
            } else return null;
        } catch (IOException ignored) {
            return null;
        }
        return result;
    }

    public void WriteDocument(String path, ArrayList<String> data) {
        try {
            File f = new File(path);
            OutputStream os = new FileOutputStream(f);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            for (String s : data) {
                writer.append(s).append(String.valueOf('\n'));
            }

            writer.close();

        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

package br.com.lynx.misc;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by rogerio on 18/02/2016.
 */
public class FileTextUtils {

    public static String[] readDBScriptFile(AssetManager assetManager, String filename, String separator) {
        StringBuilder texto = new StringBuilder();
        InputStream stream = null;

        try {
            stream = assetManager.open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            while (reader.ready())
                texto.append(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return texto.toString().split(separator);
    }
}

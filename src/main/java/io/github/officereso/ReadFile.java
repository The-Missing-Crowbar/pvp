package io.github.officereso;



import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public class ReadFile {
    InputStream file;

    public ReadFile(InputStream file){
        this.file = file;
    }

    public void updateFile(InputStream file){
        this.file = file;
    }

    public String getContents(){
        return getString(file);
    }

    public static String getContents(InputStream file){
        return getString(file);
    }

    @NotNull
    private static String getString(InputStream file) {
        int i;
        StringBuilder content = new StringBuilder();
        try {
            while((i = file.read())!=-1) {
                content.append((char) i);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public String toString() {
        return getContents();
    }
}

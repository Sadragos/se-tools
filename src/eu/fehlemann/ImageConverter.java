package eu.fehlemann;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class ImageConverter {
    public static final File TEXTCONV_PATH = new File("texconv.exe");

    public static void convertFolder(File folder) throws IOException, InterruptedException {
        for (File f : ArrayUtils.ArrayToList(folder.listFiles((file, s) -> s.toLowerCase().endsWith("dds") || s.toLowerCase().endsWith("png")))) {
            convertFile(f);
        }
        if (TEXTCONV_PATH.exists()) {
            TEXTCONV_PATH.delete();
        }
    }

    public static void convertFile(File file) throws IOException, InterruptedException {
        if(!file.isFile()) {
            System.out.println("Skipping " + file.getName() + " - not a file");
            return;
        }
        if (!TEXTCONV_PATH.exists()) {
            RessourceCopy ressourceCopy = new RessourceCopy();
            try {
                ressourceCopy.copyFromJar("assets/texconv.exe", TEXTCONV_PATH.toPath());
            } catch (URISyntaxException e) {
                System.out.println("Could not extract textconv.exe");
            }
        }
        ProcessBuilder processBuilder = null;
        boolean toDDS = false;
        if (file.getName().toLowerCase().endsWith("dds")) {
            processBuilder = new ProcessBuilder(TEXTCONV_PATH.getAbsolutePath(), "-ft", "png", file.getAbsolutePath(), "-y");
        } else if (file.getName().toLowerCase().endsWith("png")) {
            toDDS = true;
            processBuilder = new ProcessBuilder(TEXTCONV_PATH.getAbsolutePath(), "-f", "BC7_UNORM", file.getAbsolutePath(), "-y");
        }

        if (processBuilder != null) {
            System.out.print("Converting " + file.getName() + "... ");
            processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);
            processBuilder.directory(file.getParentFile());
            Process process = processBuilder.start();
            int waitFlag = process.waitFor();
            if (waitFlag == 0) {
                System.out.println("ok");
                String blankName = file.getName().substring(0, file.getName().indexOf("."));
                if (toDDS) {
                    File badName = new File(file.getParent(), blankName + ".DDS");
                    File goodName = new File(file.getParent(), blankName + ".dds");
                    if (badName.exists() && badName.renameTo(goodName)) {
                        System.out.println("Corrected Filename " + badName.getName() + " -> " + goodName.getName());
                    }
                } else {
                    File badName = new File(file.getParent(), blankName + ".PNG");
                    File goodName = new File(file.getParent(), blankName + ".png");
                    if (badName.exists() && badName.renameTo(goodName)) {
                        System.out.println("Corrected Filename " + badName.getName() + " -> " + goodName.getName());
                    }
                }
            } else {
                System.out.println("error");
            }
        }
    }
}

import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.*;
import java.util.List;

/**
 * Created by 060111737 on 18/06/2019.
 */
public class CompressingUtils {
    public CompressingUtils() {
    }

    public static void mkdirs(File outdir, String path) {
        File d = new File(outdir, path);
        if (!d.exists()) {
            d.mkdirs();
        }
    }

    public static void mkdirs(String path) {
        File d = new File(path);
        if (!d.exists()) {
            d.mkdirs();
        }
    }

    public CompressingUtils(List<File> listFiles, String output) {
        this.listFiles = listFiles;
        this.output = output;
    }
    private List<File> listFiles;
    private String output;

    public boolean createZip(List<File> listOfFiles, String outputName) throws FileNotFoundException {
        boolean isFinished = false;
        ZipOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = new ZipOutputStream(new FileOutputStream(new File(outputName)));
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword("ZxcvbnM12345");
            for (File file : listOfFiles) {
                outputStream.putNextEntry(file, parameters);
                if (file.isDirectory()) {
                    outputStream.closeEntry();
                    continue;
                }
                inputStream = new FileInputStream(file);
                byte[] readBuff = new byte[4096];
                int readLen = -1;
                while ((readLen = inputStream.read(readBuff)) != -1) {
                    outputStream.write(readBuff, 0, readLen);
                }
                outputStream.closeEntry();
                inputStream.close();
            }
            outputStream.finish();
            isFinished = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isFinished;
    }

    public boolean createZipWithoutPassword(List<File> listOfFiles, String outputName) throws FileNotFoundException {
        boolean isFinished = false;
        ZipOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = new ZipOutputStream(new FileOutputStream(new File(outputName)));
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(false);
            for (File file : listOfFiles) {
                outputStream.putNextEntry(file, parameters);
                if (file.isDirectory()) {
                    outputStream.closeEntry();
                    continue;
                }
                inputStream = new FileInputStream(file);
                byte[] readBuff = new byte[4096];
                int readLen = -1;
                while ((readLen = inputStream.read(readBuff)) != -1) {
                    outputStream.write(readBuff, 0, readLen);
                }
                outputStream.closeEntry();
                inputStream.close();
            }
            outputStream.finish();
            isFinished = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isFinished;
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.go.pajak.enkripsi.model.DetailType;
import id.go.pajak.enkripsi.model.HeaderType;
import id.go.pajak.enkripsi.model.MainType;
import id.go.pajak.ibk.model.ResponType;
import id.go.pajak.service.IbkReader;
import id.go.pajak.service.ManifestWriter;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.crypto.CryptoException;

/**
 * Created by usrdjp on 8/17/2021.
 */
public class IbkEnkripsi {
    private static String lokasiFile;
    private static String lokasiHasil;
    private static String lokasiHasilBulk;
    public static void main(String[] args) {
        IbkEnkripsi enc = new IbkEnkripsi();
        //Memeriksa folder input dan output dari proses ekripsi
        setLokasiFile();
        System.out.println("Proses enkripsi dimulai");

        /*Inisialisasi*/
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMddHHmmss");
        String parentpath = lokasiFile;
        String namaFolderTargetEnkripsi = parentpath;
        String lokasiFolderTargetEnkripsi = parentpath;

        //            public key dari DJP
        String pemContent = "-----BEGIN CERTIFICATE-----\n" +
                "MIIFTTCCBDWgAwIBAgIIPdt6uLid8FwwDQYJKoZIhvcNAQELBQAwgY0xOTA3BgNV\n" +
                "BAMMME90b3JpdGFzIFNlcnRpZmlrYXQgRGlnaXRhbCBLZW1lbnRlcmlhbiBLZXVh\n" +
                "bmdhbjEkMCIGA1UECwwbT3Rvcml0YXMgU2VydGlmaWthdCBEaWdpdGFsMR0wGwYD\n" +
                "VQQKDBRLZW1lbnRlcmlhbiBLZXVhbmdhbjELMAkGA1UEBhMCSUQwHhcNMTcxMjE4\n" +
                "MDIyNjAxWhcNMTkxMjE4MDIyNjAxWjCB6DEpMCcGCSqGSIb3DQEJARYacmlkd2Fu\n" +
                "LnNpYWdpYW5AcGFqYWsuZ28uaWQxHTAbBgNVBAMMFGVua3JpcHNpZGVrcmlwc2kt\n" +
                "ZGpwMSIwIAYDVQQLDBlEaXJla3RvcmF0IEplbmRlcmFsIFBhamFrMSQwIgYDVQQL\n" +
                "DBtPdG9yaXRhcyBTZXJ0aWZpa2F0IERpZ2l0YWwxHTAbBgNVBAoMFEtlbWVudGVy\n" +
                "aWFuIEtldWFuZ2FuMRAwDgYDVQQHDAdKYWthcnRhMRQwEgYDVQQIDAtES0kgSmFr\n" +
                "YXJ0YTELMAkGA1UEBhMCSUQwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIB\n" +
                "AQDHPA3i1UiDzwFyn6XIfjjTdo6j2TorY8J5PQuElllnooag2tFhCkQ+k9LKK8Zy\n" +
                "WZ6KUM4VjaPVOOd9+YEe0aGXKRn25pu3S0nYaepLyU9Fri1IPqTred0KC8XxPvW6\n" +
                "WjlUbfGaFhg7ftj+HEYXPQojBp9fZ1xQNZKixKyY8ZX1b6KJ9teTv3oh+8yH4sAG\n" +
                "ZBWvRFLHEWvIR9lkyMjezgXfMjeVHJZAEI0qIejORvN6fe7fOkhbuDfTt76V2iPG\n" +
                "d7kSgFOziQG2mYW9Wlw2UF2qIYb94UirB5ot232nMP5/Rjwn2RKGgioGbXRWxv3I\n" +
                "di5bI8WX/f9S0gz+ipzpZSWRAgMBAAGjggFSMIIBTjAdBgNVHQ4EFgQUJ/I5mtz0\n" +
                "aNHXm5JfYZ/Ght+phwcwDAYDVR0TAQH/BAIwADAfBgNVHSMEGDAWgBS8RkfDRY4K\n" +
                "Ydjb3RtpvVYAJuaCsTCBtwYDVR0gBIGvMIGsMIGpBglggmgBAgEEYwIwgZswgZgG\n" +
                "CCsGAQUFBwICMIGLHoGIAE8AdABvAHIAaQB0AGEAcwAgAFMAZQByAHQAaQBmAGkA\n" +
                "awBhAHQAIABEAGkAZwBpAHQAYQBsACAASwBlAG0AZQBuAHQAZQByAGkAYQBuACAA\n" +
                "SwBlAHUAYQBuAGcAYQBuACAAIABDAGUAcgB0AGkAZgBpAGMAYQB0AGUAIABQAG8A\n" +
                "bABpAGMAeTAOBgNVHQ8BAf8EBAMCBeAwNAYDVR0lBC0wKwYIKwYBBQUHAwIGCCsG\n" +
                "AQUFBwMEBgorBgEEAYI3CgMMBgkqhkiG9y8BAQUwDQYJKoZIhvcNAQELBQADggEB\n" +
                "ALTKvpKj0POcZJxb7Q2fZVdATIg5n6gxED4IEKwY6nmafmfoFogAajF30wkrg7Kn\n" +
                "TNXGat9LetlhMXzsoO3u1OkrFzVIufT0RIQzBh3B4/M/aBN718CwDmrhGdR7dOUU\n" +
                "/pZHX5cDglZIBxYa0P0uskNZGT6mXJYKE73QpNcbrqWtq6PVfnQD6t7ppryd3RIk\n" +
                "dnAA8MzVxFoDim+Ld/zTKRMYmEYTluh85af7RSKmrppxFmyfHAJ7/MXnr07ezNyt\n" +
                "9vKg9MYWJClUaYWAMBesKUnr3M1Xg1X2VasgZ5WI/+tCK19dK9A/o/nkflf/EX9C\n" +
                "w2/pCLdvTlJTCakziDPMw7g=\n" +
                "-----END CERTIFICATE-----";

        try {

            /*Step 1 : listing file dalam target folder */
            System.out.println("Step 1 : listing file dalam target folder " + lokasiFolderTargetEnkripsi);

            File fileInput = new File(lokasiFolderTargetEnkripsi);
            File[] listOfFile  = fileInput.listFiles();
            if (null != listOfFile){
                if(listOfFile.length > 0){
                    IbkReader ibkReader = new IbkReader();
                    ManifestWriter manifestWriter = new ManifestWriter();
                    MainType manifest = new MainType();
                    HeaderType hdManifest = new HeaderType();

                    System.out.println("Jumlah file dalam folder "+ listOfFile.length);
                    //Looping berdasarkan daftar file dalam folder/ direktori
                    for(File tmfile : listOfFile){
                        //Baca setiap file xml untuk

                        ResponType respon = ibkReader.getXmlDataIbk("respon-ibk-v1_1_0.xsd",tmfile);
                        hdManifest.setNpwp(respon.getLembagaJasaKeuangan().getNpwpLjk());
                        hdManifest.setNamaLjk(respon.getLembagaJasaKeuangan().getNamaLjk());
                        hdManifest.setJumlahFile(BigDecimal.valueOf(listOfFile.length));

                        System.out.println(respon.getSuratJawaban().getNoSuratPermintaan());
                        DetailType dtlManifest = new DetailType();
                        if(tmfile.isFile()){
                            //listFilesTemp.add(listOfFile[i]);
                            String pathTempFileEncryptName = parentpath + "/Encrypted"+sdf.format(new Date())+".data";
                            String pathEncryptedSecretKeyFile = parentpath + "/Encrypted"+sdf.format(new Date())+".key";

                            String strDate = sdf.format(new Date());
                            String outputFileNameZip = tmfile.getName().replace(".","")+strDate+".zip";
                            execEnkripsi(tmfile,pathTempFileEncryptName,pathEncryptedSecretKeyFile, pemContent, sdf, outputFileNameZip);

                            //membuat object detail manifest
                            dtlManifest.setNoSrPermintaan(respon.getSuratJawaban().getNoSuratPermintaan());
                            dtlManifest.setNamaFile(outputFileNameZip);

                           manifest.getDetail().add(dtlManifest);
                        }else{
                            throw new Exception("Pastikan tidak terdapat folder dalam folder\n"
                                    + "Dalam hal terdapat folder dalam folder, silahkan kompress folder tersebut terlebih dahulu");
                        }
                    }

                    //Set object header manifest
                    manifest.setHeader(hdManifest);

                    //membaca folder lokasi hasil enkripsi per file/ layer pertama
                    File fileInput2 = new File(lokasiHasil);
                    List<File> listFilesTemp = new ArrayList<File>();
                    File[] listOfFile2  = fileInput2.listFiles();
                    for(File tmfile : listOfFile2){
                        listFilesTemp.add(tmfile);
                    }

                    //Apabila terdapat lebih dari satu file pada folder hasil
                    if(listFilesTemp.size()>1){
                        manifestWriter.marshal(manifest,lokasiHasil);
                        listFilesTemp.add(new File(lokasiHasil+"/manifest.xml"));

                        String pathTempFileEncryptName2 = parentpath + "/Encrypted"+sdf.format(new Date())+".data";
                        String pathEncryptedSecretKeyFile2 = parentpath + "/Encrypted"+sdf.format(new Date())+".key";
                        String lokasiBulkEncrypted = lokasiHasilBulk+"/BulkEnkrypted";
                        execEnkripsiBulk(listFilesTemp, lokasiBulkEncrypted,pathTempFileEncryptName2,pathEncryptedSecretKeyFile2, sdf, pemContent);
                    }
                }else{
                    throw new Exception("Tidak ada file dalam folder " + namaFolderTargetEnkripsi);
                }
            }else{
                throw new Exception("Tidak ada lokasi folder " + lokasiFolderTargetEnkripsi);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.print("Proses enkripsi gagal : ");
            System.err.println(e.getMessage());
        }
    }

    public static void execEnkripsi(File fileTemp, String pathTempFileEncryptName, String pathEncryptedSecretKeyFile, String pemContent, SimpleDateFormat sdf, String outputFileNameZip) throws Exception{
                    /*Step 2 : kompresi file*/
//            System.out.println("Step 2 : kompresi file");
        //Generate Secret Key untuk setiap file yang dienkripsi
        String secretKey=RandomStringUtils.randomAlphanumeric(16);
        System.out.println("Generated Secret Key :"+secretKey);
        File exportedFile;
        CompressingUtils compressingUtils = new CompressingUtils();
        String tmpPlainZipped = lokasiHasil+"/tmp"+outputFileNameZip;
        List<File> lsFile = new ArrayList<>();
        lsFile.add(fileTemp);
        if(compressingUtils.createZipWithoutPassword(lsFile, tmpPlainZipped)){
            exportedFile = new File(tmpPlainZipped);
            //delete file awal yang telah dikompresi
            for(File dfile : lsFile){
                dfile.delete();
            }
        }else{
            throw new Exception("gagal melakukan kompresi file");
        }
        System.out.println("file kompresi berhasil dibuat "+ outputFileNameZip);

            /*Step 3 : enkripsi file dengan kunci acak */
        System.out.println("Step 3 : enkripsi file dengan kunci acak");


        String fileOutputEcnryptedname = lokasiHasil+"/"+outputFileNameZip;

        File tempFileEncryptName = new File(pathTempFileEncryptName);
        try {
            CryptoUtils.encrypt(secretKey, exportedFile, tempFileEncryptName);
        } catch (CryptoException e) {
            throw new Exception("Enkripsi file gagal : " + e.getMessage());
        }

        EncryptionUtils utils = new EncryptionUtils();
        PublicKey publicKey = utils.getPublicKeyFromX509(pemContent);

            /*Step 4 : Enkripsi kunci acak dengan public key dari DJP*/
        System.out.println("Step 4 : enkripsi kunci acak dengan public key dari DJP");

        String encryptedSecretKey;
        try{
            encryptedSecretKey = CryptoUtils.encrypt(secretKey, publicKey);
        }catch (CryptoException e) {
            throw new Exception("Enkripsi kunci gagal : " + e.getMessage());
        }
        File encryptedSecretKeyFile = new File(pathEncryptedSecretKeyFile);
        try {
            FileOutputStream outputStream = new FileOutputStream(encryptedSecretKeyFile);
            outputStream.write(encryptedSecretKey.getBytes());
            outputStream.close();
        }catch (FileNotFoundException e){
            throw new Exception("kunci yang dienkripsi tidak ditemukan : " + pathEncryptedSecretKeyFile);
        } catch (IOException e) {
            throw new Exception("gagal membentuk kunci enkripsi");
        }

                /*Step 5: Compress data dan key kedalam file zip dan menjadi hasil akhir*/
        System.out.println("Step 5: Compress enkripsi file dan kunci kedalam file zip");

        List<File> listFiles = new ArrayList<File>();
        listFiles.add(tempFileEncryptName);
        listFiles.add(encryptedSecretKeyFile);

        if(listFiles.size() != 2){
            for (File file : listFiles) {
                file.delete();
            }
            throw new Exception("file enkripsi dan/atau key enkripsi salah satunya tidak ada");
        }

        compressingUtils = new CompressingUtils();
        if (compressingUtils.createZip(listFiles, fileOutputEcnryptedname)) {
                    /*Step 6 : hapus file data dan key, hasil dari step 3 dan 4 */
            System.out.println("Step 6 : hapus file data dan key, hasil dari step 3 dan 4");

            for (File file : listFiles) {
                file.delete();
            }
                    /*Step 7: hapus file zip, hasil dari step 2 */
            System.out.println("Step 7: hapus file zip, hasil dari step 2");

            exportedFile.delete();
        }

        System.out.println("Proses enkripsi selesai, nama file : " + fileOutputEcnryptedname);
    }

    //Fungsi enkripsi untuk bulkfile
    public static void execEnkripsiBulk(List<File> lsFileTemp, String pathZippedFile, String pathTempFileEncryptName, String pathEncryptedSecretKeyFile, SimpleDateFormat sdf, String pemContent) throws Exception{
                    /*Step 2 : kompresi file*/
//            System.out.println("Step 2 : kompresi file");

        //Generate Secret Key untuk setiap file yang dienkripsi
        /**
         * Kunci acak 16 Digit, dan setiap pengekripsian harus beda
         * contoh : A1b2C3D4E5F6G7h8
         * */
        String secretKey=RandomStringUtils.randomAlphanumeric(16);
        System.out.println("Generated Secret Key :"+secretKey);

        File exportedFile;
        CompressingUtils compressingUtils = new CompressingUtils();
        String outputTmpFileNameZip = pathZippedFile.replace(".","")+"Tmp.zip";
        String outputFileNameZip = pathZippedFile.replace(".","")+sdf.format(new Date())+".zip";

        compressingUtils = new CompressingUtils();
        if(compressingUtils.createZipWithoutPassword(lsFileTemp, outputTmpFileNameZip)){
            exportedFile = new File(outputTmpFileNameZip);
        }else{
            throw new Exception("gagal melakukan kompresi file tanpa password");
        }


        //delete file hasil enkripsi per file yang telah dimasukan ke dalam file kompresi untuk enkripsi tahap gabungan file
        for(File file : lsFileTemp){
            file.delete();
        }

        System.out.println("file kompresi berhasil dibuat "+ outputFileNameZip);

            /*Step 3 : enkripsi file dengan kunci acak */
        System.out.println("Step 3 : enkripsi file dengan kunci acak");


        String fileOutputEcnryptedname = outputFileNameZip;

        File tempFileEncryptName = new File(pathTempFileEncryptName);
        try {
            CryptoUtils.encrypt(secretKey, exportedFile, tempFileEncryptName);
        } catch (CryptoException e) {
            throw new Exception("Enkripsi file gagal : " + e.getMessage());
        }


        EncryptionUtils utils = new EncryptionUtils();
        PublicKey publicKey = utils.getPublicKeyFromX509(pemContent);

            /*Step 4 : Enkripsi kunci acak dengan public key dari DJP*/
        System.out.println("Step 4 : enkripsi kunci acak dengan public key dari DJP");

        String encryptedSecretKey;
        try{
            encryptedSecretKey = CryptoUtils.encrypt(secretKey, publicKey);
        }catch (CryptoException e) {
            throw new Exception("Enkripsi kunci gagal : " + e.getMessage());
        }
        File encryptedSecretKeyFile = new File(pathEncryptedSecretKeyFile);
        try {
            FileOutputStream outputStream = new FileOutputStream(encryptedSecretKeyFile);
            outputStream.write(encryptedSecretKey.getBytes());
            outputStream.close();
        }catch (FileNotFoundException e){
            throw new Exception("kunci yang dienkripsi tidak ditemukan : " + pathEncryptedSecretKeyFile);
        } catch (IOException e) {
            throw new Exception("gagal membentuk kunci enkripsi");
        }

                /*Step 5: Compress data dan key kedalam file zip dan menjadi hasil akhir*/
        System.out.println("Step 5: Compress enkripsi file dan kunci kedalam file zip");

        List<File> listFiles = new ArrayList<File>();
        listFiles.add(tempFileEncryptName);
        listFiles.add(encryptedSecretKeyFile);

        if(listFiles.size() != 2){
            for (File file : listFiles) {
                file.delete();
            }
            throw new Exception("file enkripsi dan/atau key enkripsi salah satunya tidak ada");
        }

        compressingUtils = new CompressingUtils();
        if (compressingUtils.createZip(listFiles, fileOutputEcnryptedname)) {
                    /*Step 6 : hapus file data dan key, hasil dari step 3 dan 4 */
            System.out.println("Step 6 : hapus file data dan key, hasil dari step 3 dan 4");

            for (File file : listFiles) {
                file.delete();
            }
                    /*Step 7: hapus file zip, hasil dari step 2 */
            System.out.println("Step 7: hapus file zip, hasil dari step 2");

            exportedFile.delete();
        }

        System.out.println("Proses enkripsi selesai, nama file : " + fileOutputEcnryptedname);
    }

    private static void setLokasiFile(){
        lokasiFile = "C:\\temp\\EOIDJP\\IBK21";
        lokasiHasil ="C:\\temp\\EOIDJP\\ENK-HASIL";
        lokasiHasilBulk ="C:\\temp\\EOIDJP\\ENK-BULK-HASIL";

        List<String> lsLokasi = new ArrayList<>();
        lsLokasi.add(lokasiFile);
        lsLokasi.add(lokasiHasil);
        lsLokasi.add(lokasiHasilBulk);
        for(String lok : lsLokasi){
            File dir = new File(lok);
            if(!dir.exists()){
                dir.mkdirs();
            }
        }

    }
}

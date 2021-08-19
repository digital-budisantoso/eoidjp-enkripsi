import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.CryptoException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * Created by 060111737 on 19/06/2019.
 */
public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static void encrypt(String key, File inputFile, File outputFile) throws CryptoException {
        try {
            doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
        } catch (CryptoException e) {
            throw new CryptoException(e.toString(),e);
        } catch(Exception e){
            throw new CryptoException(e.toString(),e);
        }
    }

    private static void doCrypto(int cipherMode, String key, File inputFile,
                                 File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);;

            //proses enkripsi
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
        }catch (NoSuchPaddingException ex) {
            throw new CryptoException("Tidak Dapat melakukan Kriptografi", ex);
        }catch (NoSuchAlgorithmException ex) {
            throw new CryptoException("Tidak Dapat melakukan Kriptografi", ex);
        }catch (InvalidKeyException ex) {
            throw new CryptoException("Tidak Dapat melakukan Kriptografi", ex);
        }catch (BadPaddingException ex) {
            throw new CryptoException("Tidak Dapat melakukan Kriptografi", ex);
        }catch (IllegalBlockSizeException ex) {
            throw new CryptoException("Tidak Dapat melakukan Kriptografi", ex);
        }catch (IOException ex) {
            throw new CryptoException("Tidak Dapat melakukan Kriptografi", ex);
        }  catch(OutOfMemoryError e){
            //throw new CryptoException("Gagal melakukan enkripsi, Java heap space",e);
        }
    }

    public static String encrypt(String input, PublicKey publicKey) throws CryptoException {
        String encryptedSecretKey = null;
        try {
            Cipher encrypt = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            encrypt.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedSecretKey = Base64.encodeBase64String(encrypt.doFinal(input.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            throw new CryptoException("Tidak Dapat Melakukan Enkripsi Kunci", ex);
        }catch (NoSuchPaddingException ex) {
            throw new CryptoException("Tidak Dapat Melakukan Enkripsi Kunci", ex);
        }catch (InvalidKeyException ex) {
            throw new CryptoException("Tidak Dapat Melakukan Enkripsi Kunci", ex);
        }catch (IllegalBlockSizeException ex) {
            throw new CryptoException("Tidak Dapat Melakukan Enkripsi Kunci", ex);
        }catch (BadPaddingException ex) {
            throw new CryptoException("Tidak Dapat Melakukan Enkripsi Kunci", ex);
        }
        return encryptedSecretKey;
    }
}

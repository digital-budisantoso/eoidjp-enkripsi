import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.security.auth.x500.X500Principal;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.*;

/**
 * Created by 060111737 on 18/06/2019.
 */
public class EncryptionUtils {
    public EncryptionUtils()
    {
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        try
        {
            Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, Boolean.FALSE);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public String getCN(X509Certificate cert)
    {
        String cn = null;
        try
        {
            X500Principal principal = cert.getSubjectX500Principal();
            X500Name x500name = new X500Name(principal.getName());
            RDN rdcn = x500name.getRDNs(BCStyle.CN)[0];
            cn = IETFUtils.valueToString(rdcn.getFirst().getValue());
        }
        catch(Exception ex)
        {
            //            Logger.getLogger(id/go/lemsaneg/osd/KeyStoreUtils.getName()).log(Level.SEVERE, null, ex);
        }
        return cn;
    }

    public String getIssuer(String cert)
    {
        X509Certificate cer = pemToX509(cert);
        String issuerDN = cer.getIssuerDN().toString();
        return issuerDN;
    }

    public String convertToPem(X509Certificate cert)
            throws CertificateEncodingException
    {
        Base64 encoder = new Base64(64);
        String cert_begin = "-----BEGIN CERTIFICATE-----\n";
        String end_cert = "-----END CERTIFICATE-----";
        byte derCert[] = cert.getEncoded();
        String pemCertPre = new String(encoder.encode(derCert));
        String pemCert = (new StringBuilder()).append(cert_begin).append(pemCertPre).append(end_cert).toString();
        System.out.println(pemCert);
        return pemCert;
    }

    public X509Certificate pemToX509(String cer)
    {
        Certificate myCert = null;
        try
        {
            myCert = CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(cer.getBytes()));
        }
        catch(CertificateException ex)
        {
            System.out.println("Error " + ex.toString());
        }
        return (X509Certificate)myCert;
    }

    public PublicKey getPublicKeyFromX509(String pkcs7){
        PublicKey pub = null;
        try {
            X509Certificate myCert = pemToX509(pkcs7);
            pub = myCert.getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pub;
    }
}

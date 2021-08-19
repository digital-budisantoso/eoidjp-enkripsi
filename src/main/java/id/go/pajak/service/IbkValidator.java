package id.go.pajak.service;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by usrdjp on 4/14/2021.
 */
public class IbkValidator {
    public IbkValidator(){

    }

    public void validate(String xsdPath, String xmlPath){
        try {
            //SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            System.out.println("Instant Document "+xmlPath+" Berhasil dibuat dan telah tervalidasi");
        } catch (IOException e){
            System.out.println("Exception: "+e.getMessage());
        }catch(SAXParseException e2){
            int line = e2.getLineNumber();
            int col = e2.getColumnNumber();
            String message = e2.getMessage();
            String prsMessage = "Ditemukan Error ketika validasi XML dengan Skema XSD\n" +
                    "baris: " + line + "\n" +
                    "kolom: " + col + "\n" +
                    "pesan: " + message.substring(message.indexOf(":") + 2);
            System.out.println("Ditemukan Error ketika validasi XML dengant Skema XSD\n" +
                    "baris: " + line + "\n" +
                    "kolom: " + col + "\n" +
                    "pesan: " + message.substring(message.indexOf(":") + 2));

        }
        catch(SAXException e1){
            System.out.println("SAX Exception: "+e1.getMessage());

        }
    }
}

package id.go.pajak.service;

import id.go.pajak.ibk.model.ResponType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Created by usrdjp on 8/19/2021.
 */
public class IbkReader {
    public IbkReader(){};
    public ResponType getXmlDataIbk(String xsdFile, File xmlFile){
        JAXBContext jaxbContext;
        ResponType respon = new ResponType();
        try {
            jaxbContext = JAXBContext.newInstance(ResponType.class);
            //Create Unmarshaller
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            //Setup schema validator
            //SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
            Schema responSchema = sf.newSchema(new File(xsdFile));
            jaxbUnmarshaller.setSchema(responSchema);

            //Unmarshal xml file
            //Employee employee = (Employee) jaxbUnmarshaller.unmarshal(new File(xmlFile));
            respon = (ResponType) jaxbUnmarshaller.unmarshal(xmlFile);


        }catch(Exception e){
            e.printStackTrace();
        }
        return respon;
    }
}

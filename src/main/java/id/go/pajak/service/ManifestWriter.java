package id.go.pajak.service;

import id.go.pajak.enkripsi.model.MainType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;

/**
 * Created by usrdjp on 8/19/2021.
 */
public class ManifestWriter {
    public ManifestWriter(){};

    public void marshal(MainType objManifest, String pathHasil)throws JAXBException, IOException, Exception{
        JAXBContext context = JAXBContext.newInstance(MainType.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        mar.marshal(objManifest,new File(pathHasil+"/manifest"+".xml"));
    }
}

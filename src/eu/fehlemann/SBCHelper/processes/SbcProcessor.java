package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public abstract class SbcProcessor extends LoggedProcessor {
    protected File source;
    protected Document document;
    protected Element definition;

    public SbcProcessor(String actionName, File source) throws IOException, ParserConfigurationException, SAXException {
        super(actionName);
        this.source = source;
        prepareFile();
        document = SBCHelper.getDocument(this.source);
        definition = document.getDocumentElement();
    }

    protected void prepareFile() throws IOException {
        if (!source.exists()) {
            FileWriter writer = new FileWriter(source);
            writer.write("<?xml version=\"1.0\"?>\n" +
                    "<Definitions xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                    "</Definitions>");
            writer.flush();
            writer.close();
        }
    }

    protected void save() throws TransformerException, FileNotFoundException {
        SBCHelper.writeDocument(this.document, this.source);
    }
}

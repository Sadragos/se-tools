package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ResearchBlockProcessor extends SbcProcessor {
    Element researchBlocks;

    public ResearchBlockProcessor(File target) throws ParserConfigurationException, SAXException, IOException {
        super("ResearchBlock", target);
        NodeList blocks = definition.getElementsByTagName("ResearchBlocks");
        if(blocks.getLength() == 0) {
            researchBlocks = document.createElement("ResearchBlocks");
            definition.appendChild(researchBlocks);
        } else {
            researchBlocks = (Element) blocks.item(0);
        }
    }

    @Override
    public boolean process(SBCID id, Element element) {
        String mob = element.getAttribute("xsi:type");
        mob = mob.replaceAll("Definition", "");

        Element block = document.createElement("ResearchBlock");
        block.setAttribute("xsi:type", "ResearchBlock");
        Element idElement = document.createElement("Id");
        idElement.setAttribute("Type", mob);
        idElement.setAttribute("Subtype", id.subtypeId);
        Element unlock = document.createElement("UnlockedByGroups");
        Element groupsubtype = document.createElement("GroupSubtype");
        groupsubtype.setTextContent("0");

        unlock.appendChild(groupsubtype);
        block.appendChild(unlock);
        block.appendChild(idElement);
        researchBlocks.appendChild(block);
        try {
            save();
        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }

        log(element, "appended");
        return true;
    }
}

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

public class ResearchGroupProcessor extends SbcProcessor {
    StringBuilder builder;
    Element researchGroup;
    public ResearchGroupProcessor(File target) throws ParserConfigurationException, SAXException, IOException {
        super("ResearchGroup", target);
        NodeList blocks = definition.getElementsByTagName("ResearchGroups");
        if(blocks.getLength() == 0) {
            researchGroup = document.createElement("ResearchGroups");
            definition.appendChild(researchGroup);
        } else {
            researchGroup = (Element) blocks.item(0);
        }
    }

    @Override
    public boolean process(SBCID id, Element element) {
        String mob = element.getAttribute("xsi:type");
        mob = mob.replaceAll("Definition", "");

        Element group = document.createElement("ResearchGroup");
        group.setAttribute("xsi:type", "ResearchGroup");
        Element idElement = document.createElement("Id");
        idElement.setAttribute("Type", "MyObjectBuilder_ResearchGroupDefinition");
        idElement.setAttribute("Subtype", "0");
        Element members = document.createElement("Members");
        Element blockId = document.createElement("BlockId");
        blockId.setAttribute("Type", mob);
        blockId.setAttribute("Subtype", id.subtypeId);

        members.appendChild(blockId);
        group.appendChild(members);
        group.appendChild(idElement);
        researchGroup.appendChild(group);
        try {
            save();
        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }

        log(element, "appended");
        return true;

    }
}

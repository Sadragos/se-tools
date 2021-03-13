package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ComponentStripper extends LoggedProcessor {
    public ComponentStripper() {
        super("Stripping");
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName("Components");
        if (list != null && list.getLength() > 0) {
            Element componentElement = (Element) list.item(0);
            NodeList components = componentElement.getElementsByTagName("Component");
            log(componentElement, components.getLength() + " components removed");
            for (int j = components.getLength() - 1; j >= 0; j--) {
                componentElement.removeChild(components.item(j));
            }
            componentElement.setTextContent("");
        }
        return true;
    }
}

package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ComponentCritical extends LoggedProcessor {
    public String subtype;
    public int index;


    public ComponentCritical(String subtype, int index) {
        super("CriticalComponent");
        this.subtype = subtype;
        this.index = index;
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName("CriticalComponent");
        if (list != null && list.getLength() > 0) {
            Element componentElement = (Element) list.item(0);
            componentElement.setAttribute("Index", String.valueOf(index));
            componentElement.setAttribute("Subtype", subtype);
            log(element, subtype + " at Index " + index);
        }
        return true;
    }
}

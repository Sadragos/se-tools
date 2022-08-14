package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ComponentMultiplicator extends LoggedProcessor {
    double multi;
    int roundTo = 0;

    public ComponentMultiplicator(double multi, int roundTo) {
        super("Multiply");
        this.multi = multi;
        this.roundTo = roundTo;
    }

    public ComponentMultiplicator(double multi) {
        super("Multiply");
        this.multi = multi;
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName("Components");
        if (list != null && list.getLength() > 0) {
            Element componentElement = (Element) list.item(0);
            NodeList components = componentElement.getElementsByTagName("Component");
            log(componentElement, components.getLength() + " components multiplied by " + multi);
            for (int i = 0; i < components.getLength(); i++) {
                Element ele = (Element) components.item(i);
                double newValue = Math.ceil(Integer.parseInt(ele.getAttribute("Count")) * multi);
                if (roundTo > 0) {
                    long div = (long) ((newValue / roundTo) + 1);
                    newValue = div * multi;
                }
                ele.setAttribute("Count", String.valueOf((long) newValue));
            }
        }
        return true;
    }
}

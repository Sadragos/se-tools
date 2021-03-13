package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Scanner;

public class ComponentAdderUser extends ComponentAdder {
    private static final Scanner scanner = new Scanner(System.in);

    public ComponentAdderUser(String subtype) {
        super(subtype, 0);
        this.subtype = subtype;
    }

    public ComponentAdderUser( String subtype, String deconstructTypeId, String deconstructSubtypeId) {
        super(subtype, 0, deconstructTypeId, deconstructSubtypeId);
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName("Components");
        if (list != null && list.getLength() > 0) {
            Element componentElement = (Element) list.item(0);
            Element component = componentElement.getOwnerDocument().createElement("Component");
            component.setAttribute("Subtype", subtype);
            logSL(element, "Number of " + subtype + ": ");
            String value = scanner.nextLine();
            if(!value.trim().equals("")) {
                component.setAttribute("Count", value);
                addDeconstruct(component);
                componentElement.appendChild(component);
            }
        }
        return true;
    }
}

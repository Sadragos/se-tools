package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ComponentAdder extends LoggedProcessor {
    public String subtype;
    public int amount;
    public String deconstructTypeId, deconstructSubtypeId;


    public ComponentAdder(String subtype, int amount) {
        super("Adding");
        this.subtype = subtype;
        this.amount = amount;
    }

    public ComponentAdder(String subtype, int amount, String deconstructTypeId, String deconstructSubtypeId) {
        super("Adding");
        this.subtype = subtype;
        this.amount = amount;
        this.deconstructTypeId = deconstructTypeId;
        this.deconstructSubtypeId = deconstructSubtypeId;
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName("Components");
        if (list != null && list.getLength() > 0) {
            Element componentElement = (Element) list.item(0);
            Element component = componentElement.getOwnerDocument().createElement("Component");
            component.setAttribute("Subtype", subtype);
            component.setAttribute("Count", String.valueOf(amount));

            addDeconstruct(component);

            componentElement.appendChild(component);
            log(element, amount + "x " + subtype);
        }
        return true;
    }

    protected void addDeconstruct(Element element) {
        if (deconstructTypeId != null && deconstructSubtypeId != null) {
            Element DeconstructId = element.getOwnerDocument().createElement("DeconstructId");
            Element TypeId = element.getOwnerDocument().createElement("TypeId");
            TypeId.setTextContent("Ore");
            DeconstructId.appendChild(TypeId);
            Element SubtypeId = element.getOwnerDocument().createElement("SubtypeId");
            SubtypeId.setTextContent("Scrap");
            DeconstructId.appendChild(SubtypeId);
            element.appendChild(DeconstructId);
        }
    }
}

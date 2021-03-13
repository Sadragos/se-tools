package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NumberContentMultiplicator extends LoggedProcessor {
    String elementTag;
    double multi;

    public NumberContentMultiplicator(String elementTag, double multi) {
        super("Multiply");
        this.multi = multi;
        this.elementTag = elementTag;
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName(elementTag);
        if (list != null && list.getLength() > 0) {
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                try {
                    double value = Double.parseDouble(node.getTextContent());
                    double newValue = value * multi;
                    log(node,  value + " => " + newValue);
                    node.setTextContent(String.valueOf(newValue));
                } catch (NumberFormatException e) {
                    log(node,  node.getTextContent() + " => ERROR");
                }

            }
        }
        return true;
    }
}

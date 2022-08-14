package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TextAttributeReplacer extends LoggedProcessor {
    String search;
    String replace;
    String elementTag;

    String attribute;

    public TextAttributeReplacer(String elementTag, String attribute, String search, String replace) {
        super("Replace");
        this.search = search;
        this.replace = replace;
        this.elementTag = elementTag;
        this.attribute = attribute;
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName(elementTag);
        if (list != null && list.getLength() > 0) {
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (!node.hasAttributes()) continue;
                for (int a = 0; a < node.getAttributes().getLength(); a++) {
                    Node item = node.getAttributes().item(a);
                    if (!item.getNodeName().equals(this.attribute)) continue;
                    item.setNodeValue(item.getNodeValue().replaceAll(search, replace));
                    log(node, node.getTextContent() + " => " + item.getNodeValue());
                }
            }
        }
        return true;
    }
}

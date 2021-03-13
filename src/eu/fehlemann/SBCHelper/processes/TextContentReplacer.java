package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TextContentReplacer extends LoggedProcessor {
    String search;
    String replace;
    String elementTag;

    public TextContentReplacer(String elementTag, String search, String replace) {
        super("Replace");
        this.search = search;
        this.replace = replace;
        this.elementTag = elementTag;
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName(elementTag);
        if (list != null && list.getLength() > 0) {
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                String newText = node.getTextContent().replaceAll(search, replace);
                log(node, node.getTextContent() + " => " + newText);
                node.setTextContent(newText);
            }
        }
        return true;
    }
}

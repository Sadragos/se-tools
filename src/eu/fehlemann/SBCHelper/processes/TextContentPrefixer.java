package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TextContentPrefixer extends LoggedProcessor {
    String elementTag;
    String prefix;

    public TextContentPrefixer(String elementTag, String prefix) {
        super("Prefix");
        this.elementTag = elementTag;
        this.prefix = prefix;
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName(elementTag);
        if (list != null && list.getLength() > 0) {
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getTextContent().startsWith(prefix)) {
                    log(node, node.getTextContent() + " => " + node.getTextContent());
                    continue;
                }
                String newText = prefix + node.getTextContent();
                log(node,  node.getTextContent() + " => " + newText);
                node.setTextContent(newText);
            }
        }
        return true;
    }
}

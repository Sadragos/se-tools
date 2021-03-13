package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TextContentSuffixer extends LoggedProcessor {
    String elementTag;
    String suffix;

    public TextContentSuffixer(String elementTag, String suffix) {
        super("Suffix");
        this.elementTag = elementTag;
        this.suffix = suffix;
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName(elementTag);
        if (list != null && list.getLength() > 0) {
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getTextContent().endsWith(suffix)) {
                    log(node, node.getTextContent() + " => " + node.getTextContent());
                    continue;
                }
                String newText = node.getTextContent() + suffix;
                log(node, node.getTextContent() + " => " + newText);
                node.setTextContent(newText);
            }
        }
        return true;
    }
}

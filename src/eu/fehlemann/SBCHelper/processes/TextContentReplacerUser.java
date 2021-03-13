package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Scanner;

public class TextContentReplacerUser extends LoggedProcessor {
    private static final Scanner scanner = new Scanner(System.in);

    String elementTag;


    public TextContentReplacerUser(String elementTag) {
        super("User Replace");
        this.elementTag = elementTag;
    }

    @Override
    public boolean process(SBCID id, Element element) {
        NodeList list = element.getElementsByTagName(elementTag);
        if (list != null && list.getLength() > 0) {
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                logSL(node, node.getTextContent() + " => ");
                String text = scanner.nextLine();
                if (text.trim().equals("")) {
                    continue;
                }
                node.setTextContent(text);
            }
        }
        return true;
    }
}

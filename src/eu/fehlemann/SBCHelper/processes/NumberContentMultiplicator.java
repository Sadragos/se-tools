package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberContentMultiplicator extends LoggedProcessor {
    String elementTag;
    double multi;
    String format = "#.##########";
    int roundTo = 0;

    public NumberContentMultiplicator(String elementTag, double multi) {
        super("Multiply");
        this.multi = multi;
        this.elementTag = elementTag;
    }

    public NumberContentMultiplicator(String elementTag, double multi, String format, int roundTo) {
        super("Multiply");
        this.multi = multi;
        this.elementTag = elementTag;
        this.format = format;
        this.roundTo = roundTo;
    }

    @Override
    public boolean process(SBCID id, Element element) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat decFormat = new DecimalFormat(this.format, otherSymbols);

        NodeList list = element.getElementsByTagName(elementTag);
        if (list != null && list.getLength() > 0) {
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                try {
                    double value = Double.parseDouble(node.getTextContent());
                    double newValue = value * multi;
                    if (roundTo > 0) {
                        long div = (long) ((newValue / roundTo) + 1);
                        newValue = div * roundTo;
                    }
                    log(node, value + " => " + decFormat.format(newValue));
                    node.setTextContent(decFormat.format(newValue));
                } catch (NumberFormatException e) {
                    log(node, node.getTextContent() + " => ERROR");
                }

            }
        }
        return true;
    }
}

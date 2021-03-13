package eu.fehlemann.SBCHelper.processes;

import org.w3c.dom.Element;
import eu.fehlemann.SBCHelper.SBCID;

public interface Processor {
    boolean process(SBCID id, Element element);
}

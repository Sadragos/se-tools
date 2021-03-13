package eu.fehlemann.SBCHelper.processes;

import org.w3c.dom.Node;

public abstract class LoggedProcessor implements Processor {
    String actionName;

    public LoggedProcessor(String actionName) {
        this.actionName = actionName;
    }

    protected void log(Node node, String message) {
        System.out.println("  " + actionName + " <" + node.getNodeName() + "> " + message);
    }
    protected void logSL(Node node, String message) {
        System.out.print("  " + actionName + " <" + node.getNodeName() + "> " + message);
    }
}

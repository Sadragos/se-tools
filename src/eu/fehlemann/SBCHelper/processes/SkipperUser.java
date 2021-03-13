package eu.fehlemann.SBCHelper.processes;

import eu.fehlemann.SBCHelper.SBCID;
import org.w3c.dom.Element;

import java.util.Scanner;

public class SkipperUser implements Processor {
    private static final Scanner scanner = new Scanner(System.in);
    @Override
    public boolean process(SBCID id, Element element) {
        System.out.print("  skip this Definition? (y/N): ");
        String input = scanner.nextLine();
        return !input.trim().toLowerCase().equals("y");
    }
}

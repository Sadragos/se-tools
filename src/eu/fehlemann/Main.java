package eu.fehlemann;

import eu.fehlemann.SBCHelper.SBCHelper;
import eu.fehlemann.SBCHelper.processes.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ParserConfigurationException, SAXException, TransformerException {
        switch (args[0]) {
            case "overlay":
                overlay(args);
                break;
            case "research":
                research(args);
                break;
            case "convert":
                convertImage(args);
                break;
            default:
                System.out.println("Unbekannter Befehl");
        }

    }

    public static void convertImage(String[] args) throws IOException, InterruptedException {
        if (args.length != 2) {
            System.out.println("convert <folder>");
        } else {
            File folder = new File(args[1]);
            ImageConverter.convertFolder(folder);
        }
    }

    public static void research(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        if (args.length != 2) {
            System.out.println("convert <file>");
        } else {
            File file = new File(args[1]);
            StringBuilder researchBlocks = new StringBuilder("<?xml version=\"1.0\"?>\n" +
                    "<Definitions xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                    "  <ResearchBlocks>");
            StringBuilder researchGroups = new StringBuilder("<?xml version=\"1.0\"?>\n" +
                    "<Definitions xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                    "  <ResearchGroups>");
            SBCHelper.process(file, new Processor[]{
                    new SkipperUser(),
                    new TextContentReplacerUser("DisplayName"),
                    new TextContentReplacer("Icon", "Textures\\\\GUI\\\\Icons\\\\Cubes\\\\(.*?)\\.dds", "Textures\\\\$1Research.dds"),
                    new TextContentSuffixer("SubtypeId", "Research"),
                    new TextContentSuffixer("BlockPairName", "Research"),
                    new NumberContentMultiplicator("BuildTimeSeconds", 5),
                    new ComponentStripper(),
                    new ComponentAdder("SteelPlate", 1),
                    new ComponentAdderUser("PrototypeEarth", "Ore", "Scrap"),
                    new ComponentAdderUser("PrototypeSpace", "Ore", "Scrap"),
                    new ComponentAdderUser("PrototypeAlien", "Ore", "Scrap"),
                    new ComponentAdder("SteelPlate", 1),
                    new ComponentCritical("PrototypeEarth",0),
                    new ResearchBlockProcessor(new File("ResearchBlocks.sbc")),
                    new ResearchGroupProcessor(new File("ResearchGroups.sbc"))
            });
        }
    }

    public static void overlay(String[] args) throws IOException {
        if (args.length != 6) {
            System.out.println("overlay <folder> <overlayFolder> <overlay-height> <overlay-width> <position>");
        } else {
            File folder = new File(args[1]);
            File overlayFolder = new File(args[2]);
            int height = Integer.parseInt(args[3]);
            int width = Integer.parseInt(args[4]);
            OverlayEditor.Position pos = OverlayEditor.Position.valueOf(args[5]);

            OverlayEditor.addOverlays(
                    ArrayUtils.ArrayToList(folder.listFiles((file, s) -> s.toLowerCase().endsWith("png"))),
                    ArrayUtils.ArrayToList(overlayFolder.listFiles((file, s) -> s.toLowerCase().endsWith("png"))),
                    pos, width, height
            );
        }
    }


}

package eu.fehlemann;

import eu.fehlemann.SBCHelper.SBCHelper;
import eu.fehlemann.SBCHelper.processes.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ParserConfigurationException, SAXException, TransformerException {
        if(args.length > 0) {
            switch (args[0]) {
                case "overlay":
                    overlay(args);
                    return;
                case "research":
                    research(args);
                    return;
                case "convert":
                    convertImage(args);
                    return;
                case "multiplyBlocks":
                    multiplyBlocks(args);
                    return;
                case "multiplyWeapons":
                    multiplyWeapons(args);
                    return;
            }
        }
        System.out.println("Unbekannter Befehl. Folgende Befehle sind möglich: overlay, research, convert, multiplyBlocks, multiplyWeapons");
    }


    private static void multiplyWeapons(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        if (args.length != 2) {
            System.out.println("multiplyWeapons <file>");
            return;
        }
        File file = new File(args[1]);

        for(int i = 1; i <= 3; i++) {
            int tier = ((int)Math.pow(2, i));
            Document doc = SBCHelper.process(file, new Processor[]{
                    new TextContentSuffixer("SubtypeId", tier + "x"),
                    new NumberAttributeMultiplicator("MissileAmmoData", "RateOfFire", Math.pow(1.25, i), "#", 5),
                    new NumberAttributeMultiplicator("ProjectileAmmoData", "RateOfFire", Math.pow(1.25, i), "#", 5),
                    new NumberContentMultiplicator("ReloadTime", Math.pow(0.85, i), "#", 50),
                    new NumberContentMultiplicator("DeviateShotAngle", Math.pow(0.8, i), "#.###", 0),
                    new NumberContentMultiplicator("DeviateShotAngleAiming", Math.pow(0.8, i), "#.###", 0),
                    new NumberContentMultiplicator("DamageMultiplier", Math.pow(1.25, i), "#.###", 0),
                    new NumberContentMultiplicator("RangeMultiplier", Math.pow(1.1, i), "#.###", 0),
            }, "Weapons", "Weapon");
            SBCHelper.writeDocument(doc, new File(file.getParentFile(), file.getName().replace(".sbc", tier + "x.sbc")));
        }
    }

    private static void multiplyBlocks(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        if (args.length != 2) {
            System.out.println("multiplyBlocks <file>");
            return;
        }
        File file = new File(args[1]);
        String[] tiers = new String[] {"Vanilla", "Enhanced", "Proficient", "Elite"};

        for(int i = 1; i <= 3; i++) {
            int tier = ((int)Math.pow(2, i));
            Document doc = SBCHelper.process(file, new Processor[]{
                    new ComponentMultiplicator(Math.pow(1.5, i)),
                    new ComponentAdderUser("Tech" + tier + "x"),
                    new TextContentReplacer("Icon", "GUI\\\\Icons\\\\Cubes\\\\", ""),
                    new TextContentReplacer("Icon", ".dds", tier+"x.dds"),
                    new TextContentSuffixer("SubtypeId", tier + "x"),
                    new TextContentSuffixer("BlockPairName", tier + "x"),
                    new TextContentReplacer("DisplayName", "DisplayName", ""),
                    new TextContentReplacer("DisplayName", "Block", ""),
                    new TextContentReplacer("DisplayName", "_", ""),
                    new TextContentPrefixer("DisplayName", tiers[i] + " "),
                    new NumberContentMultiplicator("BuildTimeSeconds", Math.pow(1.5, i), "#", 5),
                    new NumberContentMultiplicator("InventoryMaxVolume", Math.pow(2, i), "#.#####", 0),
                    new NumberContentMultiplicator("Capacity", Math.pow(2, i), "#.#####", 0),
                    new NumberContentMultiplicator("GasExplosionNeededVolumeToReachMaxRadius", Math.pow(2, i), "#", 0),
                    new NumberContentMultiplicator("GasExplosionMaxRadius", Math.pow(1.75, i), "#", 0),
                    new NumberContentMultiplicator("ForceMagnitude", Math.pow(2, i), "#", 100),
                    new NumberContentMultiplicator("MaxPowerConsumption", Math.pow(1.8, i), "#.####", 0),
                    new NumberContentMultiplicator("MinPowerConsumption", Math.pow(1.8, i)),
                    new NumberContentMultiplicator("EffectivenessAtMaxInfluence", Math.pow(1.1, i),"#.#####", 0),
                    new NumberContentMultiplicator("MaxJumpDistance", Math.pow(1.75, i), "#", 100),
                    new NumberContentMultiplicator("MaxJumpMass", Math.pow(1.75, i), "#", 100),
                    new NumberContentMultiplicator("RequiredPowerInput", Math.pow(2, i), "#.######", 0),
                    new NumberContentMultiplicator("PowerNeededForJump", Math.pow(2, i), "#.######", 0),
                    new NumberContentMultiplicator("MaxOutputPerSecond", Math.pow(2, i), "#.######", 0),
                    new NumberContentMultiplicator("OperationalPowerConsumption", Math.pow(1.8, i), "#.######", 0),
                    new NumberContentMultiplicator("IceConsumptionPerSecond", Math.pow(2, i), "#.###", 0),
                    new NumberContentMultiplicator("MaxPowerOutput", Math.pow(1.75, i), "#.######", 0),
                    new NumberContentMultiplicator("RefineSpeed", Math.pow(1.75, i), "#.######", 0),
                    new NumberContentMultiplicator("MaterialEfficiency", Math.pow(1.05, i), "#.######", 0),
                    new NumberContentMultiplicator("OreAmountPerPullRequest", Math.pow(2, i), "#", 100),
                    new NumberContentMultiplicator("SensorRadius", Math.pow(1.5, i), "#.######", 0),
                    new NumberContentMultiplicator("MaxRangeMeters", Math.pow(1.1, i), "#", 10),
                    new NumberContentMultiplicator("RotationSpeed", Math.pow(1.1, i), "#.######", 0),
                    new NumberContentMultiplicator("ElevationSpeed", Math.pow(1.1, i), "#.######", 0),
                    new TextAttributeReplacer("WeaponDefinitionId", "Subtype", "$", tier+"x"),
            });
            SBCHelper.writeDocument(doc, new File(file.getParentFile(), file.getName().replace(".sbc", tier + "x.sbc")));
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
            Document doc = SBCHelper.process(file, new Processor[]{
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
            SBCHelper.writeDocument(doc, file);
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

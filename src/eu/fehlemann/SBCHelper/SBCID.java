package eu.fehlemann.SBCHelper;

public class SBCID {
    public String typeId;
    public String subtypeId;

    public SBCID(String typeId, String subtypeId) {
        this.typeId = typeId;
        this.subtypeId = subtypeId;
    }

    @Override
    public String toString() {
        return typeId + "/" + subtypeId;
    }
}

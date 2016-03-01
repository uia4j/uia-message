package uia.message.fx;

import java.util.Map;

public interface ValueFx {

    public int encode(String blockName, Object value, Map<String, Object> parsed);

    public int decode(String blockName, byte[] data, Map<String, Object> parsed, int bytePt, int bitPt);
}

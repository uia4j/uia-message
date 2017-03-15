package uia.message;

import java.util.Map;

import uia.message.fx.ValueFx;

public class RcvFx3List implements ValueFx {

    @Override
    public int encode(String blockName, Object value, Map<String, Object> parsed) {
        return (Integer) parsed.get("count");
    }

    @Override
    public int decode(String blockName, byte[] data, Map<String, Object> parsed, int bytePt, int bitPt) {
        return (Integer) parsed.get("count");
    }

}

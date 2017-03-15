package uia.message;

import java.util.Map;

import uia.message.fx.ValueFx;

public class RcvFx7 implements ValueFx {

    @Override
    public int encode(String blockName, Object value, Map<String, Object> parsed) {
        return parsed.get("content1").toString().length() * 2;
    }

    @Override
    public int decode(String blockName, byte[] data, Map<String, Object> parsed, int bytePt, int bitPt) {
        return parsed.get("content1").toString().trim().length() * 2;
    }

}

package uia.message;

import java.util.Map;

import uia.message.fx.ValueFx;

public class RcvFx3SeqList implements ValueFx {

    @Override
    public int encode(String blockName, Object value, Map<String, Object> parsed) {
        // 2*count-1
        return 2 * (Integer) parsed.get("count") - 1;
    }

    @Override
    public int decode(String blockName, byte[] data, Map<String, Object> parsed, int bytePt, int bitPt) {
        // 2*count-1
        return 2 * (Integer) parsed.get("count") - 1;
    }

}

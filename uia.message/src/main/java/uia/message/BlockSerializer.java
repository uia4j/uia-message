package uia.message;

import uia.message.codec.BlockCodecException;
import uia.message.model.xml.BlockBaseType;

/**
 *
 * @author Kyle K. Lin
 *
 */
public interface BlockSerializer {

    /**
     * Accept to serialize specific block.
     *
     * @param name Block name.
     * @param blockType Block profile.
     * @param value POJO value.
     * @param parentValue If value is parent or not.
     * @throws BlockCodecException Raise if Serialization fails.
     */
    public void accept(String name, BlockBaseType blockType, Object value, boolean parentValue) throws BlockCodecException;
}

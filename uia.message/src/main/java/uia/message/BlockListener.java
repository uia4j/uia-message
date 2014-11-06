package uia.message;

/**
 * 
 * 
 * @author Kyle
 * 
 */
public interface BlockListener {

    /**
     * Raise when one BlockSeq is read.
     * 
     * @param name The name of BlockSeq.
     */
    public void seqTouched(String name, boolean begin, int offset);

    /**
     * Raise when one BlockList is read.
     * 
     * @param name The name of BlockList.
     */
    public void listTouched(String name, boolean begin, int offset);

    /**
     * Raise after serializing/deserializing value.
     * 
     * @param name The name of Block.
     * @param block
     */
    public void valueHandled(String name, BlockInfo block);
}

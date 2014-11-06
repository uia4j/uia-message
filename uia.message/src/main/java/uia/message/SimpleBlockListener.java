package uia.message;

import uia.utils.ByteUtils;

public class SimpleBlockListener implements BlockListener {

    private String fix;

    public SimpleBlockListener() {
        this.fix = "";
    }

    @Override
    public void valueHandled(String name, BlockInfo block) {
        if(block.getValue().getClass() == byte[].class) {
            System.out.println("BLK> " + this.fix + name + ":" +
                    ByteUtils.toHexString(block.getData()) + "(" + block.getBitLength() + "), " +
                    ByteUtils.toHexString((byte[])block.getValue()));
        }
        else {
            System.out.println("BLK> " + this.fix + name + ":" +
                    ByteUtils.toHexString(block.getData()) + "(" + block.getBitLength() + "), " +
                    block.getValue());
        }
    }

    @Override
    public void seqTouched(String name, boolean start, int offset) {
        if(start) {
            System.out.println("SEQ> " + this.fix + name);
            this.fix += "  ";
        } else {
            this.fix = this.fix.substring(0, this.fix.length() - 2);
        }
    }

    @Override
    public void listTouched(String name, boolean start, int offset) {
        if(start) {
            System.out.println("LST> " + this.fix + name);
            this.fix += "  ";
        } else {
            this.fix = this.fix.substring(0, this.fix.length() - 2);
        }
    }
}

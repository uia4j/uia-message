/*******************************************************************************
 * * Copyright (c) 2015, UIA
 * * All rights reserved.
 * * Redistribution and use in source and binary forms, with or without
 * * modification, are permitted provided that the following conditions are met:
 * *
 * * * Redistributions of source code must retain the above copyright
 * * notice, this list of conditions and the following disclaimer.
 * * * Redistributions in binary form must reproduce the above copyright
 * * notice, this list of conditions and the following disclaimer in the
 * * documentation and/or other materials provided with the distribution.
 * * * Neither the name of the {company name} nor the
 * * names of its contributors may be used to endorse or promote products
 * * derived from this software without specific prior written permission.
 * *
 * * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND ANY
 * * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package uia.message;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import uia.message.codec.BitCodec;
import uia.message.codec.BlockCodec;
import uia.message.codec.BlockCodecException;
import uia.message.codec.BooleanCodec;
import uia.message.codec.ByteArrayCodec;
import uia.message.codec.ByteCodec;
import uia.message.codec.ColorCodec;
import uia.message.codec.DateTimeCodec;
import uia.message.codec.DateTimeStringCodec;
import uia.message.codec.DoubleCodec;
import uia.message.codec.IntegerBCDCodec;
import uia.message.codec.IntegerBCDLSBCodec;
import uia.message.codec.IntegerCodec;
import uia.message.codec.IntegerLSBCodec;
import uia.message.codec.IntegerStringCodec;
import uia.message.codec.LongCodec;
import uia.message.codec.StringCodec;
import uia.message.fx.ValueFx;
import uia.message.model.xml.BlockBaseType;
import uia.message.model.xml.BlockCodecType;
import uia.message.model.xml.DataExType;
import uia.message.model.xml.FxType;
import uia.message.model.xml.MessageType;

/**
 * Data exchange factory. This factory can serialize and deserialize message depend on the XML defined by user.
 *
 *
 * @author Kyle
 */
public class DataExFactory {

    private static HashMap<String, DataExFactory> DATA_EX_FACTORY;

    private final DataExType dataEx;

    private HashMap<String, BlockBaseType> bodySpace;

    private HashMap<String, MessageType> messageSpace;

    private HashMap<String, BlockCodec<?>> codecSpace;

    private HashMap<String, ValueFx> fxSpace;

    private final ArrayList<BlockListener> listeners;

    static {
        DATA_EX_FACTORY = new HashMap<String, DataExFactory>();
    }

    /**
     * Register a XML defines structure of messages with a specific domain name.
     *
     * @param domain Domain name.
     * @param fileName XML file name.
     * @throws Exception Raise when file is wrong.
     */
    public static void register(String domain, String fileName) throws Exception {
        if (!DATA_EX_FACTORY.containsKey(domain)) {
            DATA_EX_FACTORY.put(domain, new DataExFactory(DataExCodec.decode(new File(fileName))));
        }
    }

    /**
     * Register a XML defines structure of messages with a specific domain name.
     *
     * @param domain Domain name.
     * @param stream XML file stream.
     * @throws Exception Raise when file is wrong.
     */
    public static void register(String domain, InputStream stream) throws Exception {
        if (!DATA_EX_FACTORY.containsKey(domain)) {
            DATA_EX_FACTORY.put(domain, new DataExFactory(DataExCodec.decode(stream)));
        }
    }

    /**
     * Register a XML defines structure of messages with a specific domain name.
     *
     * @param domain Domain name.
     * @param file XML file.
     * @throws Exception Raise when file is wrong.
     */
    public static void register(String domain, File file) throws Exception {
        if (!DATA_EX_FACTORY.containsKey(domain)) {
            DATA_EX_FACTORY.put(domain, new DataExFactory(DataExCodec.decode(file)));
        }
    }

    /**
     * Get factory with specific domain name.
     *
     * @param domain The domain name.
     * @return The data exchange factory. Null if this domain is not registered.
     */
    public static DataExFactory getFactory(String domain) {
        return DATA_EX_FACTORY.get(domain);
    }

    /**
     * Constructor.
     *
     * @param dataEx Data exchange information from XML.
     */
    private DataExFactory(DataExType dataEx) {
        this.dataEx = dataEx;
        this.listeners = new ArrayList<BlockListener>();
        loadBodySpace();
        loadMessageSpace();
        loadCodecSpace();
        loadFxSpace();
    }

    public Set<String> getFxList() {
        return this.fxSpace.keySet();
    }

    public Set<String> getMessageList() {
        return this.messageSpace.keySet();
    }

    /**
     * Add block listener.
     *
     * @param listener The listener.
     */
    public void addListener(BlockListener listener) {
        if (listener != null && !this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }
    }

    /**
     * Remove block listener.
     *
     * @param listener The listener.
     */
    public void removeListener(BlockListener listener) {
        if (listener != null) {
            this.listeners.remove(listener);
        }
    }

    /**
     * Clear all block listeners.
     */
    public void clearListeners() {
        this.listeners.clear();
    }

    /**
     * Serialize object of message to byte array.
     *
     * @param domain The domain name.
     * @param messageName The message name defined in XML.
     * @param value Object of message.
     * @return Serialize result or null if no domain.
     * @throws BlockCodecException Raise when serialize failed.
     */
    public static byte[] serialize(String domain, String messageName, Object value) throws BlockCodecException {
        DataExFactory factory = getFactory(domain);
        if (factory == null) {
            throw new NullPointerException("Domain:" + domain + " doesn't exist.");
        }
        return factory.createSerializer(messageName).write(value);
    }

    /**
     * Serialize object of message to byte array.
     *
     * @param domain The domain name.
     * @param messageName The message name defined in XML.
     * @param value Object of message.
     * @return Serialize result or null if no domain.
     * @throws BlockCodecException Raise when serialize failed.
     */
    public static byte[] serialize(String domain, String messageName, Object value, Map<String, Object> initialValues) throws BlockCodecException {
        DataExFactory factory = getFactory(domain);
        if (factory == null) {
            throw new NullPointerException("Domain:" + domain + " doesn't exist.");
        }
        return factory.createSerializer(messageName).write(value, initialValues);
    }

    /**
     * Deserialize byte array to object of message.
     *
     * @param domain The domain name.
     * @param messageName The message name defined in XML.
     * @param value byte array of message.
     * @return Deserialize result or null if no domain.
     * @throws BlockCodecException Raise when deserialize failed.
     */
    public static Object deserialize(String domain, String messageName, byte[] value) throws BlockCodecException {
        DataExFactory factory = getFactory(domain);
        if (factory == null) {
            throw new NullPointerException("Domain:" + domain + " doesn't exist.");
        }
        return factory.createDeserializer(messageName).read(value);
    }

    /**
     * Deserialize byte array to object of message.
     *
     * @param domain The domain name.
     * @param messageName The message name defined in XML.
     * @param value byte array of message.
     * @return Deserialize result or null if no domain.
     * @throws BlockCodecException Raise when deserialize failed.
     */
    public static Object deserialize(String domain, String messageName, byte[] value, Map<String, Object> initialValues) throws BlockCodecException {
        DataExFactory factory = getFactory(domain);
        if (factory == null) {
            throw new NullPointerException("Domain:" + domain + " doesn't exist.");
        }
        return factory.createDeserializer(messageName).read(value, initialValues);
    }

    /**
     * Serialize object of message to byte array.
     *
     * @param messageName The message name defined in XML.
     * @param value Object of message.
     * @return Serialize result.
     * @throws BlockCodecException Raise when serialize failed.
     */
    public byte[] serialize(String messageName, Object value) throws BlockCodecException {
        return createSerializer(messageName).write(value);
    }

    /**
     * Serialize object of message to byte array.
     *
     * @param messageName The message name defined in XML.
     * @param value Object of message.
     * @return Serialize result.
     * @throws BlockCodecException Raise when serialize failed.
     */
    public byte[] serialize(String messageName, Object value, Map<String, Object> initialValues) throws BlockCodecException {
        return createSerializer(messageName).write(value, initialValues);
    }

    /**
     * Deserialize byte array to object of message.
     *
     * @param messageName The message name defined in XML.
     * @param value byte array of message.
     * @return Deserialize result.
     * @throws BlockCodecException Raise when deserialize failed.
     */
    public Object deserialize(String messageName, byte[] value) throws BlockCodecException {
        return createDeserializer(messageName).read(value);
    }

    /**
     * Deserialize byte array to object of message.
     *
     * @param messageName The message name defined in XML.
     * @param value byte array of message.
     * @return Deserialize result.
     * @throws BlockCodecException Raise when deserialize failed.
     */
    public Object deserialize(String messageName, byte[] value, Map<String, Object> initialValues) throws BlockCodecException {
        return createDeserializer(messageName).read(value, initialValues);
    }

    /**
     * Create a deserializer with specific message name.
     *
     * @param messageName The message name defined in XML.
     * @return The deserializer. Can be null.
     */
    public MessageDeserializer createDeserializer(String messageName) {
        MessageType mt = this.messageSpace.get(messageName);
        if (mt == null) {
            throw new NullPointerException("Mesage:" + messageName + " doesn't exist.");
        }
        return new MessageDeserializer(this, mt);
    }

    /**
     * Create a serializer with specific message name.
     *
     * @param messageName The message name defined in XML.
     * @return The serializer. Can be null.
     */
    public MessageSerializer createSerializer(String messageName) {
        MessageType mt = this.messageSpace.get(messageName);
        if (mt == null) {
            throw new NullPointerException("Mesage:" + messageName + " doesn't exist.");
        }
        return new MessageSerializer(this, mt);
    }

    BlockBaseType getReferenceBlock(String blockName) {
        return this.bodySpace.get(blockName);
    }

    ValueFx getFx(String name) throws BlockCodecException {
        ValueFx fx = this.fxSpace.get(name);
        if (fx == null) {
            throw new BlockCodecException("fx name failed. " + name + " is not defined.");
        }
        return fx;
    }

    BlockCodec<?> getCodec(String dataType) throws BlockCodecException {
        BlockCodec<?> decoder = this.codecSpace.get(dataType);
        if (decoder == null) {
            throw new BlockCodecException("codec type failed. " + dataType + " is not defined.");
        }
        return decoder;
    }

    private void loadBodySpace() {
        this.bodySpace = new HashMap<String, BlockBaseType>();
        for (BlockBaseType block : this.dataEx.getBlockSpace().getBlockOrBlockListOrBlockSeq()) {
            this.bodySpace.put(block.getName(), block);
        }
    }

    private void loadMessageSpace() {
        this.messageSpace = new HashMap<String, MessageType>();
        for (MessageType mt : this.dataEx.getMessageSpace().getMessage()) {
            this.messageSpace.put(mt.getName(), mt);
        }
    }

    private void loadFxSpace() {
        this.fxSpace = new HashMap<String, ValueFx>();
        if (this.dataEx.getFxSpace() == null) {
            return;
        }
        for (FxType fx : this.dataEx.getFxSpace().getFx()) {
            try {
                this.fxSpace.put(
                        fx.getName(),
                        (ValueFx) Class.forName(fx.getDriver()).newInstance());
            }
            catch (Exception ex) {
            }
        }
    }

    private void loadCodecSpace() {
        this.codecSpace = new HashMap<String, BlockCodec<?>>();
        if (this.dataEx.getBlockCodecSpace() == null) {
            return;
        }
        for (BlockCodecType decoder : this.dataEx.getBlockCodecSpace().getBlockCodec()) {
            try {
                this.codecSpace.put(
                        decoder.getDataType(),
                        (BlockCodec<?>) Class.forName(decoder.getDriver()).newInstance());
            }
            catch (Exception ex) {
            }
        }

        if (!this.codecSpace.containsKey("Boolean")) {
            this.codecSpace.put("Boolean", new BooleanCodec());
        }

        if (!this.codecSpace.containsKey("Bit")) {
            this.codecSpace.put("Bit", new BitCodec());
        }

        if (!this.codecSpace.containsKey("Byte")) {
            this.codecSpace.put("Byte", new ByteCodec());
        }

        if (!this.codecSpace.containsKey("ByteArray")) {
            this.codecSpace.put("ByteArray", new ByteArrayCodec());
        }

        if (!this.codecSpace.containsKey("Double")) {
            this.codecSpace.put("Double", new DoubleCodec());
        }

        if (!this.codecSpace.containsKey("Float")) {
            this.codecSpace.put("Float", new DoubleCodec());
        }

        if (!this.codecSpace.containsKey("Bcd")) {
            this.codecSpace.put("Bcd", new IntegerBCDCodec());
        }

        if (!this.codecSpace.containsKey("BcdL")) {
            this.codecSpace.put("BcdL", new IntegerBCDLSBCodec());
        }

        if (!this.codecSpace.containsKey("Int")) {
            this.codecSpace.put("Int", new IntegerCodec(false));
        }

        if (!this.codecSpace.containsKey("UInt")) {
            this.codecSpace.put("UInt", new IntegerCodec(true));
        }

        if (!this.codecSpace.containsKey("IntL")) {
            this.codecSpace.put("IntL", new IntegerLSBCodec(false));
        }

        if (!this.codecSpace.containsKey("UIntL")) {
            this.codecSpace.put("UIntL", new IntegerLSBCodec(true));
        }

        if (!this.codecSpace.containsKey("IntString")) {
            this.codecSpace.put("IntString", new IntegerStringCodec());
        }

        if (!this.codecSpace.containsKey("Long")) {
            this.codecSpace.put("Long", new LongCodec());
        }

        if (!this.codecSpace.containsKey("String")) {
            this.codecSpace.put("String", new StringCodec());
        }

        if (!this.codecSpace.containsKey("DateTime")) {
            this.codecSpace.put("DateTime", new DateTimeCodec());
        }

        if (!this.codecSpace.containsKey("DateTimeString")) {
            this.codecSpace.put("DateTimeString", new DateTimeStringCodec());
        }

        if (!this.codecSpace.containsKey("Color")) {
            this.codecSpace.put("Color", new ColorCodec());
        }
    }

    void valueHandled(String name, BlockInfo block) {
        for (BlockListener l : this.listeners) {
            l.valueHandled(name, block);
        }
    }

    void listTouched(String name, boolean begin, int offset) {
        for (BlockListener l : this.listeners) {
            l.listTouched(name, begin, offset);
        }
    }

    void seqTouched(String name, boolean begin, int offset) {
        for (BlockListener l : this.listeners) {
            l.seqTouched(name, begin, offset);
        }
    }
}

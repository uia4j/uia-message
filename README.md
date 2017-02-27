UIA message
================

[![Build Status](https://travis-ci.org/gazer2kanlin/uia.message4j.svg?branch=master)](https://travis-ci.org/gazer2kanlin/uia.message4j)

Serialize & deserialize binary message depending on XML.

## Feature

* Use XML to define data structure.

* Map binary to POJO.

* Support custom data types.


## Example

More detail, check test cases.

XML test.xml:
```
<?xml version="1.0" encoding="UTF-8"?>
<DataEx>
    <BlockSpace />
    <MessageSpace>
        <Message>
            <Name>Case1</Name>
            <Desc>The first case named Case1.</Desc>
            <Body name="root" className="example.One">
                <Block name="name" size="10" dataType="String" />
                <Block name="sex" size="1" dataType="Int" />
                <Block name="birthday" size="8" dataType="DateTimeString">
                    <CodecPropSet>
                        <Prop name="format">yyyyMMdd</Prop>
                    </CodecPropSet>
                </Block>
            </Body>
        </Message>
    </MessageSpace>
    <BlockCodecSpace />
    <FxSpace />
</DataEx>
```

POJO:
```
package example;

import java.util.Date;

public class One {

    private String name;

    private int sex;

    private Date birthday;

    public One() {
        this.name = "Kyle";
        this.sex = 1;
        this.birthday = new Date();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
```

Binary data:

```
byte[] data = new byte[] {
        0x4a, 0x75, 0x64, 0x79, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20,
        0x00,
        0x31, 0x39, 0x39, 0x32, 0x30, 0x32, 0x31, 0x38
};
```

Code:
```
// register
DataExFactory.register("cases", ExampleTest.class.getResourceAsStream("test.xml"));

// serialize
One one = (One) DataExFactory.deserialize("cases", "Case1", data);
Assert.assertEquals("Judy", one.getName());
Assert.assertEquals(0, one.getSex(), 0);
Assert.assertEquals(698342400000L, one.getBirthday().getTime(), 0);

// deserialize
one.setName("Jack");
one.setSex(1);
byte[] result = DataExFactory.serialize("cases", "Case1", one);
Assert.assertArrayEquals(
        new byte[] {
                0x4a, 0x61, 0x63, 0x6b, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, // Jack
                0x01,                                                       // sex: 1
                0x31, 0x39, 0x39, 0x32, 0x30, 0x32, 0x31, 0x38              // birthday: 19920218
        },
        result);
```


## Maven
Because uia.message is distributed on jcenter, configure Maven local repository first.

settings.xml in .m2 directory:
```
<profiles>
    <profile>
        <repositories>
            <repository>
                <snapshots>
                    <enabled>false</enabled>
                </snapshots>
                <id>central</id>
                <name>bintray</name>
                <url>http://jcenter.bintray.com</url>
            </repository>
        </repositories>
        <pluginRepositories>
            <pluginRepository>
                <snapshots>
                    <enabled>false</enabled>
                </snapshots>
                <id>central</id>
                <name>bintray-plugins</name>
                <url>http://jcenter.bintray.com</url>
            </pluginRepository>
        </pluginRepositories>
        <id>bintray</id>
    </profile>
</profiles>
<activeProfiles>
    <activeProfile>bintray</activeProfile>
</activeProfiles>
```
pom.xml in your project:
```
<dependency>
    <groupId>uia</groupId>
    <artifactId>uia.message</artifactId>
    <version>0.5.0</version>
</dependency>
```

## Data Type

### default
Below are default data types uia.messaege supports.

* Boolean - boolean

* Bit - boolean

* Byte - byte (1 = true, 0 = false)

* ByteArray - byte[ ]

* Double - double

* Float - float

* Bcd - int (Binary-coded decimal) ex: 256 = 0x02,0x56

* BcdL - int (Binary-coded decimal, low byte first) ex: 256 = 0x56,0x02

* Int - int

* IntL - int (low byte first) ex: 256 = 0x00,0x01

* UInt - int (Unsigned integer)

* UIntL - int. (Unsigned integer, low byte first) ex: 256 = 0x00,0x01

* IntString - int, ex 12 = 0x31,0x32

* Long - long

* String - String

* DateTime - java.util.Date

* DateTimeString - java.util.Date, Default format: yyyyMMddHHmmss

* Color - java.awt.Color, ex: Color.red = 0xff,0x00,0x00

### customize
Use ```<BlockCodecSpace />``` to define new data types. Classes of new types have to implement the interface ```uia.message.codec.BlockCodec```.

## Dependency Libraries

* [uia.utils](https://github.com/gazer2kanlin/uia.utils4j) - UIA common utilities
* [simpleXML](http://simple.sourceforge.net/) - XML serialization



## Copyright and License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

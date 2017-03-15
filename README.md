UIA message
================

[![Build Status](https://travis-ci.org/gazer2kanlin/uia.message4j.svg?branch=0.5.0.0)](https://travis-ci.org/gazer2kanlin/uia.message4j)
[![Codecov](https://img.shields.io/codecov/c/github/gazer2kanlin/uia.message4j.svg)](https://codecov.io/gh/gazer2kanlin/uia.message4j)
[![License](https://img.shields.io/github/license/gazer2kanlin/uia.message4j.svg)](LICENSE)

Serialize & deserialize binary message depending on XML.

## Feature

* Use XML to define data structure.
* Map binary to POJO.
* Support custom data types.


## Example

This test case exists in example package of test source code.

__Case1__ defines binary structure of example.One class which has 3 properties:
* __name__ - String, max length is 10.
* __sex__ - Int, 0: female, 1:male.
* __birthdy__ - DataString, format is yyyyMMdd.

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

POJO One.java:
```
package example;

import java.text.SimpleDateFormat;
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

    public String getBirthday(String fmt) {
        return new SimpleDateFormat(fmt).format(this.birthday);

    }
}
```

Depending on definition of __Case1__ in test.xml, the binary will be:

```
byte[] data = new byte[] {
        0x4a, 0x75, 0x64, 0x79, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, // name
        0x00,                                                       // sex
        0x31, 0x39, 0x39, 0x32, 0x30, 0x32, 0x31, 0x38              // birthday
};
```

Use DataExFactory to serialize and deserialize:

* Domain - cases
* Message - Case1

```
// register
DataExFactory.register("cases", ExampleTest.class.getResourceAsStream("test.xml"));

// deserialize
byte[] data = new byte[] {
        0x4a, 0x75, 0x64, 0x79, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, // name
        0x00,                                                       // sex
        0x31, 0x39, 0x39, 0x32, 0x30, 0x32, 0x31, 0x38              // birthday
};
One one = (One) DataExFactory.deserialize("cases", "Case1", data);
Assert.assertEquals("Judy", one.getName());
Assert.assertEquals(0, one.getSex(), 0);
Assert.assertEquals("19920218", one.getBirthday("yyyyMMdd"));

// serialize
one.setName("Jack");
one.setSex(1);
byte[] result = DataExFactory.serialize("cases", "Case1", one);
Assert.assertArrayEquals(
        new byte[] {
                0x4a, 0x61, 0x63, 0x6b, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, // name: Jack
                0x01,                                                       // sex: 1
                0x31, 0x39, 0x39, 0x32, 0x30, 0x32, 0x31, 0x38              // birthday: 19920218
        },
        result);
```

More test cases, check test source code.


## Maven
Because uia.message uses [uia.utils](https://github.com/gazer2kanlin/uia.utils4j) deployed on jcenter, configure local Maven __settings.xml__ first.

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

* __Boolean__ - boolean

* __Bit__ - boolean (1 = true, 0 = false)

* __Byte__ - byte

* __ByteArray__ - byte[ ]

* __Double__ - double

* __Float__ - float

* __Bcd__ - int (Binary-coded decimal) ex: 256 = 0x02,0x56

* __BcdL__ - int (Binary-coded decimal, LSB) ex: 256 = 0x56,0x02

* __Int__ - int

* __IntL__ - int (low byte first) ex: 256 = 0x00,0x01

* __UInt__ - int (Unsigned integer)

* __UIntL__ - int. (Unsigned integer, LSB) ex: 256 = 0x00,0x01

* __IntString__ - int, ex 12 = 0x31,0x32

* __Long__ - long

* __String__ - String

* __DateTime__ - java.util.Date

* __DateTimeString__ - java.util.Date, Default format: yyyyMMddHHmmss

* __Color__ - java.awt.Color, ex: Color.red = 0xff,0x00,0x00

### customize
Use ```<BlockCodecSpace />``` to define new data types. Classes of new types have to implement the interface __uia.message.codec.BlockCodec__.

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

/*******************************************************************************
 * Copyright 2017 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.message;

import java.util.ArrayList;

public class Rcv3 {

    private int count;

    private ArrayList<Integer> value1;

    private ArrayList<String> value2;

    private ArrayList<Value3> value3;

    public Rcv3() {
        this.value1 = new ArrayList<Integer>();
        this.value2 = new ArrayList<String>();
        this.value3 = new ArrayList<Value3>();
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Integer> getValue1() {
        return this.value1;
    }

    public void setValue1(ArrayList<Integer> value1) {
        this.value1 = value1;
    }

    public ArrayList<String> getValue2() {
        return this.value2;
    }

    public void setValue2(ArrayList<String> value2) {
        this.value2 = value2;
    }

    public ArrayList<Value3> getValue3() {
        return this.value3;
    }

    public void setValue3(ArrayList<Value3> value3) {
        this.value3 = value3;
    }

    public static class Value3 {

        private String name;

        private int id;

        public Value3() {

        }

        public Value3(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}

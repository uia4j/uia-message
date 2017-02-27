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

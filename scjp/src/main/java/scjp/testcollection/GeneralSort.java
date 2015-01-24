/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scjp.testcollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author justinwu
 */
public class GeneralSort {

    static Comparator comparator = new Comparator() {

        public int compare(Object o1, Object o2) {

            String s1 = (String) o1;
            String s2 = ((String) o2);
            return s1.compareToIgnoreCase(s2);
        }
        };

    public static void main(String[] args) {

        ArrayList<String> list = list = new ArrayList<String>();
        list.add("abc");
        list.add("bca");
        list.add("cba");
        list.add("Abcd");

        Collections.sort(list, comparator);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}

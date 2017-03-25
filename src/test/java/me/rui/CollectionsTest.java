package me.rui;

import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * Created by cr on 2017/3/9.
 */
public class CollectionsTest {
    class Food {
        String name;

        public Food(String name) {
            this.name = name;
        }
    }

    @Test
    public void vectorTest() {
        int[] a = new int[] {1,2,3};
        int length = a.length;
    }
    @Test
    public void collectionTest() {
        Collections collections;
        Collection collection;
    }
    @Test
    public void listTest() {
        List intList =  Arrays.asList(new int[] {1,2,3});
        intList.add(1);
        ArrayList arrayList = new ArrayList();
        Food chess = new Food("Chess");
        arrayList.add(chess);
        arrayList.contains(chess);
        LinkedList linkedList = new LinkedList();
    }

    @Test
    public void hashMapTest(){
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("1", 1);
        hashMap.put("1", 2);
        Map<String, Integer> cpHashMap = new HashMap<String, Integer>(hashMap);
        Map<String, Integer> clHashMap = (Map<String, Integer>) hashMap.clone();
        clHashMap.put("1",2);
//        System.out.println(hashMap.get("1"));
        System.out.println(hashMap.get("1"));
    }

    @Test
    public void hashMapKeyTest() {
        String f1 = "f1";
        String f2 = "f2";
        Key k1 = new Key(f1, f2);
        Key k2 = new Key(f1, f2);
        HashMap<Key, Key> map = new HashMap<Key, Key>();
        map.put(k1,k1);
        System.out.println(map.get(k2));
        System.out.println(k1.hashCode());
        System.out.println(k2.hashCode());
        System.out.println(k1.equals(k2));
    }
    @Test
    public void mapTest() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
    }



    class Key {
        String f1;
        String f2;

        public Key(String f1, String f2) {
            this.f1 = f1;
            this.f2 = f2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (f1 != null ? !f1.equals(key.f1) : key.f1 != null) return false;
            return f2 != null ? f2.equals(key.f2) : key.f2 == null;

        }
    }

    @Test
    public void test2() {
        int count = 0;
        for (int i =0; i<=999999; i++) {
            count += haha(i);
        }
        System.out.print(count);
    }

    public static int haha(int num) {
        String str = String.valueOf(num);
        char[] chars = str.toCharArray();
        int count = 0;
        for (char c : chars) {
            if (c == '3') count++;
        }
        return count;
    }


    @Test
    public void test22222() {
        String[] capa = new String[]{"012345","012345","012345","012345","012345","012345"};
        System.out.println(handle(capa, 6));
    }

    public static int handle(String capability[], int workerNum) {
        Map<Integer, List<Integer>> remainMap = new HashMap<>();
        for (int i = 0; i< 6; i++) {
            remainMap.put(i, new ArrayList<>());
        }
        for (int j = 0; j < workerNum; j ++) {
            String cap = capability[j];
            char[] caps = cap.toCharArray();
            for (char c : caps) {
                List list = remainMap.get(Integer.valueOf(String.valueOf(c)));
                list.add(j);
            }
        }
        return handleInternal(remainMap, 0);
    }

    public static int handleInternal(Map<Integer, List<Integer>> remain, int jobIndex) {
        if (jobIndex > 5) {
            return 1;
        }
        List<Integer> list = remain.get(jobIndex);
        if (list.isEmpty()) return 0;
        int count = 0;
        for (Integer workerIndex : list) {
            List<Integer> newList = new ArrayList<Integer>(list);
            Map<Integer, List<Integer>> newRemain = new HashMap<>(remain);
            newList.remove(workerIndex);
            newRemain.put(jobIndex, newList);
            count += handleInternal(newRemain, jobIndex+1);
        }
        return count;
    }
}

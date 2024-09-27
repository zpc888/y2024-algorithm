package assist;

import java.util.TreeSet;

public class CustomObjectAndSort {
    public static class CustomObject {
        public String name;
        public int age;

        public CustomObject(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return String.format("name=%s, age=%d", name, age);
        }
    }

    public static void main(String[] args) {
        CustomObject[] data = new CustomObject[]{
                new CustomObject("Alice", 20),
                new CustomObject("Bob", 30),
                new CustomObject("Cathy", 25)
        };
        TreeSet<CustomObject> treeSet = new TreeSet<>((a, b) -> a.age - b.age);
        for (CustomObject obj : data) {
            treeSet.add(obj);
        }
        System.out.println("Original array:");
        for (CustomObject obj : data) {
            System.out.println(obj);
        }
        System.out.println();
        System.out.println("Sorted by age:");
        for (CustomObject obj : treeSet) {
            System.out.println(obj);
        }
        System.out.println();
        System.out.println("=====================================");
        System.out.println();
        System.out.println("after modifying Cathy to 40:");
        data[2].age = 40;           // changing Cathy's age won't affect the sorted set
        System.out.println("Original array:");
        for (CustomObject obj : data) {
            System.out.println(obj);
        }
        System.out.println();
        System.out.println("From sorted set before removing/add (Cathy's order is wrong now):");
        for (CustomObject obj : treeSet) {
            System.out.println(obj);
        }
        System.out.println();
        System.out.println("From sorted set after removing/adding:");
        treeSet.remove(data[2]);    // removing Cathy from the sorted set
        treeSet.add(data[2]);       // add Cathy to the sorted set to update the sorted order
        for (CustomObject obj : treeSet) {
            System.out.println(obj);
        }
    }
}

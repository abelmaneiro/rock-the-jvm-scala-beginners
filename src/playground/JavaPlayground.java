package playground;

public class JavaPlayground {

    public static void main(String[] args) {
        System.out.println("Hello, java");
        System.out.println("Person eye " + Person.N_EYES);
    }
}

class Person {
    public static final int N_EYES = 2;  // Java has class level (static final) functionality but not Scala instead it has Object
}

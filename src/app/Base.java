package app;

public class Base {
    String val;

    public Base(String val){
        this.val = val;
    }

    @Override
    public String toString() {
        return "Base{" +
                "val='" + val + '\'' +
                '}';
    }
}

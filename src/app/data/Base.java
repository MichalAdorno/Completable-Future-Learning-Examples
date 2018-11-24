package app.data;

public abstract class Base {
    public String val;

    public Base(String val){
        this.val = val;
    }

    public Base(int nr){
        this.val = String.format("[cf %d]", nr);
    }
    @Override
    public String toString() {
        return "Base{" +
                "val='" + val + '\'' +
                '}';
    }

}

package app.data;

public class B extends Base {
    public B(String val){
        super(val);
    }
    public B(int nr){
        super(nr);
    }
    public static B reducer(B x, B y){
        return new B(x.val + y.val);
    }
}

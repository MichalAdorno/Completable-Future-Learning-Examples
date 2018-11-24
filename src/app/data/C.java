package app.data;

public class C extends Base{
    public C(String val){
        super(val);
    }
    public C(int nr){
        super(nr);
    }
    public static C reducer(C x, C y){
        return new C(x.val + y.val);
    }
}

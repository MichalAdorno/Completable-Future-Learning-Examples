package app.task;

import app.data.Base;
import app.util.Util;

public class Task {

    public static <T extends Base> T doTask(Long timeout, T t){
        try{
            Thread.sleep(timeout);
            Util.printThreadDiagnostics(timeout, t.val);
            return t;
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

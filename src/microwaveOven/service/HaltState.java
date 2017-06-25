package microwaveOven.service;

import microwaveOven.util.Logger;
import java.time.LocalTime;

/**
 * Created by abhineetsharma on 6/19/17.
 */
public class HaltState implements MicrowaveStateI {

    MicrowaveContext context;
    private String className;

    public HaltState(MicrowaveContext context){
        this.context = context;
        this.className = this.getClass().getSimpleName();
    }

    @Override
    public void setOrStart() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        Logger.log(str);
        CookingTime currentTimeObject = context.getCookingTimeObject();
        if(null != currentTimeObject )
        {
            currentTimeObject.time = LocalTime.now().plusSeconds(currentTimeObject.cookingTime);
            context.setCookingTimeObject(currentTimeObject);
            context.setState(context.getCookingState());
        }
    }

    @Override
    public void cancelOrStop() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        Logger.log(str);
        context.setCookingTimeObject(null);
        context.setState(context.getClockDisplayState());
    }

    @Override
    public void setClock() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        Logger.log(str);
        String msg =String.format("%s %s", getMethodName()," disabled");
        context.storeStringToResult(msg);
    }

    @Override
    public void pressKey(int num) {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        Logger.log(str);
        String msg =String.format("%s %s", getMethodName()," disabled");
        context.storeStringToResult(msg);
    }
    public String getClassName() {
        return className;
    }

    String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}

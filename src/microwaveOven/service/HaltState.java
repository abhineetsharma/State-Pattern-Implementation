package microwaveOven.service;

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
        context.storeNewResult(str);
        CookingTimeObject currentTimeObject = context.getCookingTimeObject();
        if(null != currentTimeObject )
        {
            currentTimeObject.time.plusSeconds(currentTimeObject.cookingTime);
            context.setCookingTimeObject(currentTimeObject);
            context.setState(context.getCookingState());
        }
    }

    @Override
    public void cancelOrStop() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeNewResult(str);
        context.setCookingTimeObject(null);
        context.setState(context.getInitialState());
    }

    @Override
    public void setClock() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeNewResult(str);
        String msg = "set Or Start disabled";
        context.storeNewResult(msg);
    }

    @Override
    public void pressKey(int num) {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeNewResult(str);
        String msg = "set Or Start disabled";
        context.storeNewResult(msg);
    }
    public String getClassName() {
        return className;
    }

    String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}

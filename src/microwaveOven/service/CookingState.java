package microwaveOven.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by abhineetsharma on 6/19/17.
 */
public class CookingState implements MicrowaveStateI {

    MicrowaveContext context;
    private String className;


    public CookingState(MicrowaveContext context) {
        this.context = context;
        this.className = this.getClass().getSimpleName();
    }

    @Override
    public void setOrStart() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeStringToResult(str);
        String msg = "set Or Start disabled";
        context.storeStringToResult(msg);
        updateTimeObject();
    }

    @Override
    public void cancelOrStop() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeStringToResult(str);
        context.setState(context.getHaltState());
        updateTimeObject();
    }

    @Override
    public void setClock() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeStringToResult(str);
        String msg = "set Clock disabled";
        context.storeStringToResult(msg);
        updateTimeObject();
    }

    @Override
    public void pressKey(int num) {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeStringToResult(str);
        String msg = "press Key disabled";
        context.storeStringToResult(msg);
        updateTimeObject();
    }

    private void updateTimeObject() {
        CookingTime timeObject = null;
        CookingTime currentTimeObject = context.getCookingTimeObject();
        if (null != currentTimeObject) {
            long secPassed = ChronoUnit.SECONDS.between(LocalTime.now(),currentTimeObject.time);
            if (secPassed > 0) {
                currentTimeObject.cookingTime = (int) secPassed;
                currentTimeObject.time.plusSeconds(currentTimeObject.cookingTime);
                timeObject = currentTimeObject;
            }
            context.setCookingTimeObject(timeObject);
            if(timeObject == null){
                String msg = "Cooking Done";
                context.storeStringToResult(msg);
                context.setState(context.getInitialState());
            }
        }
    }
    public String getClassName() {
        return className;
    }

    String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}

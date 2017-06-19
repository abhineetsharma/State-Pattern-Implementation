package microwaveOven.service;
import microwaveOven.util.Logger;

import java.time.LocalTime;

/**
 * Created by abhineetsharma on 6/18/17.
 */
public class InitialState implements MicrowaveStateI {

    MicrowaveContext context;
    private String className;
    private boolean newFlag = true;
    public InitialState(MicrowaveContext context){
        this.context = context;
        this.className = this.getClass().getSimpleName();
    }

    private StringBuilder sbr;
    @Override
    public void setOrStart() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeNewResult(str);
        if(sbr.length()>0){
            try{
                int num = Integer.parseInt(sbr.toString());
                newFlag = true;
                CookingTimeObject timeObject = new CookingTimeObject();
                timeObject.cookingTime =  num;
                timeObject.time = LocalTime.now();
                context.setCookingTimeObject(timeObject);
                context.setState(context.getCookingState());
            }catch (NumberFormatException e){
                Logger.storeNewResult(e.getStackTrace());
                System.exit(0);
            }
        }
        else{
            String msg = "Please press any key 0 to 9";
            context.storeNewResult(msg);
        }
    }

    @Override
    public void cancelOrStop() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeNewResult(str);
        String msg = "Cancel disabled";
        context.storeNewResult(msg);
    }

    @Override
    public void setClock() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeNewResult(str);
        context.setState(context.getClockSetState());
    }

    @Override
    public void pressKey(int num) {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeNewResult(str);
        String msg = String.format("");
        context.storeNewResult(msg);
        if(newFlag){
            sbr = new StringBuilder();
            newFlag = false;
        }
        sbr.append(num);
    }
    public String getClassName() {
        return className;
    }

    String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}

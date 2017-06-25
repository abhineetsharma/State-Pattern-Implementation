package microwaveOven.service;

import microwaveOven.util.Logger;
import java.time.LocalTime;

/**
 * Created by abhineetsharma on 6/18/17.
 */
public class ClockDisplayState implements MicrowaveStateI {

    MicrowaveContext context;
    private String className;
    public ClockDisplayState(MicrowaveContext context){
        this.context = context;
        this.className = this.getClass().getSimpleName();
    }

    private StringBuilder sbr;
    @Override
    public void setOrStart() {
        String str = String.format("State : %s Function name : %s",getClassName() ,getMethodName());
        Logger.log(str);
        if(null != sbr && sbr.length()>0){
            try{
                int num = Integer.parseInt(sbr.toString());
                sbr = null;

                CookingTime timeObject = new CookingTime();
                timeObject.cookingTime =  num;
                timeObject.time = LocalTime.now().plusSeconds(num);
                context.setCookingTimeObject(timeObject);


                context.setState(context.getCookingState());


            }catch (NumberFormatException e){
                Logger.log(e.toString());
                System.exit(1);
            }
        }
        else{
            String msg = "Please press any key 0 to 9, to set the time to cook";
            context.storeStringToResult(msg);
        }
    }

    @Override
    public void cancelOrStop() {
        String str = String.format("State : %s Function name : %s",getClassName() ,getMethodName());
        Logger.log(str);
        String msg =String.format("%s %s", getMethodName()," disabled");
        context.storeStringToResult(msg);
    }

    @Override
    public void setClock() {
        String str = String.format("State : %s Function name : %s",getClassName() ,getMethodName());
        Logger.log(str);
        context.setState(context.getClockSetState());
    }

    @Override
    public void pressKey(int num) {
        String str = String.format("State : %s Function name : %s",getClassName() ,getMethodName());
        Logger.log(str);
        if(sbr == null){
            sbr = new StringBuilder();
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

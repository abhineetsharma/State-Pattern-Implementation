package microwaveOven.service;

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
        context.storeStringToResult(str);
        if(null != sbr && sbr.length()>0){
            try{
                int num = Integer.parseInt(sbr.toString());
                sbr = null;
                newFlag = true;
                CookingTime timeObject = new CookingTime();
                timeObject.cookingTime =  num;
                timeObject.time = LocalTime.now().plusSeconds(num);
                context.setCookingTimeObject(timeObject);


                context.setState(context.getCookingState());


            }catch (NumberFormatException e){
                e.printStackTrace();
                System.exit(0);
            }
        }
        else{
            String msg = "Please press any key 0 to 9";
            context.storeStringToResult(msg);
        }
    }

    @Override
    public void cancelOrStop() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeStringToResult(str);
        String msg = "Cancel disabled";
        context.storeStringToResult(msg);
    }

    @Override
    public void setClock() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeStringToResult(str);
        context.setState(context.getClockSetState());
    }

    @Override
    public void pressKey(int num) {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeStringToResult(str);
        String msg = String.format("");
        //context.storeStringToResult(msg);
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

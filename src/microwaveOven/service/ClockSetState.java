package microwaveOven.service;

import microwaveOven.util.Logger;

import java.time.LocalTime;

/**
 * Created by abhineetsharma on 6/19/17.
 */
public class ClockSetState implements MicrowaveStateI {

    MicrowaveContext context;
    private String className;
    private StringBuilder sbr;
    private boolean newFlag = true;

    public ClockSetState(MicrowaveContext context){
        this.context = context;
        this.className = this.getClass().getSimpleName();
    }

    @Override
    public void setOrStart() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeNewResult(str);
        if(validatePressKey()) {
            try {
                String minStr = sbr.substring(sbr.length(),sbr.length()+1);
                int min = Integer.parseInt(minStr);

                String hrStr = sbr.substring(0,sbr.length()-1);
                int hr = Integer.parseInt(hrStr);
                LocalTime lt = LocalTime.of(hr,min);
                newFlag = true;

                context.setState(context.getInitialState());
            } catch (NumberFormatException e) {
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
        String msg = "cancel or Stop disabled";
        context.storeNewResult(msg);
    }

    @Override
    public void setClock() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeNewResult(str);
        String msg = "setClock disabled";
        context.storeNewResult(msg);
    }

    @Override
    public void pressKey(int num) {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeNewResult(str);
        if(newFlag){
            sbr = new StringBuilder();
            newFlag = false;
        }
        sbr.append(num);
    }

    public boolean validatePressKey(){
        if(sbr == null) return false;
        else if(sbr.length()<3) return false;
        else if(sbr.length()<5)return true;
        else if(sbr.length()>=5){
            while(sbr.length()>4)
                sbr.deleteCharAt(0);
            return true;
        }
        else return false;
    }

    public String getClassName() {
        return className;
    }

    String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}

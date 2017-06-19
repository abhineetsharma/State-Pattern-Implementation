package microwaveOven.service;

import microwaveOven.util.Logger;

import java.time.LocalTime;

/**
 * Created by abhineetsharma on 6/19/17.
 */
public class clockSetState implements MicrowaveStateI {

    MicrowaveContext context;

    private StringBuilder sbr;
    private boolean newFlag = true;

    public clockSetState(MicrowaveContext context){
        this.context = context;
    }

    @Override
    public void setOrStart() {
        if(validatePressKey()) {
            try {
                int num = Integer.parseInt(sbr.toString());
                newFlag = true;
                context.setTime(LocalTime.now());
                context.setState(context.getInitialState());
            } catch (NumberFormatException e) {
                Logger.storeNewResult(e.getStackTrace());
                System.exit(0);
            }
        }
        else{
            String msg = "Please press any key 0 to 9";
        }
    }

    @Override
    public void cancelOrStop() {
        String msg = "cancel or Stop disabled";
    }

    @Override
    public void setClock() {
        String msg = "setClock disabled";
    }

    @Override
    public void pressKey(int num) {
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
}

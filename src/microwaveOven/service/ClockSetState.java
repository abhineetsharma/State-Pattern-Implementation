package microwaveOven.service;

import microwaveOven.util.Logger;

import java.time.LocalTime;

/**
 * Created by abhineetsharma on 6/19/17.
 */
public class ClockSetState implements MicrowaveStateI {

    MicrowaveContext context;

    private StringBuilder sbr;
    private boolean newFlag = true;

    public ClockSetState(MicrowaveContext context){
        this.context = context;
    }

    @Override
    public void setOrStart() {
        if(validatePressKey()) {
            try {
                String minStr = sbr.substring(sbr.length(),sbr.length()+1);
                int min = Integer.parseInt(minStr);

                String hrStr = sbr.substring(0,sbr.length()-1);
                int hr = Integer.parseInt(hrStr);
                LocalTime lt = LocalTime.of(hr,min);
                newFlag = true;
                context.setTime(lt);
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

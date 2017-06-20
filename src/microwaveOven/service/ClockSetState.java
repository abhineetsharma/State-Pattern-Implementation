package microwaveOven.service;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Created by abhineetsharma on 6/19/17.
 */
public class ClockSetState implements MicrowaveStateI {

    MicrowaveContext context;
    private String className;
    private StringBuilder sbr;

    public ClockSetState(MicrowaveContext context){
        this.context = context;
        this.className = this.getClass().getSimpleName();
    }

    @Override
    public void setOrStart() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeStringToResult(str);
        if(validatePressKey()) {
            try {
                String minStr = sbr.substring(sbr.length()-2,sbr.length());
                int min = Integer.parseInt(minStr);

                String hrStr = sbr.substring(0,sbr.length()-2);
                int hr = Integer.parseInt(hrStr);
                sbr = null;
                LocalTime lt = LocalTime.of(hr,min);
                String msg = String.format("The display clock set to %s ",lt.format(DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)).toString());
                context.storeStringToResult(msg);
                DisplayTime dt = new DisplayTime();
                dt.localTime = LocalTime.now();
                dt.displayTime = lt;
                context.setDisplayTimeObject(dt);

                context.setState(context.getInitialState());
            } catch (NumberFormatException e) {
//                Logger.storeStringToResult(e.getMessage().toString());
                e.printStackTrace();
                System.exit(0);
            }
            catch (DateTimeException e){
//                Logger.storeStringToResult(e.getMessage().toString());
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
        String msg = "cancel or Stop disabled";
        context.storeStringToResult(msg);
    }

    @Override
    public void setClock() {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeStringToResult(str);
        String msg = "setClock disabled";
        context.storeStringToResult(msg);
    }

    @Override
    public void pressKey(int num) {
        String str = String.format("State : %s, Function name : %s",getClassName() ,getMethodName());
        context.storeStringToResult(str);
        if(sbr == null){
            sbr = new StringBuilder();
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

    String getClassName() {
        return className;
    }

    String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

}

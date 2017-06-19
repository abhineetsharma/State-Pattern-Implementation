package microwaveOven.service;
import microwaveOven.util.Logger;
/**
 * Created by abhineetsharma on 6/18/17.
 */
public class InitialState implements MicrowaveStateI {

    MicrowaveContext context;
    private boolean newFlag = true;
    public InitialState(MicrowaveContext context){
        this.context = context;
    }

    private StringBuilder sbr;
    @Override
    public void setOrStart() {
        if(sbr.length()>0){
            try{
                int num = Integer.parseInt(sbr.toString());
                newFlag = true;
                context.setCookingTime(num);
                context.setState(context.getCookingState());
            }catch (NumberFormatException e){
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
        String msg = "Cancel disabled";
    }

    @Override
    public void setClock() {
        context.setState(context.getClockSetState());
    }

    @Override
    public void pressKey(int num) {
        if(newFlag){
            sbr = new StringBuilder();
            newFlag = false;
        }
        sbr.append(num);
    }
}

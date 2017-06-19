package microwaveOven.service;

/**
 * Created by abhineetsharma on 6/19/17.
 */
public class HaltState implements MicrowaveStateI {

    MicrowaveContext context;

    public HaltState(MicrowaveContext context){
        this.context = context;
    }

    @Override
    public void setOrStart() {
        context.setState(context.getCookingState());
    }

    @Override
    public void cancelOrStop() {
        context.setCookingTime(0);
        context.setState(context.getInitialState());
    }

    @Override
    public void setClock() {
        String msg = "set Or Start disabled";
    }

    @Override
    public void pressKey(int num) {
        String msg = "set Or Start disabled";
    }
}

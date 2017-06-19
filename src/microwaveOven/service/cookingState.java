package microwaveOven.service;

/**
 * Created by abhineetsharma on 6/19/17.
 */
public class cookingState implements MicrowaveStateI {

    MicrowaveContext context;

    private boolean newFlag = true;

    StringBuilder sbr ;

    public cookingState(MicrowaveContext context){
        this.context = context;
    }

    @Override
    public void setOrStart() {
        String msg = "set Or Start disabled";
    }

    @Override
    public void cancelOrStop() {
        context.setState(context.getHaltState());
    }

    @Override
    public void setClock() {
        String msg = "set Clock disabled";
    }

    @Override
    public void pressKey(int num) {
        String msg = "press Key disabled";
    }


}

package microwaveOven.service;

/**
 * Created by abhineetsharma on 6/17/17.
 */
public class MicrowaveContext {
    private MicrowaveStateI state;


    public MicrowaveStateI getState() {
        return state;
    }

    public void setState(MicrowaveStateI state) {
        this.state = state;
    }
}

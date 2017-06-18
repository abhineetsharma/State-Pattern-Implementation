package microwaveOven.service;

/**
 * Created by abhineetsharma on 6/17/17.
 */
public interface MicrowaveStateI {
    void setOrStart();
    void cancelOrStop();
    void setClock();
    void pressKey();
}

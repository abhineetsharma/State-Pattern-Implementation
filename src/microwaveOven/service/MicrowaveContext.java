package microwaveOven.service;


import microwaveOven.util.Logger;
import microwaveOven.util.Results;

import java.time.LocalTime;

/**
 * Created by abhineetsharma on 6/17/17.
 */
public class MicrowaveContext implements MicrowaveStateI {

    public MicrowaveStateI getState() {
        return state;
    }

    public void setState(MicrowaveStateI state) {
        this.state = state;
    }

    private MicrowaveStateI initialState;
    private MicrowaveStateI cookingState;
    private MicrowaveStateI haltState;
    private MicrowaveStateI clockSetState;

    MicrowaveStateI state;
    private CookingTimeObject cookingTimeObject;

    private Results results;

    public MicrowaveContext(Results results) {
        this.initialState = new InitialState(this);
        this.cookingState = new CookingState(this);
        this.haltState = new HaltState(this);
        this.clockSetState = new ClockSetState(this);
        this.setCookingTimeObject(new CookingTimeObject());
        this.state = getInitialState();
        this.results = results;
    }




    public void action(String selector) {
        int num = isStrigAInteger(selector);
        if(num > -1)
            selector = "keyPress";
        switch (selector) {
            case "setOrStart":
                setOrStart();
                break;
            case "cancelOrStop":
                cancelOrStop();
                break;
            case "setClock":
                setClock();
                break;
            case "keyPress":
                pressKey(num);
                break;
        }

    }

    @Override
    public void setOrStart() {
        state.setOrStart();
    }

    @Override
    public void cancelOrStop() {
        state.cancelOrStop();
    }

    @Override
    public void setClock() {
        state.setClock();
    }

    @Override
    public void pressKey(int num) {
        state.pressKey(num);
    }


    public MicrowaveStateI getInitialState() {
        return initialState;
    }


    public MicrowaveStateI getCookingState() {
        return cookingState;
    }


    public MicrowaveStateI getHaltState() {
        return haltState;
    }


    public MicrowaveStateI getClockSetState() {
        return clockSetState;
    }


    public CookingTimeObject getCookingTimeObject() {
        return cookingTimeObject;
    }

    void setCookingTimeObject(CookingTimeObject cookingTimeObject) {
        this.cookingTimeObject = cookingTimeObject;
    }

    int isStrigAInteger(String str){
        str = str.trim();

        try{
            int num = Integer.parseInt(str);
            return num;
        }
        catch (NumberFormatException ex){
            Logger.storeNewResult(ex.getStackTrace());
        }

        return -1;
    }

    void storeNewResult(Object obj){
        results.storeNewResult(obj);
    }


}
class CookingTimeObject{
    int cookingTime;
    LocalTime time;
}

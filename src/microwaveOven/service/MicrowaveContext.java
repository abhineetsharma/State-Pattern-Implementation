package microwaveOven.service;


import microwaveOven.util.Results;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

/**
 * Created by abhineetsharma on 6/17/17.
 */
public class MicrowaveContext implements MicrowaveStateI {

    public void setState(MicrowaveStateI state) {
        results.storeNewResult(String.format("State changed to %s \n%s", state.getClass().getSimpleName(), generate72Dash()));
        this.state = state;
    }

    private MicrowaveStateI initialState;
    private MicrowaveStateI cookingState;
    private MicrowaveStateI haltState;
    private MicrowaveStateI clockSetState;

    MicrowaveStateI state;
    private CookingTime cookingTimeObject;
    private DisplayTime displayTimeObject;

    private Results results;

    public MicrowaveContext(Results results) {
        this.initialState = new InitialState(this);
        this.cookingState = new CookingState(this);
        this.haltState = new HaltState(this);
        this.clockSetState = new ClockSetState(this);
        this.state = getInitialState();
        this.results = results;
        this.displayTimeObject = new DisplayTime();
        this.displayTimeObject.displayTime = LocalTime.now();
        this.displayTimeObject.localTime = LocalTime.now();
        this.storeStringToResult(this.generate72Dash());
    }


    public void action(String selector) {
        try {
            updateDisplayTimeObject();
            showDisplayTime();
            int num = -1;
            if (selector.length() == 1)
                num = isStringAInteger(selector);

            if (num > -1)
                selector = "keyPress";
            String msg = String.format("Key pressed: %s", num > -1 ? Integer.toString(num) : selector);
            storeStringToResult(msg);
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
                default:
                    storeStringToResult("Invalid key press");

            }
            Thread.sleep(100);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    private void showDisplayTime() {
        DateTimeFormatter Formatter =
                DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT);
        storeStringToResult(String.format("Display time is : %s", displayTimeObject.displayTime.format(Formatter).toString()));
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


    public CookingTime getCookingTimeObject() {
        return cookingTimeObject;
    }

    void setCookingTimeObject(CookingTime cookingTimeObject) {
        if (null != cookingTimeObject) {
            String msg = String.format("Cooking time %d sec left", cookingTimeObject.cookingTime);
            storeStringToResult(msg);
        }
        this.cookingTimeObject = cookingTimeObject;
    }

    int isStringAInteger(String str) {
        str = str.trim();

        try {
            int num = Integer.parseInt(str);
            return num;
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            //System.exit(0);
        }

        return -1;
    }

    void storeStringToResult(Object obj) {
        if (null != obj)
            results.storeNewResult(obj);
    }

    private String generate72Dash() {
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < 72; i++) sbr.append("-");
        //sbr.append("\n");
        return sbr.toString();
    }

    public void setDisplayTimeObject(DisplayTime displayTimeObject) {
        this.displayTimeObject = displayTimeObject;
    }

    void updateDisplayTimeObject() {

        DisplayTime dt = new DisplayTime();
        dt.displayTime = displayTimeObject.displayTime;
        dt.localTime = displayTimeObject.localTime;

        long sec = ChronoUnit.SECONDS.between(dt.localTime, LocalTime.now());
        displayTimeObject.displayTime = displayTimeObject.displayTime.plusSeconds(sec);
        displayTimeObject.localTime = LocalTime.now();


    }
}

class CookingTime {
    int cookingTime;
    LocalTime time;
}

class DisplayTime {
    LocalTime displayTime;
    LocalTime localTime;
}
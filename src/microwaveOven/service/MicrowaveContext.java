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

    private MicrowaveStateI clockDisplayState;
    private MicrowaveStateI cookingState;
    private MicrowaveStateI haltState;
    private MicrowaveStateI clockSetState;

    private MicrowaveStateI state;
    private CookingTime cookingTimeObject;
    private DisplayTime displayTimeObject;

    private Results results;

    public MicrowaveContext(Results results) {
        this.clockDisplayState = new ClockDisplayState(this);
        this.cookingState = new CookingState(this);
        this.haltState = new HaltState(this);
        this.clockSetState = new ClockSetState(this);

        this.results = results;
        this.displayTimeObject = new DisplayTime();
        this.displayTimeObject.displayTime = this.displayTimeObject.localTime = LocalTime.now();
        this.setState(getClockDisplayState());
    }


    public void action(String selector) {

        if (selector.length() > 0) {
            updateDisplayTimeObject();
            String msg = String.format("Key pressed : %s", selector);
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
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    pressKey(Integer.parseInt(selector));
                    break;
                default:
                    storeStringToResult("Invalid key press");

            }
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

    MicrowaveStateI getClockDisplayState() {
        return clockDisplayState;
    }

    MicrowaveStateI getCookingState() {
        return cookingState;
    }

    MicrowaveStateI getHaltState() {
        return haltState;
    }

    MicrowaveStateI getClockSetState() {
        return clockSetState;
    }

    CookingTime getCookingTimeObject() {
        return cookingTimeObject;
    }

    void setState(MicrowaveStateI state) {
        results.storeNewResult(String.format("%s\nChanging State \n%s\nState set to : %s", generate72Dash(), generate72Dash(), beautifyName(state.getClass().getSimpleName())));
        this.state = state;
        if (state instanceof ClockDisplayState) showDisplayTime();
    }

    void setCookingTimeObject(CookingTime cookingTimeObject) {
        if (null != cookingTimeObject) {
            String msg = String.format("Cooking time left : %d sec", cookingTimeObject.cookingTimeLeft);
            storeStringToResult(msg);
        }
        this.cookingTimeObject = cookingTimeObject;
    }

    void storeStringToResult(Object obj) {
        if (null != obj)
            results.storeNewResult(obj);
    }

    void showDisplayTime() {
        DateTimeFormatter Formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        storeStringToResult(String.format("Display time is : %s", displayTimeObject.displayTime.format(Formatter).toString()));
    }

    private String generate72Dash() {
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < 72; i++) sbr.append("-");
        return sbr.toString();
    }

    private void updateDisplayTimeObject() {
        DisplayTime dt = new DisplayTime();
        dt.displayTime = displayTimeObject.displayTime;
        dt.localTime = displayTimeObject.localTime;
        long sec = ChronoUnit.SECONDS.between(dt.localTime, LocalTime.now());
        displayTimeObject.displayTime = displayTimeObject.displayTime.plusSeconds(sec);
        displayTimeObject.localTime = LocalTime.now();
    }

    private String beautifyName(String name) {
        StringBuilder sbr = new StringBuilder();
        for (char c : name.toCharArray()) {
            int n = (int) c;
            if (n <= (int) 'Z' && n >= (int) 'A')
                sbr.append(" ");

            sbr.append(c);
        }
        return sbr.toString().trim();
    }

    public void setDisplayTimeObject(DisplayTime displayTimeObject) {
        this.displayTimeObject = displayTimeObject;
    }
}



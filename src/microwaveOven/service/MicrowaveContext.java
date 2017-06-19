package microwaveOven.service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

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

    private int cookingTime;
    private LocalTime time;

    public MicrowaveContext() {
        this.initialState = new InitialState(this);
        this.cookingState = new cookingState(this);
        this.haltState = new HaltState(this);
        this.clockSetState = new clockSetState(this);

    }

    MicrowaveStateI state = getInitialState();

    public void action(String selector) {
        int num = 0;
        int c = 0;
        switch (c) {
            case 1:
                setOrStart();
                break;
            case 2:
                cancelOrStop();
                break;
            case 3:
                setClock();
                break;
            case 4:
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

    private void countDown() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = cookingTime;

            public void run() {
                System.out.println(i--);
                if (i < 0)
                    timer.cancel();
            }
        }, 0, 1000);
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


    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }


    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}

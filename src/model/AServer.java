/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Observable;

/**
 *
 * @author Dorian
 */
public class AServer extends Observable
{

    protected boolean _running = true;
    protected String _message = "";

    public void stop()
    {
        this._running = false;
    }

    protected void somethingChanged()
    {
        this.setChanged();
        this.notifyObservers();
    }

    public String getMessage()
    {
        return this._message;
    }

}

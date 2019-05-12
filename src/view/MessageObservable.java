/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Observable;

/**
 *
 * @author Dorian
 */
public class MessageObservable extends Observable
{

    private String _message;

    public String getMessage()
    {
        return this._message;
    }

    public void setMessage(String mess)
    {
        this._message = mess;
    }
}

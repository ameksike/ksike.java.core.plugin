/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksike.plugin;

/**
 *
 * @author jose.rod
 */
public class KsPluginException extends Exception {

    private int code;
    private String sms;
    private String name;

    public KsPluginException(int code, String name, String sms) {
        super();
        this.code = code;
        this.name = name;
        this.sms = sms;
        
    }

    @Override
    public String getMessage() {
        String mess = "";
        switch (this.code) {
            case 1:
                mess = "Error: Plugin onInit > "+ this.name + " >> " + this.sms;
                break;
            case 2:
                mess = "Error: Plugin onLoad > "+ this.name + " >> " + this.sms;
                break;
            case 3:
                mess = "Error: Plugin > "+ this.name + " >> " + this.sms;
                break;
        }
        return mess;
    }

}

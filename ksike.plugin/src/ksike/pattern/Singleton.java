/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ksike.pattern;


/**
 *
 * @author adm
 */
public class Singleton {
    //... Singleton ...........................................
    protected static Singleton _obj = null;
    protected static Singleton self() {
        if (_obj == null) {
             
            _obj = new Singleton();
        }
        return _obj;
    }
    //.........................................................
}

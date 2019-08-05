package ksike.mvc;

import ksike.secretary.EntityManager;

/**
 * @author Antonio Membrides Espinosa
 * @made 19/04/2019
 * @version 1.0
 */
public class KsEntity {
    protected EntityManager em;
    protected String _table;

    public String getTable() {
        return _table;
    }

    public void setTable(String _table) {
        this._table = _table;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}

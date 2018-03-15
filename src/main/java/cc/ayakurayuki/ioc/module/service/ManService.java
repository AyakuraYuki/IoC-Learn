package cc.ayakurayuki.ioc.module.service;

import cc.ayakurayuki.ioc.module.dao.ManDAO;
import cc.ayakurayuki.ioc.module.entity.Man;

/**
 * Man业务逻辑层模拟
 * <p>
 * cc.ayakurayuki.ioc.module.service
 *
 * @author ayakurayuki
 * @date 2018/3/15-14:04
 */
public class ManService {

    private ManDAO manDAO;

    public Man get() {
        return manDAO.get();
    }

    public void setManDAO(ManDAO manDAO) {
        this.manDAO = manDAO;
    }

}

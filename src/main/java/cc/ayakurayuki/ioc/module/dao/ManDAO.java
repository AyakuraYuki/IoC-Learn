package cc.ayakurayuki.ioc.module.dao;

import cc.ayakurayuki.ioc.module.entity.Man;

/**
 * Man数据访问层模拟
 * <p>
 * cc.ayakurayuki.ioc.module.dao
 *
 * @author ayakurayuki
 * @date 2018/3/15-14:02
 */
public class ManDAO {

    public Man get() {
        Man man = new Man();
        man.setCommand("vim");
        man.setDescription("VIM(1)                                                                  VIM(1)\n" +
                "\n" +
                "NAME\n" +
                "       vim - Vi IMproved, a programmers text editor\n" +
                "\n" +
                "SYNOPSIS\n" +
                "       vim [options] [file ..]\n" +
                "       vim [options] -\n" +
                "       vim [options] -t tag\n" +
                "       vim [options] -q [errorfile]\n" +
                "\n" +
                "       ex\n" +
                "       view\n" +
                "       gvim gview evim eview\n" +
                "       rvim rview rgvim rgview\n");
        return man;
    }

}

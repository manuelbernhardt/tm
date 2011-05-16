package util;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class MySQL5InnoDBUTF8Dialect extends MySQL5InnoDBDialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }


}

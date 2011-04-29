import org.hibernate.ejb.Ejb3Configuration;
import play.db.jpa.JPAConfigurationExtension;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class TestJPAConfigurationExtension extends JPAConfigurationExtension {

    public static Ejb3Configuration configuration;

    @Override
    public void configure(Ejb3Configuration ejb3Configuration) {
        configuration = ejb3Configuration;
    }
}

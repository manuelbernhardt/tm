package models.general;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.Query;

import play.db.jpa.JPA;

/**
 * Generates natural identifiers for composite models
 * If we are successful this will have to be rewritten (as it can't work in a clustered environment),
 * or a special Hibernate identifier generator will have to be used.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class CompositeModelIdentifierGenerator {

    private static Map<Class<? extends CompositeModel>, AtomicLong> counters = new ConcurrentHashMap<Class<? extends CompositeModel>, AtomicLong>();

    public static Long getNextNaturalIdentifier(Class<? extends CompositeModel> clazz) {
        AtomicLong counter = counters.get(clazz);
        if(counter == null) {
            Query query = JPA.em().createQuery("select count(*) from " + clazz.getSimpleName() + " m group by m.id");
            List r = query.getResultList();
            if(r.isEmpty()) {
                counter = new AtomicLong(0l);
            } else {
                Long count = (Long) r.get(0);
                counter = new AtomicLong(count + 1);
                counters.put(clazz, counter);
            }
        }
        return counter.getAndIncrement();
    }
}
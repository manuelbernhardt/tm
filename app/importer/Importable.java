package importer;

import java.util.Map;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface Importable<T> {

    public T convert(Object data, Map<String, Object> contextData);

    // why can't java have an optional keyword?
    public void postConversionCallback(Map<String, Object> contextData);
}

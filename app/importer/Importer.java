package importer;

import java.io.File;
import java.util.List;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface Importer {

    public List<ImportError> importFile(File file) throws Throwable;

}

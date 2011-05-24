import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import importer.ExcelImporter;
import importer.Importer;
import models.tm.Project;
import models.tm.Requirement;
import models.tm.TMUser;
import models.tm.test.Tag;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ExcelImporterTest extends UnitTest {

    @Before
    public void setUp() {
        new SeleniumTestDataLoader();
    }

    @Test
    public void testBasicImport() {

        // count before import
        Long count = Requirement.count("from Requirement r order by r.created asc");

        Project p = Project.find("from Project p where p.name = 'Test Management Application'").first();

        Importer importer = new ExcelImporter();
        File f = new File("test/ExcelImport.xls");
        Map<String, Object> contextData = new HashMap<String, Object>();
        contextData.put("accountId", p.account.getId());

        try {
            importer.importFile(Requirement.class, contextData, p, f);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            fail();
        }

        // test if the newly imported requirements are there
        List<Requirement> requirements = Requirement.find("from Requirement r order by r.created asc").from(count.intValue()).<Requirement>fetch();
        assertEquals(4, requirements.size());

        assertEquals("Imported Requirement 1", requirements.get(0).name);
        assertEquals("Imported Requirement 2", requirements.get(1).name);
        assertEquals("Imported Requirement 3", requirements.get(2).name);
        assertEquals("Imported Requirement 4", requirements.get(3).name);

        assertEquals("Imported Description 1", requirements.get(0).description);
        assertEquals("Imported Description 2", requirements.get(1).description);
        assertEquals("Imported Description 3", requirements.get(2).description);
        assertEquals("Imported Description 4", requirements.get(3).description);

        TMUser gwen = TMUser.find("from TMUser u where u.user.firstName = 'Gwenael' and u.user.lastName = 'Alizon'").first();
        TMUser manu = TMUser.find("from TMUser u where u.user.firstName = 'Manuel' and u.user.lastName = 'Bernhardt'").first();

        assertEquals(gwen, requirements.get(0).createdBy);
        assertEquals(gwen, requirements.get(1).createdBy);
        assertEquals(manu, requirements.get(2).createdBy);
        assertEquals(manu, requirements.get(3).createdBy);

        Tag functional = Tag.find("from Tag t where t.name = 'Functional' and t.type = ?", Tag.TagType.REQUIREMENT).<Tag>first();
        Tag technical = Tag.find("from Tag t where t.name = 'Technical' and t.type = ?", Tag.TagType.REQUIREMENT).<Tag>first();
        Tag newTag = Tag.find("from Tag t where t.name = 'Newtag' and t.type = ?", Tag.TagType.REQUIREMENT).<Tag>first();

        assertEquals(requirements.get(0).tags.size(), 1);
        assertTrue(requirements.get(0).tags.contains(functional));

        assertEquals(requirements.get(1).tags.size(), 2);
        assertTrue(requirements.get(1).tags.contains(functional));
        assertTrue(requirements.get(1).tags.contains(technical));

        assertEquals(requirements.get(2).tags.size(), 2);
        assertTrue(requirements.get(2).tags.contains(functional));
        assertTrue(requirements.get(2).tags.contains(technical));

        assertEquals(requirements.get(3).tags.size(), 2);
        assertTrue(requirements.get(3).tags.contains(newTag));
        assertTrue(requirements.get(3).tags.contains(functional));

    }


}

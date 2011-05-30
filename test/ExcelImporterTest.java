import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import importer.ExcelImporter;
import importer.Importer;
import models.tm.Project;
import models.tm.ProjectTreeNode;
import models.tm.Requirement;
import models.tm.RequirementFolder;
import models.tm.TMUser;
import models.tm.test.Tag;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;
import tree.persistent.AbstractTree;

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

        // names
        assertEquals("Imported Requirement 1", requirements.get(0).name);
        assertEquals("Imported Requirement 2", requirements.get(1).name);
        assertEquals("Imported Requirement 3", requirements.get(2).name);
        assertEquals("Imported Requirement 4", requirements.get(3).name);

        // descriptions
        assertEquals("Imported Description 1", requirements.get(0).description);
        assertEquals("Imported Description 2", requirements.get(1).description);
        assertEquals("Imported Description 3", requirements.get(2).description);
        assertEquals("Imported Description 4", requirements.get(3).description);

        // users
        TMUser gwen = TMUser.find("from TMUser u where u.user.firstName = 'Gwenael' and u.user.lastName = 'Alizon'").first();
        TMUser manu = TMUser.find("from TMUser u where u.user.firstName = 'Manuel' and u.user.lastName = 'Bernhardt'").first();

        assertEquals(gwen, requirements.get(0).createdBy);
        assertEquals(gwen, requirements.get(1).createdBy);
        assertEquals(manu, requirements.get(2).createdBy);
        assertEquals(manu, requirements.get(3).createdBy);

        // tags
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

        // TreeNodes
        ProjectTreeNode importRoot = ProjectTreeNode.find("from ProjectTreeNode n where n.path = '/ImportRoot'").<ProjectTreeNode>first();
        ProjectTreeNode subFolder1 = ProjectTreeNode.find("from ProjectTreeNode n where n.path = '/ImportRoot/SubFolder1'").<ProjectTreeNode>first();
        ProjectTreeNode subFolder2 = ProjectTreeNode.find("from ProjectTreeNode n where n.path = '/ImportRoot/SubFolder2'").<ProjectTreeNode>first();
        ProjectTreeNode anotherRoot = ProjectTreeNode.find("from ProjectTreeNode n where n.path = '/AnotherRoot'").<ProjectTreeNode>first();

        assertNotNull(importRoot);
        assertNotNull(subFolder1);
        assertNotNull(subFolder2);
        assertNotNull(anotherRoot);

        RequirementFolder importRootFolder = RequirementFolder.find("name = 'ImportRoot'").<RequirementFolder>first();
        RequirementFolder subFolder1Folder = RequirementFolder.find("name = 'SubFolder1'").<RequirementFolder>first();
        RequirementFolder subFolder2Folder = RequirementFolder.find("name = 'SubFolder2'").<RequirementFolder>first();
        RequirementFolder anotherRootFolder = RequirementFolder.find("name = 'AnotherRoot'").<RequirementFolder>first();

        assertNotNull(importRootFolder);
        assertNotNull(subFolder1Folder);
        assertNotNull(subFolder2Folder);
        assertNotNull(anotherRootFolder);

        assertEquals(AbstractTree.getNodeType(RequirementFolder.class).getName(), importRoot.getType());
        assertEquals(AbstractTree.getNodeType(RequirementFolder.class).getName(), subFolder1.getType());
        assertEquals(AbstractTree.getNodeType(RequirementFolder.class).getName(), subFolder2.getType());
        assertEquals(AbstractTree.getNodeType(RequirementFolder.class).getName(), anotherRoot.getType());

        assertEquals(importRootFolder.getId(), importRoot.getNodeId());
        assertEquals(subFolder1Folder.getId(), subFolder1.getNodeId());
        assertEquals(subFolder2Folder.getId(), subFolder2.getNodeId());
        assertEquals(anotherRootFolder.getId(), anotherRoot.getNodeId());

        ProjectTreeNode importedRequirement1 = ProjectTreeNode.find("from ProjectTreeNode n where n.name = 'Imported Requirement 1'").<ProjectTreeNode>first();
        ProjectTreeNode importedRequirement2 = ProjectTreeNode.find("from ProjectTreeNode n where n.name = 'Imported Requirement 2'").<ProjectTreeNode>first();
        ProjectTreeNode importedRequirement3 = ProjectTreeNode.find("from ProjectTreeNode n where n.name = 'Imported Requirement 3'").<ProjectTreeNode>first();
        ProjectTreeNode importedRequirement4 = ProjectTreeNode.find("from ProjectTreeNode n where n.name = 'Imported Requirement 4'").<ProjectTreeNode>first();

        assertNotNull(importedRequirement1);
        assertNotNull(importedRequirement2);
        assertNotNull(importedRequirement3);
        assertNotNull(importedRequirement4);

        assertEquals(requirements.get(0).getId(), importedRequirement1.getNodeId());
        assertEquals(requirements.get(1).getId(), importedRequirement2.getNodeId());
        assertEquals(requirements.get(2).getId(), importedRequirement3.getNodeId());
        assertEquals(requirements.get(3).getId(), importedRequirement4.getNodeId());
    }


}

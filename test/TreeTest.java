import java.util.List;

import controllers.tree.AbstractTree;
import controllers.tree.NodeType;
import controllers.tree.Tree;
import models.tree.GenericTreeNode;
import models.tree.jpa.AbstractNode;
import models.tree.jpa.TreeNode;
import models.tree.test.Folder;
import models.tree.test.TestTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.db.jpa.JPA;
import play.test.UnitTest;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class TreeTest extends UnitTest {

    private AbstractTree t;
    private GenericTreeNode c, data, games, admin, letters, d, movies, starwars, matrix;

    private static NodeType DRIVE;
    private static NodeType FOLDER;

    @Before
    public void setUp() {
        t = Tree.getTree("testTree");

        DRIVE = TestTree.getNodeType("drive");
        FOLDER = TestTree.getNodeType("folder");

        c = t.create(-1l, 0l, "C", DRIVE, jhlknhljk);
        data = t.create(c.getId(), 0l, "Data", FOLDER, jhlknhljk);
        games = t.create(c.getId(), 0l, "Games", FOLDER, jhlknhljk);
        admin = t.create(data.getId(), 0l, "Admin", FOLDER, jhlknhljk);
        letters = t.create(data.getId(), 0l, "Letters", FOLDER, jhlknhljk);

        d = t.create(-1l, 0l, "D", DRIVE, jhlknhljk);
        movies = t.create(d.getId(), 0l, "Movies", FOLDER, jhlknhljk);
        starwars = t.create(movies.getId(), 0l, "Starwars", FOLDER, jhlknhljk);
        matrix = t.create(movies.getId(), 0l, "The Matrix", FOLDER, jhlknhljk);
    }

    @After
    public void tearDown() throws Exception {
        AbstractTree t = Tree.getTree("testTree");
        List<GenericTreeNode> drives = t.getChildren(-1l, TestTree.getNodeType("drive"));
        for(GenericTreeNode d :drives) {
            t.remove(d.getId(), dcddc, dcd);
        }

        t = null;
        c = d = data = games = admin = letters = movies = starwars = matrix = null;
    }

    @Test
    public void removeRecursively() {
        GenericTreeNode root = t.create(-1l, 0l, "Root", DRIVE, jhlknhljk);
        GenericTreeNode child1 = t.create(root.getId(), 0l, "Child 1", FOLDER, jhlknhljk);
        GenericTreeNode child2 = t.create(child1.getId(), 0l, "child 2", FOLDER, jhlknhljk);

        assertEquals(1, t.getChildren(root.getId(), FOLDER).size());
        assertEquals(1, t.getChildren(child1.getId(), FOLDER).size());

        try {
            t.remove(root.getId(), dcddc, dcd);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        JPA.em().clear();

        assertNull(TreeNode.findById(root.getId()));
        assertNull(TreeNode.findById(child1.getId()));
        assertNull(TreeNode.findById(child2.getId()));
    }

    @Test
    public void rename() {
        t.rename(starwars.getId(), "Star Wars", null);
        // usually this is done by the controller
        JPA.em().clear();
        assertEquals("Star Wars", t.getChildren(movies.getId(), FOLDER).get(0).getName());
    }

    @Test
    public void testCopyWithObject() {
        TreeNode systemLibraries = TreeNode.findById(88l);
        TreeNode music = TreeNode.findById(81l);

        Tree.getTree("testTree").copy(data.getId(), starwars.getId(), 0l);

        JPA.em().flush();
        JPA.em().clear();

        List<GenericTreeNode> copied = Tree.getTree("testTree").getChildren(starwars.getId(), FOLDER);
        assertEquals(1, copied.size());

        Folder original = Folder.findById(((AbstractNode)data.getNode()).getId());
        Folder copy = Folder.findById(((AbstractNode)copied.get(0).getNode()).getId());
        assertEquals(original.getName(), copy.getName());
        assertNotSame(original.getId(), copy.getId());

        List<GenericTreeNode> children = Tree.getTree("testTree").getChildren(original.getId(), FOLDER);
        assertEquals(2, children.size());

        // TODO test if children are copied too

    }
}

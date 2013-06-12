package pl.tls.repo;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.tls.entity.Mark;
import pl.tls.entity.Model;
import pl.tls.service.util.Producer;

/**
 *
 * @author Tomasz.Lenczyk
 */
@RunWith(Arquillian.class)
public class MarkRepoTest {

    @Deployment
    public static Archive<?> createDeployment() {

        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Mark.class.getPackage())
                .addPackage(MarkRepo.class.getPackage())
                .addPackage(Producer.class.getPackage())
                .addAsLibraries(resolver.artifact("org.apache.commons:commons-lang3").resolveAsFiles())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(archive.toString(true));

        return archive;
    }
    @Inject
    private MarkRepo markRepo;
    @Inject
    private ModelRepo modelRepo;

    @Test
    public void shouldInject() throws Exception {
        Assert.assertNotNull(markRepo);
    }

    @Test
    public void shouldSaveOneMark() throws Exception {
        Mark m = createMark();
        markRepo.save(m);
        long count = markRepo.count();
        Assert.assertEquals(1, count);
    }

    @Test
    public void shouldSaveOneMarkWithTwoModels() throws Exception {
        //Given
        Mark mark = createMark();

        //When
        markRepo.save(mark);

        //Then
        long count = markRepo.count();
        List<Mark> allMarks = markRepo.findAll();

        Assert.assertEquals(1, count);
        Assert.assertEquals(1, allMarks.size());
        Mark markFromDB = allMarks.get(0);

        Assert.assertEquals(mark.getName(), markFromDB.getName());

        List<Model> modelsFromDB = markFromDB.getModels();

        Assert.assertNotNull(modelsFromDB);
        Assert.assertEquals(2, modelsFromDB.size());
        Assert.assertEquals(mark.getModels().get(0).getName(), modelsFromDB.get(0).getName());
        Assert.assertEquals(mark.getModels().get(1).getName(), modelsFromDB.get(1).getName());
    }

    @Test
    public void shouldRremoveModelsWhenMarkRemoved() throws Exception {
        //Given
        Mark mark = createMark();


        //When
        markRepo.save(mark);
        markRepo.deleteById(mark.getId());



        //Then
        List<Mark> allMarksFromDB = markRepo.findAll();
        List<Model> allModelsFromDB = modelRepo.findAll();

        Assert.assertNotNull(allMarksFromDB);
        Assert.assertEquals(0, allMarksFromDB.size());

        Assert.assertNotNull(allModelsFromDB);
        Assert.assertEquals(0, allModelsFromDB.size());
    }

    @After
    public void cleanup() throws Exception {
        int removed = markRepo.deleteAll();
        System.out.println("Marks removed: " + removed);
    }

    private Mark createMark() {
        Mark mark = new Mark("Opel");
        List<Model> models = new ArrayList<>();

        Model m1 = new Model("Corsa");
        Model m2 = new Model("Astra");

        models.add(m1);
        models.add(m2);

        mark.setModels(models);
        return mark;
    }
}

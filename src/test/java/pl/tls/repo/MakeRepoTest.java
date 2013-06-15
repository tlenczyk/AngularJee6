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
import pl.tls.entity.Make;
import pl.tls.entity.Model;
import pl.tls.service.util.Producer;

/**
 *
 * @author Tomasz.Lenczyk
 */
@RunWith(Arquillian.class)
public class MakeRepoTest {

    @Deployment
    public static Archive<?> createDeployment() {

        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Make.class.getPackage())
                .addPackage(MakeRepo.class.getPackage())
                .addPackage(Producer.class.getPackage())
                .addAsLibraries(resolver.artifact("org.apache.commons:commons-lang3").resolveAsFiles())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(archive.toString(true));

        return archive;
    }
    @Inject
    private MakeRepo makeRepo;
    @Inject
    private ModelRepo modelRepo;

    @Test
    public void shouldInject() throws Exception {
        Assert.assertNotNull(makeRepo);
    }

    @Test
    public void shouldSaveOneMake() throws Exception {
        Make m = createMake();
        makeRepo.save(m);
        long count = makeRepo.count();
        Assert.assertEquals(1, count);
    }

    @Test
    public void shouldSaveOneMakeWithTwoModels() throws Exception {
        //Given
        Make make = createMake();

        //When
        makeRepo.save(make);

        //Then
        long count = makeRepo.count();
        List<Make> allMakes = makeRepo.findAll();

        Assert.assertEquals(1, count);
        Assert.assertEquals(1, allMakes.size());
        Make makeFromDB = allMakes.get(0);

        Assert.assertEquals(make.getName(), makeFromDB.getName());

        List<Model> modelsFromDB = makeFromDB.getModels();

        Assert.assertNotNull(modelsFromDB);
        Assert.assertEquals(2, modelsFromDB.size());
        Assert.assertEquals(make.getModels().get(0).getName(), modelsFromDB.get(0).getName());
        Assert.assertEquals(make.getModels().get(1).getName(), modelsFromDB.get(1).getName());
    }

    @Test
    public void shouldRremoveModelsWhenMakeRemoved() throws Exception {
        //Given
        Make make = createMake();


        //When
        makeRepo.save(make);
        makeRepo.deleteById(make.getId());



        //Then
        List<Make> allMakesFromDB = makeRepo.findAll();
        List<Model> allModelsFromDB = modelRepo.findAll();

        Assert.assertNotNull(allMakesFromDB);
        Assert.assertEquals(0, allMakesFromDB.size());

        Assert.assertNotNull(allModelsFromDB);
        Assert.assertEquals(0, allModelsFromDB.size());
    }

    @After
    public void cleanup() throws Exception {
        int removed = makeRepo.deleteAll();
        System.out.println("Marks removed: " + removed);
    }

    private Make createMake() {
        Make make = new Make("Opel");
        List<Model> models = new ArrayList<>();

        Model m1 = new Model("Corsa");
        Model m2 = new Model("Astra");

        models.add(m1);
        models.add(m2);

        make.setModels(models);
        return make;
    }
}

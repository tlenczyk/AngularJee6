package pl.tls.repo;

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
import pl.tls.entity.Model;
import pl.tls.service.util.Producer;

/**
 *
 * @author Tomasz.Lenczyk
 */
@RunWith(Arquillian.class)
public class ModelRepoTest {

    @Deployment
    public static Archive<?> createDeployment() {

        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Model.class.getPackage())
                .addPackage(ModelRepo.class.getPackage())
                .addPackage(Producer.class.getPackage())
                .addAsLibraries(resolver.artifact("org.apache.commons:commons-lang3").resolveAsFiles())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(archive.toString(true));

        return archive;
    }
    @Inject
    private ModelRepo modelRepo;

    @Test
    public void shouldInject() throws Exception {
        Assert.assertNotNull(modelRepo);
    }

    @Test
    public void shouldSaveOneModel() throws Exception {
        Model m = new Model("206");
        modelRepo.save(m);
        long count = modelRepo.count();
        Assert.assertEquals(1, count);

        Model modelFromDB = modelRepo.findById(m.getId());
        Assert.assertNotNull(modelFromDB);
        Assert.assertEquals(m.getName(), modelFromDB.getName());
    }

    @Test
    public void shouldSaveAndRemove() throws Exception {
        Model m = new Model("206");
        modelRepo.save(m);
        modelRepo.deleteById(m.getId());

        long count = modelRepo.count();

        Assert.assertEquals(0, count);
    }

    @After
    public void cleanup() throws Exception {
        int removed = modelRepo.deleteAll();
        System.out.println("Models removed: " + removed);
    }
}

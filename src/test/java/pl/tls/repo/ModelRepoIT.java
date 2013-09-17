package pl.tls.repo;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.tls.entity.Model;
import pl.tls.service.util.Producer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;

/**
 *
 * @author Tomasz.Lenczyk
 */
@RunWith(Arquillian.class)
public class ModelRepoIT {

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
        assertThat(modelRepo, is(notNullValue()));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void shouldSaveOneModel() throws Exception {
        //Given
        Model m = new Model("206");

        //When
        modelRepo.save(m);
        List<Model> modelsFromDB = modelRepo.findAll();
        Model modelFromDB = modelRepo.findById(m.getId());

        //Than
        assertThat(modelsFromDB, is(notNullValue()));
        assertThat(modelsFromDB.size(), is(equalTo(1)));
        assertThat(modelFromDB, is(notNullValue()));
        assertThat(m.getName(), is(equalTo(modelFromDB.getName())));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void shouldSaveAndRemove() throws Exception {
        //Given
        Model m = new Model("206");

        //When
        modelRepo.save(m);
        modelRepo.deleteById(m.getId());
        long count = modelRepo.count();

        //Than
        assertThat(count, is(equalTo(0L)));
    }
}

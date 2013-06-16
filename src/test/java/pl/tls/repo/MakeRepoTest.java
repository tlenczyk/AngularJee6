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
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.tls.entity.Make;
import pl.tls.entity.Model;
import pl.tls.service.util.Producer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.notNullValue;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;

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
        assertThat(makeRepo, is(notNullValue()));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void shouldSaveOneMake() throws Exception {
        Make m = createMake();
        makeRepo.save(m);
        long count = makeRepo.count();
        assertThat(count, is(equalTo(1L)));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void shouldSaveOneMakeWithTwoModels() throws Exception {
        //Given
        Make make = createMake();

        //When
        makeRepo.save(make);
        long count = makeRepo.count();
        List<Make> allMakes = makeRepo.findAll();

        //Then
        assertThat(count, is(equalTo(1L)));
        assertThat(allMakes.size(), is(equalTo(1)));
        Make makeFromDB = allMakes.get(0);

        assertThat(make.getName(), is(equalTo(makeFromDB.getName())));

        List<Model> modelsFromDB = makeFromDB.getModels();

        assertThat(modelsFromDB, is(notNullValue()));
        assertThat(modelsFromDB.size(), is(equalTo(2)));
        assertThat(make.getModels().get(0).getName(), is(equalTo(modelsFromDB.get(0).getName())));
        assertThat(make.getModels().get(1).getName(), is(equalTo(modelsFromDB.get(1).getName())));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void shouldRremoveModelsWhenMakeRemoved() throws Exception {
        //Given
        Make make = createMake();


        //When
        makeRepo.save(make);
        makeRepo.deleteById(make.getId());
        List<Make> allMakesFromDB = makeRepo.findAll();
        List<Model> allModelsFromDB = modelRepo.findAll();


        //Then
        assertThat(allMakesFromDB, is(notNullValue()));
        assertThat(allMakesFromDB, is(empty()));

        assertThat(allModelsFromDB, is(notNullValue()));
        assertThat(allModelsFromDB, is(empty()));
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

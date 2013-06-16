package pl.tls.repo;

import java.math.BigDecimal;
import java.util.Date;
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
import pl.tls.entity.Auction;
import pl.tls.entity.Color;
import pl.tls.entity.FuelType;
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
public class AuctionRepoTest {

    @Deployment
    public static Archive<?> createDeployment() {

        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Auction.class.getPackage())
                .addPackage(AuctionRepo.class.getPackage())
                .addPackage(Producer.class.getPackage())
                .addAsLibraries(resolver.artifact("org.apache.commons:commons-lang3").resolveAsFiles())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(archive.toString(true));

        return archive;
    }
    @Inject
    private AuctionRepo auctionRepo;
    @Inject
    private MakeRepo makeRepo;
    @Inject
    private ModelRepo modelRepo;

    @Test
    public void shouldInject() throws Exception {
        Assert.assertNotNull(auctionRepo);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void shouldSaveOneAuction() throws Exception {
        //Given
        Auction auction = createAuction();

        //When
        auctionRepo.save(auction);
        Auction auctionFromDB = auctionRepo.findById(auction.getId());
        long count = auctionRepo.count();

        //Then
        assertThat(count, is(equalTo(1L)));
        assertThat(auctionFromDB, is(notNullValue()));
        assertThat(auction, is(equalTo(auctionFromDB)));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void shouldFindAuctionsByMakeName() throws Exception {
        //Given
        Make make = new Make("Peugeot");
        Model model = new Model("206");
        make.getModels().add(model);

        Make make2 = new Make("BMW");
        Model model2 = new Model("E39");
        make2.getModels().add(model2);

        makeRepo.save(make);
        makeRepo.save(make2);

        Auction auction1 = createAuction();
        auction1.setMake(make);
        Auction auction2 = createAuction();
        auction2.setMake(make2);
        auctionRepo.save(auction1);
        auctionRepo.save(auction2);

        //When
        List<Auction> auctionsByMakeFromDB = auctionRepo.getAuctionsByMake(make.getName());
        long actiounsCount = auctionRepo.count();

        //Then
        assertThat(actiounsCount, is(equalTo(2L)));

        assertThat(auctionsByMakeFromDB, is(notNullValue()));
        assertThat(auction1, is(equalTo(auctionsByMakeFromDB.get(0))));
    }

//    @After
//    public void cleanup() throws Exception {
//        int removed = auctionRepo.deleteAll();
//        System.out.println("Auctions removed: " + removed);
//    }
    private Auction createAuction() {
        Auction auction = new Auction();
        auction.setTitle("Title");
        auction.setDescription("Description");

        auction.setProductionYear(new Date());

        auction.setColor(Color.BLACK);
        auction.setFuelType(FuelType.GASOLINE);
        auction.setMilage(Long.valueOf(10000));
        auction.setPrice(BigDecimal.valueOf(5000L));
        auction.setCreationDate(new Date());
        return auction;
    }
}

package pl.tls.repo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import pl.tls.repo.AuctionRepo;
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
import pl.tls.entity.Mark;
import pl.tls.entity.Model;
import pl.tls.service.util.Producer;

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
    private MarkRepo markRepo;
    @Inject
    private ModelRepo modelRepo;

    @Test
    public void shouldInject() throws Exception {
        Assert.assertNotNull(auctionRepo);
    }

    @Test
    public void shouldSaveOneAuction() throws Exception {
        //Given
        Auction auction = createAuction();

        //When
        auctionRepo.save(auction);
        Auction auctionFromDB = auctionRepo.findById(auction.getId());

        //Then
        long count = auctionRepo.count();
        Assert.assertEquals(1, count);

        Assert.assertNotNull(auctionFromDB);
        Assert.assertEquals(auction, auctionFromDB);
    }

    @Test
    public void shouldFindAuctionsByMarkName() throws Exception {
        //Given
        Mark mark = new Mark("Peugeot");
        Model model = new Model("206");
        mark.getModels().add(model);

        Mark mark2 = new Mark("BMW");
        Model model2 = new Model("E39");
        mark2.getModels().add(model2);

        markRepo.save(mark);
        markRepo.save(mark2);

        Auction auction1 = createAuction();
        auction1.setMark(mark);
        Auction auction2 = createAuction();
        auction2.setMark(mark2);
        auctionRepo.save(auction1);
        auctionRepo.save(auction2);

        //When
        List<Auction> auctionsByMarkFromDB = auctionRepo.getAuctionsByMark(mark.getName());

        //Then
        long actiounsCount = auctionRepo.count();
        Assert.assertEquals(2, actiounsCount);

        Assert.assertNotNull(auctionsByMarkFromDB);
        Assert.assertEquals(1, auctionsByMarkFromDB.size());
        Assert.assertEquals(auction1, auctionsByMarkFromDB.get(0));
    }

    @After
    public void cleanup() throws Exception {
        int removed = auctionRepo.deleteAll();
        System.out.println("Auctions removed: " + removed);
    }

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

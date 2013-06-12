/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.entity;

import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Tomasz.Lenczyk
 */
@RunWith(Arquillian.class)
public class AuctionTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Auction.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    @PersistenceContext
    EntityManager em;
    @Inject
    UserTransaction utx;

    @Before
    public void preparePersistenceTest() throws Exception {
        clearData();
        insertData();
        startTransaction();
    }

    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Dumping old records...");
        em.createQuery("delete from Auction").executeUpdate();
        em.createQuery("delete from Model").executeUpdate();
        em.createQuery("delete from Mark").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();



        System.out.println("Inserting records...");

        Mark mark = new Mark("Peugeot");
        for (int i = 0; i < 10; i++) {
            Model model = new Model("Model " + i);
            mark.getModels().add(model);
        }

        em.persist(mark);

        for (int i = 0; i < 1; i++) {
            Auction auction = new Auction();
            auction.setMark(mark);
            auction.setColor(Color.BLACK);
            auction.setPrice(new BigDecimal("10000" + i));

            em.persist(auction);
        }
        utx.commit();
        // reset the persistence context (cache)
        em.clear();
    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    @After
    public void commitTransaction() throws Exception {
        utx.commit();
    }

    @Test
    public void shouldFindAllMarks() throws Exception {
        // given
        String selectMarks = "select m from Mark m";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<Mark> marks = em.createQuery(selectMarks, Mark.class).getResultList();

        List<Model> models = em.createQuery("select m from Model m", Model.class).getResultList();

        Assert.assertNotNull(marks);
        Assert.assertNotNull(models);
        Assert.assertEquals(1, marks.size());
        Assert.assertEquals(10, models.size());

        List<Auction> auctions = em.createQuery("select a from Auction a", Auction.class).getResultList();
        Assert.assertNotNull(marks);
        Assert.assertEquals(1, marks.size());
//
        Auction a = auctions.get(0);
        Mark mark = a.getMark();
        a.setMark(null);
        em.merge(a);
        em.remove(mark);
        marks = em.createQuery(selectMarks, Mark.class).getResultList();
        Assert.assertNotNull(marks);
        Assert.assertEquals(0, marks.size());


        models = em.createQuery("select m from Model m", Model.class).getResultList();
        Assert.assertNotNull(models);
        Assert.assertEquals(0, models.size());
    }

    @Test
    public void shouldFindAllModels1() throws Exception {
        // given
        String fetchingAllAuctionsInJpql = "select a from Auction a order by a.id";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<Auction> auctions = em.createQuery(fetchingAllAuctionsInJpql, Auction.class).getResultList();

        // then
        System.out.println("Found " + auctions.size() + " cars (using JPQL):");

        Assert.assertEquals(1, auctions.size());
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.service;

import pl.tls.repo.AuctionRepo;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import pl.tls.entity.Auction;
import pl.tls.entity.Color;
import pl.tls.entity.FuelType;
import pl.tls.entity.Mark;
import pl.tls.entity.Marks;
import pl.tls.entity.Model;
import pl.tls.repo.MarkRepo;
import pl.tls.repo.ModelRepo;

/**
 *
 * @author Tomasz.Lenczyk
 */
@Singleton
@Startup
public class InitAuctions {

    @Inject
    private Logger LOG;
    @Inject
    AuctionRepo auctionRepo;
    @Inject
    MarkRepo markRepo;

    @PostConstruct
    public void init() {

        auctionRepo.deleteAll();
        markRepo.deleteAll();

        for (Marks mark : Marks.values()) {

            Mark markToSave = mark.getMark();
            markRepo.save(markToSave);

            for (int i = 0; i < 10; i++) {
                int size = mark.getMark().getModels().size();
                Model model = mark.getMark().getModels().get(getRandom(size - 1));
                Calendar gc = new GregorianCalendar();
                gc.set(Calendar.YEAR, 2000 + i);

                Auction auction = new Auction("title " + i, "desciption " + i, gc.getTime(), markToSave, model, Color.getRandom(), new BigDecimal(getRandom(30000)), FuelType.GASOLINE, Long.valueOf(getRandom(500001)), new Date());
                auctionRepo.save(auction);
            }
        }
    }

    private int getRandom(int size) {

        return new Random().nextInt(size);

    }
}

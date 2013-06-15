/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import pl.tls.entity.Auction;
import pl.tls.entity.Color;
import pl.tls.entity.FuelType;
import pl.tls.entity.Make;
import pl.tls.entity.Model;
import pl.tls.rest.dto.ColorDTO;
import pl.tls.rest.dto.FuelTypeDTO;
import pl.tls.rest.pojo.AddAuctionRequest;
import pl.tls.repo.AuctionRepo;
import pl.tls.repo.MakeRepo;
import pl.tls.repo.ModelRepo;
import pl.tls.service.util.LoggingInterceptor;

/**
 * REST Web Service
 *
 * @author Tomasz.Lenczyk
 */
@Path("/")
@Stateless
@Interceptors(LoggingInterceptor.class)
public class AuctionResource {

    @Inject
    private Logger LOG;
    @Inject
    private AuctionRepo auctionRepo;
    @Inject
    private MakeRepo makeRepo;
    @Inject
    private ModelRepo modelRepo;

    @PostConstruct
    public void init() {
    }

    public AuctionResource() {
    }

    @GET
    @Path("makes")
    @Produces("application/json")
    public List<Make> getMakes() {
        return makeRepo.findAll();
    }

    @GET
    @Path("colors")
    @Produces("application/json")
    public List<ColorDTO> getColors() {
        List<ColorDTO> colors = new ArrayList<>();
        for (Color color : Color.values()) {
            ColorDTO dto = new ColorDTO(color.name(), color);
            colors.add(dto);
        }
        return colors;
    }

    @GET
    @Path("fuelTypes")
    @Produces("application/json")
    public List<FuelTypeDTO> getFuelTypes() {
        List<FuelTypeDTO> fuelTypes = new ArrayList<>();
        for (FuelType fuel : FuelType.values()) {
            FuelTypeDTO dto = new FuelTypeDTO(fuel.name(), fuel);
            fuelTypes.add(dto);
        }
        return fuelTypes;
    }

    @GET
    @Path("auctions")
    @Produces("application/json")
    public List<Auction> getAuctions() {
        return auctionRepo.findAll();
    }

    @GET
    @Path("auction/{auctionId}")
    @Produces("application/json")
    public Auction getAuction(@PathParam("auctionId") Long auctionId) {

        return auctionRepo.findById(auctionId);
    }

    @POST
    @Path("save")
    @Produces("application/json")
    public void saveAuction(AddAuctionRequest request) {

        Make make = makeRepo.findById(request.getMakeId());
        Model model = modelRepo.findById(request.getModelId());

        Auction auction = new Auction();
        auction.setTitle(request.getTitle());
        auction.setDescription(request.getDescription());

        auction.setProductionYear(toDate(request.getProductionYear()));
        auction.setMake(make);
        auction.setModel(model);
        auction.setColor(Color.valueOf(request.getColor()));
        auction.setFuelType(FuelType.valueOf(request.getFuel()));
        auction.setMilage(request.getMilage());
        auction.setPrice(request.getPrice());
        auction.setCreationDate(new Date());

        auctionRepo.save(auction);

    }

    private Date toDate(String date) {
        try {
            DateFormat dr = new SimpleDateFormat("yyyy");
            return dr.parse(date);
        } catch (ParseException ex) {
            LOG.log(Level.SEVERE, "Cannot parse date {0}", date);
            throw new RuntimeException(ex);
        }
    }
}

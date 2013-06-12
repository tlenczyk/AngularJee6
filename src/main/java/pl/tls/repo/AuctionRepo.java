package pl.tls.repo;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.Validate;
import pl.tls.entity.Auction;

@Stateless
public class AuctionRepo extends AbstractRepo<Auction> {

    public AuctionRepo() {
        super(Auction.class);
    }

    public List<Auction> getAuctionsByMark(String markName) {
        Validate.notBlank(markName);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Auction> criteria = cb.createQuery(Auction.class);

        Root<Auction> auction = criteria.from(Auction.class);
        criteria.select(auction);
        ParameterExpression<String> markNameParam = cb.parameter(String.class);

        Path<String> name = auction.get("mark").get("name");
//        Path<String> name = car.get(Auction_.mark).get(Mark_.name);
        criteria.where(cb.equal(name, markNameParam));

        List<Auction> resultList = em.createQuery(criteria)
                .setParameter(markNameParam, markName)
                .getResultList();

        return resultList;
    }
}

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

    public List<Auction> getAuctionsByMake(String makeName) {
        Validate.notBlank(makeName);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Auction> criteria = cb.createQuery(Auction.class);

        Root<Auction> auction = criteria.from(Auction.class);
        criteria.select(auction);
        ParameterExpression<String> makeNameParam = cb.parameter(String.class);

        Path<String> name = auction.get("make").get("name");
//        Path<String> name = car.get(Auction_.make).get(Make_.name);
        criteria.where(cb.equal(name, makeNameParam));

        List<Auction> resultList = em.createQuery(criteria)
                .setParameter(makeNameParam, makeName)
                .getResultList();

        return resultList;
    }
}

package pl.tls.repo;

import javax.ejb.Stateless;
import pl.tls.entity.Make;

@Stateless
public class MakeRepo extends AbstractRepo<Make> {

    public MakeRepo() {
        super(Make.class);
    }
}

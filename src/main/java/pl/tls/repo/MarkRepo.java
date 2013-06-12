package pl.tls.repo;

import javax.ejb.Stateless;
import pl.tls.entity.Mark;

@Stateless
public class MarkRepo extends AbstractRepo<Mark> {

    public MarkRepo() {
        super(Mark.class);
    }
}

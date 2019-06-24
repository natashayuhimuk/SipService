package repository;

import models.Authorization;
import models.User;

import javax.ejb.Remote;

@Remote
public interface AuthorizationDAO extends AbstractDAO<Long, Authorization> {
    Authorization findByUser(User user);
}

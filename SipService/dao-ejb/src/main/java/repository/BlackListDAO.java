package repository;

import models.BlackList;
import models.User;

import javax.ejb.Remote;

@Remote
public interface BlackListDAO extends AbstractDAO<Long, BlackList> {
    BlackList findByUser(User user);
}

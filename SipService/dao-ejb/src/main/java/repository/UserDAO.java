package repository;

import models.User;

import javax.ejb.Remote;

@Remote
public interface UserDAO extends AbstractDAO<String, User> {
}

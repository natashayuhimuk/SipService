package repository;

import models.Configuration;

import javax.ejb.Remote;

@Remote
public interface ConfigurationDAO extends AbstractDAO<String, Configuration> {
}

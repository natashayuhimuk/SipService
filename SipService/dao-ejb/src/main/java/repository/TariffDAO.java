package repository;

import models.Tariff;

import javax.ejb.Remote;

@Remote
public interface TariffDAO extends AbstractDAO<String, Tariff> {
}

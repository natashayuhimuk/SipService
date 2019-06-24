package repository.impl;

import lib.TypeLog;
import lombok.Setter;
import models.Tariff;
import repository.TariffDAO;
import service.LoggerService;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless(name = "TariffDAO", mappedName = "mappedTariffDAO")
@EJB(name = "java:global/TariffDAO", beanInterface = TariffDAO.class)
@Remote(TariffDAO.class)
@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
public class TariffDAOImpl implements TariffDAO {

    @Setter
    @PersistenceContext(unitName = "dao-sip")
    EntityManager entityManager;

    @Setter
    @EJB
    LoggerService LOGGER;

    @Override
    public boolean addAndUpdate(Tariff entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Tariff': addAndUpdate << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                LOGGER.addLog(TypeLog.INFO, "'Tariff': addAndUpdate entry = %s", new Object[]{entry});
                Tariff oldTariff = entityManager.find(Tariff.class, entry.getName());
                if (null == oldTariff) {
                    entityManager.persist(entry);
                } else {
                    entityManager.merge(entry);
                }
                this.entityManager.flush();
                status = true;
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Tariff': addAndUpdate -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Tariff': addAndUpdate status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public boolean delete(Tariff entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Tariff': delete << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                LOGGER.addLog(TypeLog.INFO, "'Tariff': delete entry = %s", new Object[]{entry});
                Tariff delCandidat = entityManager.find(Tariff.class, entry.getName());
                if (null != delCandidat) {
                    entityManager.remove(entityManager.contains(entry) ? entry : entityManager.merge(entry));
                    status = true;
                }
                entityManager.flush();
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Tariff': delete -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Tariff': delete status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public ArrayList<Tariff> findAll() {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Tariff': findAll << ", new Object[]{null});
        ArrayList<Tariff> resultList = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM tariffs", Tariff.class);
            List list = query.getResultList();
            if (!list.isEmpty()) {
                resultList = new ArrayList<>();
                for (Object obj : list)
                    resultList.add((Tariff) obj);
            }
            entityManager.flush();
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Tariff': findAll -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Tariff': findAll size list = %s >>", new Object[]{resultList != null ? resultList.size() : 0});
        return resultList;
    }

    @Override
    public Tariff findById(String key) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Tariff': findById << ", new Object[]{null});
        Tariff findTariff = null;
        try {
            LOGGER.addLog(TypeLog.INFO, "'Tariff': findById = %s ", new Object[]{key});
            if (!key.isEmpty() && key.toCharArray().length > 0) {
                findTariff = entityManager.find(Tariff.class, key);
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Tariff': findById -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Tariff': findById status = %s >>", new Object[]{findTariff != null ? true : false});
        return findTariff;
    }
}

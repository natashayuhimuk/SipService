package repository.impl;

import lib.TypeLog;
import models.Configuration;
import repository.ConfigurationDAO;
import service.LoggerService;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless(name = "ConfigurationDAO", mappedName = "mappedConfigurationDAO")
@EJB(name = "java:global/ConfigurationDAO", beanInterface = ConfigurationDAO.class)
@Remote(ConfigurationDAO.class)
@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
public class ConfigurationDAOImpl implements ConfigurationDAO {

    @PersistenceContext(unitName = "dao-sip")
    EntityManager entityManager;
    @EJB
    LoggerService LOGGER;

    @Override
    public boolean addAndUpdate(Configuration entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Configuration': addAndUpdate << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                LOGGER.addLog(TypeLog.INFO, "'Configuration': addAndUpdate entry = %s", new Object[]{entry});
                Configuration oldConf = entityManager.find(Configuration.class, entry.getName());
                if (null == oldConf) {
                    entityManager.persist(entry);
                } else {
                    entityManager.merge(entry);
                }
                this.entityManager.flush();
                status = true;
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Configuration': addAndUpdate -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Configuration': addAndUpdate status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public boolean delete(Configuration entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Configuration': delete << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                LOGGER.addLog(TypeLog.INFO, "'Configuration': delete entry = %s", new Object[]{entry});
                Configuration delCandidat = entityManager.find(Configuration.class, entry.getName());
                if (null != delCandidat) {
                    entityManager.remove(entityManager.contains(entry) ? entry : entityManager.merge(entry));
                    status = true;
                }
                entityManager.flush();
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Configuration': delete -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Configuration': delete status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public ArrayList<Configuration> findAll() {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Configuration': findAll << ", new Object[]{null});
        ArrayList<Configuration> resultList = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM configurations", Configuration.class);
            List list = query.getResultList();
            if (!list.isEmpty()){
                resultList = new ArrayList<>();
                for (Object obj : list)
                    resultList.add((Configuration) obj);
            }
            entityManager.flush();
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Configuration': findAll -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Configuration': findAll size list = %s >>", new Object[]{resultList != null ? resultList.size() : 0});
        return resultList;
    }

    @Override
    public Configuration findById(String key) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Configuration': findById << ", new Object[]{null});
        Configuration findConf = null;
        try {
            LOGGER.addLog(TypeLog.INFO, "'Configuration': findById = %s ", new Object[]{key});
            if (!key.isEmpty() && key.toCharArray().length > 0) {
                findConf = entityManager.find(Configuration.class, key);
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Configuration': findById -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Configuration': findById status = %s >>", new Object[]{findConf != null ? true : false});
        return findConf;
    }
}

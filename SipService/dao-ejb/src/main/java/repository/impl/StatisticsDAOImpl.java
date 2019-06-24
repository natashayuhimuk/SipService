package repository.impl;

import lib.TypeLog;
import lib.TypeStatistic;
import models.Statistics;
import repository.StatisticsDAO;
import service.LoggerService;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless(name = "StatisticsDAO", mappedName = "mappedStatisticsDAO")
@EJB(name = "java:global/StatisticsDAO", beanInterface = StatisticsDAO.class)
@Remote(StatisticsDAO.class)
@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
public class StatisticsDAOImpl implements StatisticsDAO {

    @PersistenceContext(unitName = "dao-sip")
    EntityManager entityManager;

    @EJB
    LoggerService LOGGER;

    @Override
    public boolean addAndUpdate(Statistics entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Statistic': addAndUpdate << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                LOGGER.addLog(TypeLog.INFO, "'Statistic': addAndUpdate entry = %s", new Object[]{entry});
                Statistics oldStatistics = entityManager.find(Statistics.class, entry.getName());
                if (null == oldStatistics) {
                    entityManager.persist(entry);
                } else {
                    entityManager.merge(entry);
                }
                this.entityManager.flush();
                status = true;
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Statistic': addAndUpdate -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Statistic': addAndUpdate status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public boolean delete(Statistics entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Statistic': delete << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                LOGGER.addLog(TypeLog.INFO, "'Statistic': delete entry = %s", new Object[]{entry});
                Statistics delCandidat = entityManager.find(Statistics.class, entry.getName());
                if (null != delCandidat) {
                    entityManager.remove(entityManager.contains(entry) ? entry : entityManager.merge(entry));
                    status = true;
                }
                entityManager.flush();
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Statistic': delete -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Statistic': delete status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public ArrayList<Statistics> findAll() {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Statistic': findAll << ", new Object[]{null});
        ArrayList<Statistics> resultList = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM statistics", Statistics.class);
            List list = query.getResultList();
            if (!list.isEmpty()){
                resultList = new ArrayList<>();
                for (Object obj : list)
                    resultList.add((Statistics) obj);
            }
            entityManager.flush();
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Statistic': findAll -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Statistic': findAll size list = %s >>", new Object[]{resultList != null ? resultList.size() : 0});
        return resultList;
    }

    @Override
    public Statistics findById(String key) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Statistic': findById << ", new Object[]{null});
        Statistics findStatistics = null;
        try {
            LOGGER.addLog(TypeLog.INFO, "'Statistic': findById = %s ", new Object[]{key});
            if (!key.isEmpty() && key.toCharArray().length > 0) {
                findStatistics = entityManager.find(Statistics.class, key);
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Statistic': findById -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Statistic': findById status = %s >>", new Object[]{findStatistics != null ? true : false});
        return findStatistics;
    }

    @Override
    public boolean addAndUpdate(HashMap<TypeStatistic, Statistics> entries) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Statistic': addAndUpdate(HashMap) << size = %s", new Object[]{entries != null ? entries.size() : 0});
        boolean status = false;
        try {
            if (null != entries) {
                for (Statistics entry : entries.values()){
                    LOGGER.addLog(TypeLog.INFO, "'Statistic': addAndUpdate(HashMap) entry = %s", new Object[]{entry});
                    Statistics oldEntry = entityManager.find(Statistics.class, entry.getName());
                    if (null == oldEntry){
                        entityManager.persist(entry);
                    }else {
                        entityManager.merge(entry);
                    }
                }
                this.entityManager.flush();
                status = true;
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Statistic': addAndUpdate(HashMap) -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Statistic': addAndUpdate(HashMap) status = %s >>", new Object[]{status});
        return status;
    }
}

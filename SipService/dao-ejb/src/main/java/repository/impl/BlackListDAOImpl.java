package repository.impl;

import lib.TypeLog;
import models.BlackList;
import models.User;
import repository.BlackListDAO;
import service.LoggerService;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless(name = "BlackListDAO", mappedName = "mappedBlackListDAO")
@EJB(name = "java:global/BlackListDAO", beanInterface = BlackListDAO.class)
@Remote(BlackListDAO.class)
@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
public class BlackListDAOImpl implements BlackListDAO {

    @PersistenceContext(unitName = "dao-sip")
    EntityManager entityManager;

    @EJB
    LoggerService LOGGER;

    @Override
    public boolean addAndUpdate(BlackList entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'BlackList': addAndUpdate << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                if (entry.getId() != null) {
                    LOGGER.addLog(TypeLog.INFO, "'BlackList': addAndUpdate entry = %s", new Object[]{entry});
                    BlackList oldBL = entityManager.find(BlackList.class, entry.getId());
                    if (null == oldBL) {
                        entityManager.persist(entry);
                    } else {
                        entityManager.merge(entry);
                    }
                }else {
                    LOGGER.addLog(TypeLog.INFO, "'BlackList': addAndUpdate entry = %s", new Object[]{entry});
                    entityManager.persist(entry);
                }
                this.entityManager.flush();
                status = true;
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'BlackList': addAndUpdate -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'BlackList': addAndUpdate status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public boolean delete(BlackList entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'BlackList': delete << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                LOGGER.addLog(TypeLog.INFO, "'BlackList': delete entry = %s", new Object[]{entry});
                BlackList delCandidat = entityManager.find(BlackList.class, entry.getId());
                if (null != delCandidat) {
                    entityManager.remove(entityManager.contains(entry) ? entry : entityManager.merge(entry));
                    status = true;
                }
                entityManager.flush();
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'BlackList': delete -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'BlackList': delete status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public ArrayList<BlackList> findAll() {
        LOGGER.addLog(TypeLog.INFO, "Entering 'BlackList': findAll << ", new Object[]{null});
        ArrayList<BlackList> resultList = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM blacklist", BlackList.class);
            List list = query.getResultList();
            if (!list.isEmpty()){
                resultList = new ArrayList<>();
                for (Object obj : list)
                    resultList.add((BlackList) obj);
            }
            entityManager.flush();
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'BlackList': findAll -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'BlackList': findAll size list = %s >>", new Object[]{resultList != null ? resultList.size() : 0});
        return resultList;
    }

    @Override
    public BlackList findById(Long key) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'BlackList': findById << ", new Object[]{null});
        BlackList findBL = null;
        try {
            LOGGER.addLog(TypeLog.INFO, "'BlackList': findById = %s ", new Object[]{key});
            if (null != key) {
                findBL = entityManager.find(BlackList.class, key);
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'BlackList': findById -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'BlackList': findById status = %s >>", new Object[]{findBL != null ? true : false});
        return findBL;
    }

    @Override
    public BlackList findByUser(User user) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'BlackList': findByUser << ", new Object[]{null});
        BlackList userBL = null;
        try {
            LOGGER.addLog(TypeLog.INFO, "'BlackList': findByUser = %s ", new Object[]{user});
            if (null != user) {
                Query query = entityManager.createNativeQuery("SELECT * FROM blacklist WHERE phoneNumber = ?",BlackList.class);
                query.setParameter(1, user.getPhoneNumber());
                userBL = (BlackList) query.getSingleResult();
            }
            LOGGER.addLog(TypeLog.INFO, "'BlackList': findByUser  userBL = %s ", new Object[]{userBL});
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'BlackList': findByUser -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'BlackList': findByUser status = %s >>", new Object[]{userBL != null ? true : false});
        return userBL;
    }
}

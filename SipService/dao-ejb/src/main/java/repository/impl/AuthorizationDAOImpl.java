package repository.impl;

import lib.TypeLog;
import models.Authorization;
import models.User;
import repository.AuthorizationDAO;
import service.LoggerService;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless(name = "AuthorizationDAO", mappedName = "mappedAuthorizationDAO")
@EJB(name = "java:global/AuthorizationDAO", beanInterface = AuthorizationDAO.class)
@Remote(AuthorizationDAO.class)
@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
public class AuthorizationDAOImpl implements AuthorizationDAO {

    @PersistenceContext(unitName = "dao-sip")
    EntityManager entityManager;

    @EJB
    LoggerService LOGGER;

    @Override
    public boolean addAndUpdate(Authorization entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Authorization': addAndUpdate << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                if (entry.getId() != null) {
                    LOGGER.addLog(TypeLog.INFO, "'Authorization': addAndUpdate entry = %s", new Object[]{entry});
                    Authorization oldAuth = entityManager.find(Authorization.class, entry.getId());
                    if (null == oldAuth) {
                        entityManager.persist(entry);
                    } else {
                        entityManager.merge(entry);
                    }
                }else {
                    LOGGER.addLog(TypeLog.INFO, "'Authorization': addAndUpdate entry = %s", new Object[]{entry});
                    entityManager.persist(entry);
                }
                this.entityManager.flush();
                status = true;
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Authorization': addAndUpdate -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Authorization': addAndUpdate status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public boolean delete(Authorization entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Authorization': delete << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                LOGGER.addLog(TypeLog.INFO, "'Authorization': delete entry = %s", new Object[]{entry});
                Authorization delCandidat = entityManager.find(Authorization.class, entry.getId());
                if (null != delCandidat) {
                    entityManager.remove(entityManager.contains(entry) ? entry : entityManager.merge(entry));
                    status = true;
                }
                entityManager.flush();
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Authorization': delete -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Authorization': delete status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public ArrayList<Authorization> findAll() {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Authorization': findAll << ", new Object[]{null});
        ArrayList<Authorization> resultList = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM authorization", Authorization.class);
            List list = query.getResultList();
            if (!list.isEmpty()){
                resultList = new ArrayList<>();
                for (Object obj : list)
                    resultList.add((Authorization) obj);
            }
            entityManager.flush();
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Authorization': findAll -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Authorization': findAll size list = %s >>", new Object[]{resultList != null ? resultList.size() : 0});
        return resultList;
    }

    @Override
    public Authorization findById(Long key) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Authorization': findById << ", new Object[]{null});
        Authorization findAuth = null;
        try {
            LOGGER.addLog(TypeLog.INFO, "'Authorization': findById = %s ", new Object[]{key});
            if (null != key) {
                findAuth = entityManager.find(Authorization.class, key);
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Authorization': findById -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Authorization': findById status = %s >>", new Object[]{findAuth != null ? true : false});
        return findAuth;
    }

    @Override
    public Authorization findByUser(User user) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'Authorization': findByUser << ", new Object[]{null});
        Authorization userAuth = null;
        try {
            LOGGER.addLog(TypeLog.INFO, "'Authorization': findByUser = %s ", new Object[]{user});
            if (null != user) {
                Query query = entityManager.createNativeQuery("SELECT * FROM `authorization` WHERE phoneNumber = ?",Authorization.class);
                query.setParameter(1, user.getPhoneNumber());
                userAuth = (Authorization) query.getSingleResult();
            }
            LOGGER.addLog(TypeLog.INFO, "'Authorization': findByUser  userAuth = %s ", new Object[]{userAuth});
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'Authorization': findByUser -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'Authorization': findByUser status = %s >>", new Object[]{userAuth != null ? true : false});
        return userAuth;
    }
}

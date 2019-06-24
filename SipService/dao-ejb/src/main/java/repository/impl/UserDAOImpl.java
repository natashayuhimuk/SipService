package repository.impl;

import lib.TypeLog;
import models.User;
import repository.UserDAO;
import service.LoggerService;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@EJB(name = "java:global/UserDAO", beanInterface = UserDAO.class)
@Stateless(name = "UserDAO", mappedName = "mappedUserDAO")
@Remote(UserDAO.class)
@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(unitName = "dao-sip")
    EntityManager entityManager;

    @EJB
    LoggerService LOGGER;

    @Override
    public boolean addAndUpdate(User entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'USER': addAndUpdate << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                LOGGER.addLog(TypeLog.INFO, "'USER': addAndUpdate entry = %s", new Object[]{entry});
                User oldUser = entityManager.find(User.class, entry.getPhoneNumber());
                if (null == oldUser) {
                    entityManager.persist(entry);
                } else {
                    entityManager.merge(entry);
                }
                this.entityManager.flush();
                status = true;
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'USER': addAndUpdate -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'USER': addAndUpdate status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public boolean delete(User entry) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'USER': delete << ", new Object[]{null});
        boolean status = false;
        try {
            if (null != entry) {
                LOGGER.addLog(TypeLog.INFO, "'USER': delete entry = %s", new Object[]{entry});
                User delCandidat = entityManager.find(User.class, entry.getPhoneNumber());
                if (null != delCandidat) {
                    entityManager.remove(entityManager.contains(entry) ? entry : entityManager.merge(entry));
                    status = true;
                }
                entityManager.flush();
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'USER': delete -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'USER': delete status = %s >>", new Object[]{status});
        return status;
    }

    @Override
    public ArrayList<User> findAll() {
        LOGGER.addLog(TypeLog.INFO, "Entering 'USER': findAll << ", new Object[]{null});
        ArrayList<User> resultList = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM users", User.class);
            List list = query.getResultList();
            if (!list.isEmpty()) {
                resultList = new ArrayList<>();
                for (Object obj : list)
                    resultList.add((User) obj);
            }
            entityManager.flush();
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'USER': findAll -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'USER': findAll size list = %s >>", new Object[]{resultList != null ? resultList.size() : 0});
        return resultList;
    }

    @Override
    public User findById(String key) {
        LOGGER.addLog(TypeLog.INFO, "Entering 'USER': findById << ", new Object[]{null});
        User findUser = null;
        try {
            LOGGER.addLog(TypeLog.INFO, "'USER': findById = %s ", new Object[]{key});
            if (!key.isEmpty() && key.toCharArray().length > 0) {
                findUser = entityManager.find(User.class, key);
            }
        } catch (Exception ex) {
            LOGGER.addLog(TypeLog.ERRORR, "'USER': findById -> Unexpected error", new Object[]{ex});
        }
        LOGGER.addLog(TypeLog.INFO, "'USER': findById status = %s >>", new Object[]{findUser != null ? true : false});
        return findUser;
    }
}

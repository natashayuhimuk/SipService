package service.impl;

import lib.TypeConfiguration;
import lib.TypeStatistic;
import models.Statistics;
import repository.ConfigurationDAO;
import repository.StatisticsDAO;
import service.StatisticsService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.NoSuchEJBException;
import javax.ejb.Remote;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;


@Startup
@Stateful(name = "StatisticsService", mappedName = "mappedStatisticsService")
@EJB(name = "java:global/StatisticsService", beanInterface = StatisticsService.class)
@Remote(StatisticsService.class)
@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
public class StatisticsServiceImpl implements StatisticsService {

    public static HashMap<TypeStatistic, Statistics> mapStat;
    private static UpdateStatisticsThread upd;

    @EJB
    StatisticsDAO statisticsDAO;

    @EJB
    ConfigurationDAO configurationDAO;

    @Override
    public void receivedCall() {
        mapStat.get(TypeStatistic.RECEIVED_CALLS).getDescription().incrementAndGet();
    }

    @Override
    public void successfulNormalize() {
        mapStat.get(TypeStatistic.SUCCESSFUL_NORMALIZ).getDescription().incrementAndGet();
    }

    @Override
    public void unSuccessfulNormalize() {
        mapStat.get(TypeStatistic.UNSUCCESSFUL_NORMALIZ).getDescription().incrementAndGet();
    }

    @Override
    public void successfulRedirection() {
        mapStat.get(TypeStatistic.SUCCESSFUL_REDIRECTIONS).getDescription().incrementAndGet();
    }

    @Override
    public void unSuccessfulRedirection() {
        mapStat.get(TypeStatistic.UNSUCCESSFUL_REDIRECTIONS).getDescription().incrementAndGet();
    }

    private void updateStatistics() {
        try {
            ArrayList<Statistics> list = statisticsDAO.findAll();
            if (null == list){
                mapStat.clear();
                for (TypeStatistic type : TypeStatistic.values()) {
                    Statistics statistics = new Statistics(type.getType(), new AtomicLong());
                    mapStat.put(type, statistics);
                }
            }
            statisticsDAO.addAndUpdate(mapStat);
        }catch (NoSuchEJBException ex){
            destroy();
        }
    }

    @PostConstruct
    private void init() {
        if (mapStat == null)
            mapStat = new HashMap<>();
        ArrayList<Statistics> list = statisticsDAO.findAll();
        if (list == null) {
            for (TypeStatistic type : TypeStatistic.values()) {
                Statistics statistics = new Statistics(type.getType(), new AtomicLong());
                mapStat.put(type, statistics);
            }
        } else {
            for (TypeStatistic type : TypeStatistic.values()) {
                for (Statistics stat : list) {
                    if (type.getType().equals(stat.getName())) {
                        stat.setDescription(new AtomicLong(0));
                        mapStat.put(type, stat);
                        break;
                    }
                }
            }
        }
        long time = Long.parseLong(configurationDAO.findById(TypeConfiguration.TIMER.getType()).getDescription());
        if (upd == null) {
            upd = new UpdateStatisticsThread(time);
            upd.start();
        }
    }

    @PreDestroy
    private void destroy() {
        if (upd != null && !upd.isInterrupted())
            upd.isInterrupted();
    }

    private class UpdateStatisticsThread extends Thread {

        private long timeUpdate;

        UpdateStatisticsThread(long timeUpdate) {
            this.timeUpdate = timeUpdate;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                updateStatistics();
                try {
                    sleep(timeUpdate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeUpdate = Long.parseLong(configurationDAO.findById(TypeConfiguration.TIMER.getType()).getDescription());
            }
        }
    }

}

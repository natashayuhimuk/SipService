package repository;

import lib.TypeStatistic;
import models.Statistics;

import javax.ejb.Remote;
import java.util.HashMap;

@Remote
public interface StatisticsDAO extends AbstractDAO<String, Statistics> {
    boolean addAndUpdate(HashMap<TypeStatistic, Statistics> entries);
}

package service;

import javax.ejb.Remote;

@Remote
public interface StatisticsService {

    void receivedCall();

    void successfulNormalize();

    void unSuccessfulNormalize();

    void successfulRedirection();

    void unSuccessfulRedirection();
}

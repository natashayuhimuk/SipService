package controller;

import filters.PhoneChecker;
import lib.TypeLog;
import models.User;
import repository.UserDAO;
import service.LoggerService;
import service.StatisticsService;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.sip.Proxy;
import javax.servlet.sip.SipErrorEvent;
import javax.servlet.sip.SipErrorListener;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import java.io.IOException;

public class SipController extends SipServlet implements SipErrorListener, Servlet {

    @EJB
    PhoneChecker phoneChecker;
    @EJB
    StatisticsService statService;
    @EJB
    LoggerService LOGGER;
    @EJB
    UserDAO userDAO;
    @Resource
    SipFactory sipFactory;

    @Override
    protected void doInvite(SipServletRequest sipServletRequest) throws ServletException, IOException {
        LOGGER.addLog(TypeLog.INFO, "Entering 'SipController': doInvite << ", new Object[]{null});
        boolean status = false;
        if (sipServletRequest.isInitial()) {
            LOGGER.addLog(TypeLog.INFO, "'SipController': doInvite = %s ", new Object[]{sipServletRequest});
            statService.receivedCall();
            Proxy proxy = sipServletRequest.getProxy();
            proxy.setRecordRoute(true);
            proxy.setSupervised(true);
            if (phoneChecker.isValidNumber(sipServletRequest.getTo().getDisplayName())) {
                String normPN = phoneChecker.normalizeNumber(sipServletRequest.getTo().getDisplayName());
                statService.successfulNormalize();
                if (checkBalance(normPN)) {
                    LOGGER.addLog(TypeLog.INFO, "'SipController': doInvite -> checkBalance = %s >> ",
                            new Object[]{true});
                    sipServletRequest.setRequestURI(sipFactory.createURI(sipServletRequest.getRequestURI()
                            .toString().replace(sipServletRequest.getTo().getDisplayName(), normPN)));
                    LOGGER.addLog(TypeLog.INFO, "'SipController': doInvite -> proxyTo = %s >> ",
                            new Object[]{sipServletRequest.getRequestURI()});
                    proxy.proxyTo(sipServletRequest.getRequestURI());
                } else {
                    LOGGER.addLog(TypeLog.INFO,
                            "'SipController': doInvite -> checkBalance = %s ", new Object[]{false});
                    LOGGER.addLog(TypeLog.INFO, "'SipController': doInvite -> response = %s >> ",
                            new Object[]{SipServletResponse.SC_FORBIDDEN});
                    statService.unSuccessfulRedirection();
                    SipServletResponse resp = sipServletRequest.createResponse(SipServletResponse.SC_FORBIDDEN,
                            "No Money Left on RFC Account");
                    resp.send();
                }
            } else {
                statService.unSuccessfulNormalize();
                statService.unSuccessfulRedirection();
                LOGGER.addLog(TypeLog.INFO, "'SipController': doInvite -> response = %s >> ",
                        new Object[]{SipServletResponse.SC_FORBIDDEN});
                SipServletResponse resp = sipServletRequest.createResponse(SipServletResponse.SC_FORBIDDEN,
                        "Number is no valid!");
                resp.send();
            }
        }
    }

    @Override
    protected void doBye(SipServletRequest sipServletRequest) throws ServletException, IOException {
        LOGGER.addLog(TypeLog.INFO, "'SipController': doBye -> status = %s >> ", new Object[]{true});
        statService.successfulRedirection();
        super.doBye(sipServletRequest);
    }

    protected boolean checkBalance(String userPN) {
        userPN = userPN.replace("+", "");
        User user = userDAO.findById(userPN);
        if (null != user) {
            double userBalance = user.getBalance();
            double tariffCost = user.getTariff().getCost();
            if (userBalance < 0 || (userBalance - tariffCost) < 0)
                return false;
            else {
                user.setBalance(Double.parseDouble(String.format("%.2f", (userBalance - tariffCost))
                        .replace(",", ".")));
                userDAO.addAndUpdate(user);
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    protected void doErrorResponse(SipServletResponse sipServletResponse) throws ServletException, IOException {
        statService.unSuccessfulRedirection();
        super.doErrorResponse(sipServletResponse);
    }

    @Override
    public void noAckReceived(SipErrorEvent sipErrorEvent) {
    }

    @Override
    public void noPrackReceived(SipErrorEvent sipErrorEvent) {

    }
}

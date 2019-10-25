package com.PinkyUni.model.service;

import com.PinkyUni.model.service.impl.TourAgencyService;
import com.PinkyUni.model.service.impl.TourUserService;

public class ServiceFactory {

    private static final ServiceFactory serviceFactory = new ServiceFactory();

    private final AgencyService agencyService = new TourAgencyService();
    private final UserService userService = new TourUserService();

    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }

    public AgencyService getAgencyService() {
        return agencyService;
    }

    public UserService getUserService() {
        return userService;
    }
}

package com.PinkyUni.model.service;

public class ServiceFactory {

    private static final ServiceFactory serviceFactory = new ServiceFactory();

    private final AgencyService agencyService = new TourAgencyService();

    private ServiceFactory(){}

    public static ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public AgencyService getAgencyService() {
        return agencyService;
    }
}

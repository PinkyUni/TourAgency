package com.PinkyUni.model.service;

public class ServiceFactory {

    private final ServiceFactory serviceFactory = new ServiceFactory();

    private final AgencyService agencyService = new TourAgencyService();

    private ServiceFactory(){}

    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public AgencyService getAgencyService() {
        return agencyService;
    }
}

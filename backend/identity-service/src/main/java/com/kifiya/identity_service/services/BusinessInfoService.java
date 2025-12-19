package com.kifiya.identity_service.services;

import com.kifiya.identity_service.dto.BusinessInfoRequest;
import com.kifiya.identity_service.repository.BusinessInfoRepository;
import com.kifiya.identity_service.repository.domain.BusinessInfo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BusinessInfoService {

    private final BusinessInfoRepository businessInfoRepository;

    public BusinessInfoService(BusinessInfoRepository businessInfoRepository) {
        this.businessInfoRepository = businessInfoRepository;
    }

    public List<BusinessInfo> getBusinessInfoByCustomerId(String customerId) {
        List<BusinessInfo> businessInfoList = businessInfoRepository.findByCustomerId(customerId);
        if (businessInfoList.isEmpty()) {
            throw new EntityNotFoundException("business information not found for the specified customer ID");
        }
        return businessInfoList;
    }

    public BusinessInfo registerBusinessInfo(BusinessInfoRequest request) {
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setCustomerId(request.getCustomerId());
        businessInfo.setBusinessAssociationType(request.getBusinessAssociationType());
        businessInfo.setBusinessSector(request.getBusinessSector());
        businessInfo.setBusinessLevel(request.getBusinessLevel());
        businessInfo.setBusinessEstablishmentYear(request.getBusinessEstablishmentYear());
        businessInfo.setBusinessStartingNoOfEmployees(request.getBusinessStartingNoOfEmployees());
        businessInfo.setBusinessCurrentNoOfEmployees(request.getBusinessCurrentNoOfEmployees());
        businessInfo.setBusinessStartingCapital(request.getBusinessStartingCapital());
        businessInfo.setBusinessCurrentCapital(request.getBusinessCurrentCapital());
        businessInfo.setBusinessSourceOfInitialCapital(request.getBusinessSourceOfInitialCapital());
        businessInfo.setBusinessAnnualSales(request.getBusinessAnnualSales());
        businessInfo.setBusinessAnnualProfit(request.getBusinessAnnualProfit());
        businessInfo.setBusinessRegion(request.getBusinessRegion());
        businessInfo.setBusinessCity(request.getBusinessCity());
        businessInfo.setBusinessZoneOrSubCity(request.getBusinessZoneOrSubCity());
        businessInfo.setBusinessWoreda(request.getBusinessWoreda());

        return businessInfoRepository.save(businessInfo);
    }
}


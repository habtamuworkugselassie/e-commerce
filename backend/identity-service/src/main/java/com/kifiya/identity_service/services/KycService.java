package com.kifiya.identity_service.services;

import com.kifiya.identity_service.dto.KycRequest;
import com.kifiya.identity_service.repository.KycRepository;
import com.kifiya.identity_service.repository.domain.Kyc;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class KycService {

    private final KycRepository kycRepository;

    public KycService(KycRepository kycRepository) {
        this.kycRepository = kycRepository;
    }

    public List<Kyc> getKycByCustomerId(String customerId) {
        List<Kyc> kycList = kycRepository.findByCustomerId(customerId);
        if (kycList.isEmpty()) {
            throw new EntityNotFoundException("customer kyc not found for the specified customer ID");
        }
        return kycList;
    }

    public Kyc registerKyc(KycRequest request) {
        Kyc kyc = new Kyc();
        kyc.setCustomerId(request.getCustomerId());
        kyc.setCustomerGender(request.getCustomerGender());
        kyc.setCustomerAge(request.getCustomerAge());
        kyc.setCustomerMaritalStatus(request.getCustomerMaritalStatus());
        kyc.setCustomerEducationLevel(request.getCustomerEducationLevel());
        kyc.setCustomerRegion(request.getCustomerRegion());
        kyc.setCustomerCity(request.getCustomerCity());
        kyc.setCustomerZoneOrSubCity(request.getCustomerZoneOrSubCity());
        kyc.setCustomerWoreda(request.getCustomerWoreda());
        kyc.setSalaryBankAccountNumber(request.getSalaryBankAccountNumber());
        kyc.setCompanyName(request.getCompanyName());

        return kycRepository.save(kyc);
    }
}


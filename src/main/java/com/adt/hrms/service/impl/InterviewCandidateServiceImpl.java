package com.adt.hrms.service.impl;

import com.adt.hrms.model.InterviewCandidateDetails;
import com.adt.hrms.repository.InterviewCandidateRepo;
import com.adt.hrms.service.InterviewCandidateService;
import com.adt.hrms.util.MobileNumberValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class InterviewCandidateServiceImpl implements InterviewCandidateService {

    private static final Logger LOGGER = LogManager.getLogger(InterviewCandidateServiceImpl.class);

    @Autowired
    InterviewCandidateRepo interviewCandidateRepo;

    @Autowired
    private MessageSource messageSource;

    @Override
    public String saveInterviewCandidateDetail(InterviewCandidateDetails interviewCandidateDetails) {
        List<InterviewCandidateDetails> opt = interviewCandidateRepo.findCandidateDetailsByEmailId(interviewCandidateDetails.getEmailId());
        if(opt.size()>0)
            return "Interview Candidate With Id: " + opt.get(0).getCandidateId() + " is Already Present";
        if(!MobileNumberValidation.isValidMobileNo(interviewCandidateDetails.getContactNo()))
            return "Interview Candidate Contact Number: " + opt.get(0).getCandidateId() + " is Invalid";
        return "Candidate With Id: "+interviewCandidateRepo.save(interviewCandidateDetails).getCandidateId()+" is Successfully Saved";
    }

    @Override
    public List<InterviewCandidateDetails> getAllInterviewCandidateDetail() {
        List<InterviewCandidateDetails> interviewCandidateDetails = interviewCandidateRepo.findAll();
        return interviewCandidateDetails;
    }

    @Override
    public String updateInterviewCandidateDetail(int candidateId, InterviewCandidateDetails interviewCandidateDetails) {
        Optional<InterviewCandidateDetails> candidateDetails = interviewCandidateRepo.findById(candidateId);
        if (!candidateDetails.isPresent())
            return "Candidate With Id: "+candidateId+" is Not Present";
        if(!MobileNumberValidation.isValidMobileNo(interviewCandidateDetails.getContactNo()))
            return "Candidate With Id: "+candidateId+" is Invalid Contact Number";
        candidateDetails.get().setCandidateName(interviewCandidateDetails.getCandidateName());
        candidateDetails.get().setEmailId(interviewCandidateDetails.getEmailId());
        candidateDetails.get().setContactNo(interviewCandidateDetails.getContactNo());
        candidateDetails.get().setAddress(interviewCandidateDetails.getAddress());
        candidateDetails.get().setCvShortlisted(interviewCandidateDetails.isCvShortlisted());
        candidateDetails.get().setLastCTC(interviewCandidateDetails.getLastCTC());
        candidateDetails.get().setHighestQualification(interviewCandidateDetails.getHighestQualification());
        candidateDetails.get().setNoticePeriod(interviewCandidateDetails.getNoticePeriod());
        candidateDetails.get().setTechnicalStack(interviewCandidateDetails.getTechnicalStack());
        candidateDetails.get().setWorkExperience(interviewCandidateDetails.getWorkExperience());
        interviewCandidateRepo.save(candidateDetails.get());
        return "Successfully Updated";
    }

    @Override
    public InterviewCandidateDetails getInterviewCandidateById(int candidateId) {
        Optional<InterviewCandidateDetails> candidateDetail = interviewCandidateRepo.findById(candidateId);
        if (!candidateDetail.isPresent()) {
            String message = messageSource.getMessage("api.error.data.not.found.id", null, Locale.ENGLISH);
            LOGGER.error(message=message+candidateId);
            throw new EntityNotFoundException(message);
        }
        return candidateDetail.get();
    }

    @Override
    public String deleteInterviewCandidateById(int candidateId) {
        if(interviewCandidateRepo.existsById(candidateId)) {
            interviewCandidateRepo.deleteById(candidateId);
            return "Candidate With id: " + candidateId + " is Successfully Deleted";
        }
        return "Candidate With id: " + candidateId + " is Not Present";
    }
}

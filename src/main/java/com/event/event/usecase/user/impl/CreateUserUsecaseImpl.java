package com.event.event.usecase.user.impl;

import com.event.event.common.exception.DuplicateEmailException;
import com.event.event.entity.ReferralHistory;
import com.event.event.entity.Role;
import com.event.event.entity.User;
import com.event.event.infrastructure.points.events.ReferralEventReferee;
import com.event.event.infrastructure.users.dto.CreateUserRequestDTO;
import com.event.event.infrastructure.users.repository.RoleRepository;
import com.event.event.infrastructure.users.repository.UsersRepository;
import com.event.event.infrastructure.users.utils.ReferralCodeService;
import com.event.event.usecase.referrals.ReferralHistoryUsecase;
import com.event.event.usecase.user.CreateUserUsecase;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CreateUserUsecaseImpl implements CreateUserUsecase {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ReferralCodeService referralCodeService;
    private final ReferralHistoryUsecase referralHistoryUsecase;
    private final ApplicationEventPublisher applicationEventPublisher;


    public CreateUserUsecaseImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository, ReferralCodeService referralCodeService,
                                 ReferralHistoryUsecase referralHistoryUsecase, ApplicationEventPublisher applicationEventPublisher) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.referralCodeService = referralCodeService;
        this.referralHistoryUsecase = referralHistoryUsecase;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @Transactional
    public User createUser(CreateUserRequestDTO req) {
        User newUser = req.toEntity();

        if (req.getReferralCode() != null) {
            log.info("User has a referral code");
            try{
                 usersRepository.findByReferralCode(req.getReferralCode())
                         .ifPresent(referredUser -> {
                             Long referredUserId = referredUser.getId();
                             newUser.setReferredBy(referredUserId);
                             log.info("User with ID {} was referred by user with ID {}", newUser.getId(), referredUserId);
                         });
                }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        Optional<Role> defaultRole = roleRepository.findByName("USER");
        String referralCode = referralCodeService.generateUniqueReferralCode();
        newUser.setReferralCode(referralCode);
        if (defaultRole.isPresent()) {
            newUser.getRoles().add(defaultRole.get());
        } else {
            throw new RuntimeException("Default role not found");
        }

        try {
            usersRepository.save(newUser);
        }catch (DataIntegrityViolationException e){
            String email = req.getEmail();
            throw new DuplicateEmailException((email + "Email already exists"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (newUser.getReferredBy() != null){
            Long idReferree = newUser.getId();
            Long idReferrer = newUser.getReferredBy();
            try{
                ReferralHistory referralHistory = referralHistoryUsecase.createReferralHistory(idReferrer, idReferree);
            }catch (Exception e){
                e.printStackTrace();
            }
            applicationEventPublisher.publishEvent(new ReferralEventReferee(this,idReferrer,idReferree));
        }
        return newUser;
    }

    @Override
    @Transactional
    public User createEventOrgUser(CreateUserRequestDTO req) {
        User newUser = req.toEntity();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        Optional<Role> defaultRole = roleRepository.findByName("EVENT_ORGANIZER");
        if (defaultRole.isPresent()) {
            newUser.getRoles().add(defaultRole.get());
        } else {
            throw new RuntimeException("Default role not found");
        }

        try {
            usersRepository.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }

}

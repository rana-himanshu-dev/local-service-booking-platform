package com.localservice.bookingplatform.service;

import com.localservice.bookingplatform.dto.CreateServiceProviderRequest;
import com.localservice.bookingplatform.dto.EarningsResponse;
import com.localservice.bookingplatform.dto.ServiceProviderResponse;
import com.localservice.bookingplatform.dto.TransactionResponse;
import com.localservice.bookingplatform.enums.ApprovalStatus;
import com.localservice.bookingplatform.model.Booking;
import com.localservice.bookingplatform.model.ServiceCategory;
import com.localservice.bookingplatform.model.ServiceProvider;
import com.localservice.bookingplatform.model.User;
import com.localservice.bookingplatform.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProviderService {

    private final ServiceProviderRepository providerRepository;
    private final UserRepository userRepository;
    private final ServiceCategoryRepository categoryRepository;
    private final EmailService emailService;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public ProviderService(ServiceProviderRepository providerRepository,
                           UserRepository userRepository,
                           ServiceCategoryRepository categoryRepository, EmailService emailService, BookingRepository bookingRepository, PaymentRepository paymentRepository) {
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.emailService = emailService;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
    }

    private String getCurrentUserEmail() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();  // returns email (subject)
    }

    public ServiceProviderResponse createProfile(
            CreateServiceProviderRequest request) {

        String email = getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "User not found: " + email));

        if (!user.getRole().name().equals("PROVIDER")) {
            throw new RuntimeException(
                    "Only PROVIDER role can create a profile");
        }

        if (providerRepository.findByUser(user).isPresent()) {
            throw new RuntimeException(
                    "Provider profile already exists. Use update instead.");
        }


        ServiceCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException(
                        "Category not found: " + request.getCategoryId()));

        ServiceProvider provider = new ServiceProvider();
        provider.setUser(user);
        provider.setBusinessName(request.getBusinessName());
        provider.setCity(request.getCity());
        provider.setAddress(request.getAddress());
        provider.setHourlyRate(request.getHourlyRate());
        provider.setExperienceYears(request.getExperienceYears());
        provider.setDescription(request.getDescription());
        provider.setCategory(category);
        provider.setApprovalStatus(ApprovalStatus.PENDING);  // needs admin approval
        provider.setAvgRating(0.0);
        provider.setTotalReviews(0);

        ServiceProvider saved = providerRepository.save(provider);

        return convertToResponse(saved);
    }

    public ServiceProviderResponse updateProfile(
            CreateServiceProviderRequest request) {

        String email = getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "User not found: " + email));

        ServiceProvider provider = providerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException(
                        "Provider profile not found. Create one first."));


        provider.setBusinessName(request.getBusinessName());
        provider.setCity(request.getCity());
        provider.setAddress(request.getAddress());
        provider.setHourlyRate(request.getHourlyRate());
        provider.setExperienceYears(request.getExperienceYears());
        provider.setDescription(request.getDescription());

        if (request.getCategoryId() != null) {
            ServiceCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException(
                            "Category not found: " + request.getCategoryId()));
            provider.setCategory(category);
        }

        ServiceProvider updated = providerRepository.save(provider);
        return convertToResponse(updated);
    }

    public ServiceProviderResponse getProfileById(Long providerId) {
        ServiceProvider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException(
                        "Provider not found: " + providerId));
        return convertToResponse(provider);
    }

    private ServiceProviderResponse convertToResponse(ServiceProvider provider) {
        return new ServiceProviderResponse(
                provider.getId(),
                provider.getBusinessName(),
                provider.getCity(),
                provider.getAddress(),
                provider.getHourlyRate(),
                provider.getExperienceYears(),
                provider.getDescription(),
                provider.getCategory() != null ? provider.getCategory().getName() : "Unknown",
                provider.getAvgRating(),
                provider.getTotalReviews(),
                provider.getApprovalStatus().name(),
                provider.getUser() != null ? provider.getUser().getEmail() : "Unknown"
        );
    }

    public List<ServiceProviderResponse> searchProviders(
            String city, Long categoryId, String keyword) {

        List<ServiceProvider> providers;

        if (city != null && categoryId != null) {
            providers = providerRepository
                    .findByApprovalStatusAndCityAndCategoryId(
                            ApprovalStatus.APPROVED, city, categoryId);
        } else if (city != null) {
            providers = providerRepository
                    .findByApprovalStatusAndCity(
                            ApprovalStatus.APPROVED, city);
        } else if (categoryId != null) {
            providers = providerRepository
                    .findByApprovalStatus(ApprovalStatus.APPROVED);
            providers = providers.stream()
                    .filter(p -> p.getCategory().getId().equals(categoryId))
                    .collect(Collectors.toList());
        } else {
            providers = providerRepository
                    .findByApprovalStatus(ApprovalStatus.APPROVED);
        }
        if (keyword != null && !keyword.isEmpty()) {
            String kw = keyword.toLowerCase();
            providers = providers.stream()
                    .filter(p -> p.getBusinessName().toLowerCase().contains(kw) ||
                            p.getDescription().toLowerCase().contains(kw))
                    .collect(Collectors.toList());
        }

        return providers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ServiceProviderResponse> getPendingProviders() {
        return providerRepository.findByApprovalStatus(ApprovalStatus.PENDING)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ServiceProviderResponse approveProvider(Long providerId, Boolean approved, String remarks) {
        ServiceProvider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found: " + providerId));

        if (approved) {
            provider.setApprovalStatus(ApprovalStatus.APPROVED);
            emailService.sendApprovalEmail(
                    provider.getUser().getEmail(),
                    provider.getBusinessName(),
                    true,
                    "Your profile has been approved!"
            );
        } else {
            provider.setApprovalStatus(ApprovalStatus.REJECTED);
            emailService.sendApprovalEmail(
                    provider.getUser().getEmail(),
                    provider.getBusinessName(),
                    false,
                    remarks != null ? remarks : "Your profile was rejected."
            );
        }

        ServiceProvider updated = providerRepository.save(provider);
        return convertToResponse(updated);
    }

    public EarningsResponse getEarnings() {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ServiceProvider provider = providerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Provider profile not found"));

        List<Booking> bookings = bookingRepository.findByProvider(provider);

        double totalEarnings = 0;
        for (Booking booking : bookings) {
            if (booking.getStatus().name().equals("COMPLETED")) {
                totalEarnings += booking.getTotalAmount();
            }
        }
        double commission = totalEarnings * 0.10;
        double netEarnings = totalEarnings - commission;

        return new EarningsResponse(
                totalEarnings,
                commission,
                netEarnings,
                bookings.size()
        );
    }

    public List<TransactionResponse> getTransactions() {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ServiceProvider provider = providerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Provider profile not found"));

        List<Booking> bookings = bookingRepository.findByProvider(provider);

        return bookings.stream()
                .map(booking -> {
                    double commission = booking.getTotalAmount() * 0.10;
                    double netAmount = booking.getTotalAmount() - commission;
                    return new TransactionResponse(
                            booking.getId(),
                            booking.getCustomer().getFullName(),
                            booking.getTotalAmount(),
                            commission,
                            netAmount,
                            booking.getStatus().name(),
                            booking.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());
    }
}

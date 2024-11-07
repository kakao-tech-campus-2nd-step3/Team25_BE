package com.team25.backend.domain.admin.service;

import com.team25.backend.domain.admin.dto.response.AdminPageResponse;
import com.team25.backend.domain.manager.entity.Manager;
import com.team25.backend.domain.user.entity.User;
import com.team25.backend.domain.user.repository.UserRepository;
import com.team25.backend.domain.manager.entity.Certificate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public AdminService(UserRepository userRepository, S3Service s3Service) {
        this.userRepository = userRepository;
        this.s3Service = s3Service;
    }

    public List<AdminPageResponse> getAllUsersWithManagers() {
        String bucketName = "manager-app-storage";
        int duration_minute = 10;
        Duration duration = Duration.ofMinutes(duration_minute);

        return userRepository.findUsersWithManager().stream()
                .map(user -> {
                    Manager manager = user.getManager();
                    List<String> certificateImages = manager.getCertificates().stream()
                            .map(Certificate::getCertificateImage)
                            .map(objectKey -> s3Service.generatePresignedUrl(bucketName, objectKey,duration).toString())
                            .collect(Collectors.toList());

                    return new AdminPageResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getRole(),
                            manager.getId(),
                            manager.getManagerName(),
                            certificateImages
                    );
                })
                .collect(Collectors.toList());
    }

    public void changeUserRole(Long userId, String role) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateRole(role);
            userRepository.save(user);
        }
    }
}

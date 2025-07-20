package com.be.bus.domain.kakaoSocial.repository;

import com.be.bus.domain.kakaoSocial.entity.KakaoSocial;
import com.be.bus.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KakaoSocialRepository extends JpaRepository<KakaoSocial, Long> {


    Optional<KakaoSocial> findByKakaoMemberId(Long kakaoMemberId);
    Optional<KakaoSocial> findByUser(User user);
}
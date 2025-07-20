package com.be.bus.domain.kakaoSocial.entity;


import com.be.bus.domain.user.entity.User;
import com.be.bus.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "kakao_social")
@Entity
public class KakaoSocial extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kakao_social_id")
    private Long id;

    @Column(name = "kakao_member_id", nullable = false, unique = true)
    private Long kakaoMemberId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static KakaoSocial create(Long kakaoMemberId,  User user) {
        return KakaoSocial.builder()
                .kakaoMemberId(kakaoMemberId)
                .user(user)
                .build();
    }
}
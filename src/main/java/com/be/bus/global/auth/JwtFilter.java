package com.be.bus.global.auth;

import com.be.bus.global.error.exception.EntityNotFoundException;
import com.be.bus.global.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.be.bus.global.auth.error.AuthErrorCode.TOKEN_NOT_FOUND;


@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    // accessToken이 필요없는 화이트리스트 목록
    private static final List<RequestMatcher> whiteUrlMatchers = Arrays.asList(
            //TODO : 와일드카드 표현 삭제하기

            //auth
            new AntPathRequestMatcher("/api/auth/kakao-login"),
            new AntPathRequestMatcher("/api/auth/kakao-register"),
//            new AntPathRequestMatcher("/api/auth/reissue"),
//            new AntPathRequestMatcher("/api/auth/send-mail"),
//            new AntPathRequestMatcher("/api/auth/check-mail"),

            //user
            new AntPathRequestMatcher("/api/member/profile-image"),

            //badge

            //group

            //stamp
            new AntPathRequestMatcher("/api/stamp"),

            //send-praise
            new AntPathRequestMatcher("/api/send-praise/group"),
            new AntPathRequestMatcher("/api/send-praise/check"),

            //receive-praise
            // TODO : POST, DEL은 매치하도록 수정
            new AntPathRequestMatcher("/api/receive-praise"),


            // 헬스체크
            new AntPathRequestMatcher("/api/health/*"),

            // 스웨거
            new AntPathRequestMatcher("/swagger-ui/*"),
            new AntPathRequestMatcher("/v3/api-docs/**")
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("🔍 [REQUEST URI] {}", request.getRequestURI());
        log.info("🔐 [Authorization Header] {}", request.getHeader("Authorization"));
        for (RequestMatcher urlMatchers : whiteUrlMatchers) {
            if (urlMatchers.matches(request)) {
                filterChain.doFilter(request, response);
                return;
            }
        }


        String authorizationHeader = getAuthorizationHeaderFromRequest(request);
        String accessToken = splitAccessTokenFromHeader(authorizationHeader);
        jwtUtil.validateJwtToken(accessToken, "accessToken");
        Long userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        String role = String.valueOf(jwtUtil.getRoleFromAccessToken(accessToken));
        setAuthentication(request, userId, role);
        filterChain.doFilter(request, response);
    }

    private String getAuthorizationHeaderFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private String splitAccessTokenFromHeader(String accessToken) {

        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer")) {
            return accessToken.split(" ")[1]; //토큰 꺼내기
        }
        throw new EntityNotFoundException(TOKEN_NOT_FOUND);
    }

    private void setAuthentication(HttpServletRequest request, Long userId, String role) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority(role)));
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("[+] Token in SecurityContextHolder");
    }
}
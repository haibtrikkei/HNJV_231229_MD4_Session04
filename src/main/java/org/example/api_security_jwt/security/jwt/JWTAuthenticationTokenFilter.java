package org.example.api_security_jwt.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.api_security_jwt.security.principals.CustomUserDetailService;
import org.example.api_security_jwt.security.principals.CustomerUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Lấy chuỗi token từ request gửi tới
        String token = getTokenFromRequest(request);

        //Nếu token có thì sẽ lấy username từ token và truy vấn database để lấy thông tin tài khoản
        if(token!=null && !token.isEmpty()){
            String username = jwtProvider.getUsernameFromToken(token);
            CustomerUserDetail userDetail = (CustomerUserDetail) userDetailsService.loadUserByUsername(username);
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());

            //Set lại thông tin tài khoản đã đăng nhập vào SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);

    }

    private String getTokenFromRequest(HttpServletRequest request) {
        //Chuỗi token được gửi ở trường Authorization của Header
        String authorization = request.getHeader("Authorization");

        //Chuỗi jwt token đặt bắt đầu với hằng số Bearer nên phải tách hằng ra để lấy token
        if(authorization!=null && authorization.startsWith("Bearer ")){
            return authorization.substring("Bearer ".length());
        }
        return null;
    }
}

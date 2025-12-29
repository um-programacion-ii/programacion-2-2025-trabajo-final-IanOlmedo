package com.mycompany.myapp.config.filters;

import com.mycompany.myapp.service.dto.SesionRedisDTO;
import com.mycompany.myapp.service.impl.RedisSesionService;
import com.mycompany.myapp.service.impl.SesionServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SessionFilterRedis extends OncePerRequestFilter {

    private final SesionServiceImpl sesionService;

    private final RedisSesionService redisSesionService;

    public SessionFilterRedis(SesionServiceImpl sesionService, RedisSesionService redisSesionService) {
        this.sesionService = sesionService;
        this.redisSesionService = redisSesionService;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/api/authenticate")
            || path.startsWith("/api/register")
            || path.startsWith("/api/internal/")
            || path.startsWith("/h2-console")
            || path.startsWith("/actuator/")
            || path.startsWith("/swagger")
            || path.startsWith("/v3/api-docs")
            || path.startsWith("/error");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        String token = request.getHeader("X-SESSION-TOKEN");

        // ✅ Validar que el token existe y es válido
        if (token == null || token.isBlank()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Session token requerido\"}");
            return;
        }

        try {
            // Verificar que la sesión existe y está activa
            SesionRedisDTO sesion = redisSesionService.obtenerDeCache(token);

            if (sesion == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Sesión inválida o expirada\"}");
                return;
            }

            // Renovar actividad de la sesión
            sesionService.renovarActividad(token);

            // Continuar con la petición
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Error al validar sesión: " + e.getMessage() + "\"}");
        }
    }
}

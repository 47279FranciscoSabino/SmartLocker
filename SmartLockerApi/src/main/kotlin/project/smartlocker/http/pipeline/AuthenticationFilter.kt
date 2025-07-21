package project.smartlocker.http.pipeline

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import project.smartlocker.repository.UserRepository
import java.util.*

@Component
class AuthTokenFilter(
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    private val publicPaths = listOf("/api/login", "/api/signup", "/api/map")

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (publicPaths.contains(request.servletPath)) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header")
            return
        }

        val tokenValue = authHeader.removePrefix("Bearer ").trim()

        try {
            val token = UUID.fromString(tokenValue)
            val user = userRepository.getUserByToken(token)

            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token")
                return
            }

            request.setAttribute("authenticatedUser", user)
            filterChain.doFilter(request, response)
        } catch (e: IllegalArgumentException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Malformed token")
        }
    }
}
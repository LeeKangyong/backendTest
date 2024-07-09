package kr.co.polycube.backendtest.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SpecialCharacterFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SpecialCharacterFilter.class);
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^\\w\\s?&=:-]+");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String queryString = httpRequest.getQueryString();
        logger.info("Query String: {}", queryString);

        if (queryString != null && SPECIAL_CHAR_PATTERN.matcher(queryString).find()) {
            logger.error("Invalid characters found in query string: {}", queryString);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ServletOutputStream out = httpResponse.getOutputStream();
            objectMapper.writeValue(out, new ErrorResponseDto("Invalid characters in query string"));
            out.flush();
            return;
        }

        chain.doFilter(request, response);
    }

    static class ErrorResponseDto {
        public String reason;

        public ErrorResponseDto(String reason) {
            this.reason = reason;
        }
    }
}
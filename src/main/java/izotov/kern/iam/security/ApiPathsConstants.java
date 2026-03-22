package izotov.kern.iam.security;

public enum ApiPathsConstants {
    
    OPEN_API("/actuator/health",
            
            "/kern/v1/users",
            "/kern/v1/user/create",
            
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/webjars/**",
            "/swagger-resources/**"
    );
    
    //AUTHENTICATED_API("/kern/users");
    
    final String[] paths;
    
    ApiPathsConstants(String... paths) {
        this.paths = paths;
    }
    
    public String[] paths() {
        return paths;
    }
}

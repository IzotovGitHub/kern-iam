package izotov.kern.iam.repository.migration.properties;

public interface LiquibaseProperties {
    
    void setEnabled(boolean enabled);
    
    void setChangeLog(String changeLog);
    
    void setDropFirst(boolean dropFirst);
    
    boolean isEnabled();
    
    boolean isDropFirst();
    
    String getChangeLog();
    
}

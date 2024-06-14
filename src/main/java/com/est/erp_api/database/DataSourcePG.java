package com.est.erp_api.database;

public class DataSourcePG implements DataSource {
    public static final String URL = "r2dbc:h2:mem:///erp_db?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    public static final String USER = "erp";
    public static final String PASSWORD = "erp_erp_erp";
}

package com.innovetsolutions.delushapiservice.rowMappers;

import com.innovetsolutions.delushapiservice.model.OrderDelivery;
import com.innovetsolutions.delushapiservice.model.StoreSettings;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreSettingsWrapper implements RowMapper<StoreSettings> {
    @Override
    public StoreSettings mapRow(ResultSet rs, int rowNum) throws SQLException {

        StoreSettings settings = new StoreSettings();

        settings.setBulk_discount(rs.getInt("BULK_DISCOUNT"));
        settings.setPayment_method(rs.getString("PAYMENT_METHOD"));
        settings.setDelivery_fee(rs.getDouble("DELIVERY_FEE"));
        settings.setDelivery_pack(rs.getDouble("DELIVERY_PACK"));
        return settings;
    }
}

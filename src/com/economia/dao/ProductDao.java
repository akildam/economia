package com.economia.dao;

import com.economia.bean.Product;
import com.economia.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Department Data Access Object.
 */
public class ProductDao {
   
    private Connection connection = null;
    
    /**
     * DepartmentDao constructor. Initializes the database connection.
     */
    public ProductDao() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    /**
     * Inserts a department. If this department is already registered, nothing will be recorded in the database.
     * @param product
     */
    public void insert(Product product){
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO product(product_external_id, product_name, product_measure_unity, product_quantity, dept_id) VALUES(?,?,?,?,?)";
            
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,product.getExternalId());
            preparedStatement.setString(2,product.getName());
            preparedStatement.setString(3,product.getMeasureUnity());
            preparedStatement.setInt(4,product.getQuantity());
            preparedStatement.setInt(5,product.getDeptId());
            preparedStatement.executeUpdate();
            
        } catch (SQLIntegrityConstraintViolationException e) {
            // Nothing is done when trying to insert an existent product.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            /*
            // @TODO Fix this! Need to use the same connection.
            if (connection != null) { 
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/
        }
    }
}

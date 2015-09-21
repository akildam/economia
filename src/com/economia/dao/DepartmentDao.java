package com.economia.dao;

import com.economia.bean.Department;
import com.economia.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Department Data Access Object.
 */
public class DepartmentDao {
   
    private Connection connection = null;
    
    /**
     * DepartmentDao constructor. Initializes the database connection.
     */
    public DepartmentDao() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    /**
     * Inserts a department. If this department is already registered, nothing will be recorded in the database.
     * @param department
     * @return dept_id
     */
    public int insert(Department department){
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            String sql = "INSERT INTO department(dept_external_id, dept_name) VALUES(?,?)";
            
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,department.getExternalId());
            preparedStatement.setString(2,department.getName());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                result = rs.getInt(1);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Nothing is done when trying to insert an existent department.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return result;
    }
    
    /**
     * Inserts a list of departments.
     * @param departments 
     */
    public void insert(List<Department> departments){
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO department(dept_external_id, dept_name) VALUES(?,?)";
            
            preparedStatement = connection.prepareStatement(sql);
            
            for(Department dept : departments){
                preparedStatement.setInt(1,dept.getId());
                preparedStatement.setString(2,dept.getName());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new RuntimeException(e.getNextException());
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) { 
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

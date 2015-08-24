package com.economia.dao;

import com.economia.bean.Department;
import com.economia.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
     * Inserts a department.
     * @param Department department 
     */
    public void insert(Department department){
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO department(dept_id_external, dept_name) VALUES(?,?)";
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,department.getId());
            preparedStatement.setString(2,department.getName());
            preparedStatement.execute();

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
        }
    }
    
    /**
     * Inserts a list of departments.
     * @param departments 
     */
    public void insert(List<Department> departments){
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO department(dept_id_external, dept_name) VALUES(?,?)";
            
            preparedStatement = connection.prepareStatement(sql);
            
            for(Department dept : departments){
                preparedStatement.setString(1,dept.getId());
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

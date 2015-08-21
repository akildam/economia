package com.economia.dao;

import com.economia.bean.Department;
import com.economia.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Renato
 */
public class DepartmentDao {
   
    private Connection connection = null;

    public DepartmentDao() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    /**
     * Insert department.
     * @param department 
     */
    public void insert(Department department){
        
    }
    
    /**
     * Insert a list of departments.
     * @param departments 
     */
    public void insert(List<Department> departments){
        PreparedStatement stmt = null;
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO department(dept_id_external, dept_name) VALUES(?,?)";
            
            stmt = connection.prepareStatement(sql);
            
            for(Department dept : departments){
                stmt.setString(1,dept.getId());
                stmt.setString(2,dept.getName());
                stmt.addBatch();
            }
            stmt.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                try { stmt.close();} 
                catch (SQLException e) {throw new RuntimeException(e);}
            }
            if (connection != null) { 
                try { connection.close();}
                catch (SQLException e) {throw new RuntimeException(e);}
            }
        }
    }
    
    /*ContatoDao dao = new ContatoDao();
    dao.exclui(contato);

    PreparedStatement pstmt = conn.prepareStatement(INSERT_RECORD);
    pstmt.setString(1, "1");
    pstmt.setString(2, "name1");
    pstmt.addBatch();

    pstmt.setString(1, "2");
    pstmt.setString(2, "name2");
    pstmt.addBatch();*/

    /*Connection conn =  new ConnectionFactory().getConnection();
    String sql = "INSERT INTO departamento(dept_id_externo, dept_name) VALUES(?,?)";*/
}

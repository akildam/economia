package com.economia.webscraping;

import com.economia.bean.Product;
import com.economia.bean.Department;
import com.economia.dao.DepartmentDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Renato
 */
public class WebScraping {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        WebScraping ws = new WebScraping();
        
        List<Department> departments = ws.geDepartments();
        DepartmentDao departmentDao = new DepartmentDao();
        
        for(Department dept : departments){
            departmentDao.insert(dept);
        }
    }
    
    /**
     * Get departments from "Disque Economia" site.
     * @return A list of departments.
     */
    public ArrayList<Department> geDepartments(){
        ArrayList<Department> departments = new ArrayList();
        try {
            Document doc = Jsoup.connect("http://disqueeconomia.curitiba.pr.gov.br/").get();
            Element content = doc.getElementsByClass("lista_menu").first();
            Elements aList = content.getElementsByTag("a");
            
            for(Element a : aList){
                String[] arrHref = a.attr("href").split("=");
                if(arrHref.length > 1){
                    Element span = a.getElementsByTag("span").first();
                    Department dept = new Department();
                    dept.setId(arrHref[1]);
                    dept.setName(span.text());
                    departments.add(dept);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WebScraping.class.getName()).log(Level.SEVERE, null, ex);
        }
        return departments;
    }
    
    /**
     * Get all products from a given department. The data is collected from "Disque Economia" site.
     * @param dept A department object.
     * @return A list of products.
     */
    public ArrayList<Product> getProductsByDepartment(Department dept){
        ArrayList<Product> products = new ArrayList();
        try {
            Document doc = Jsoup.connect("http://disqueeconomia.curitiba.pr.gov.br/default.asp?GrPCod=" + dept.getId()).get();
            Element content = doc.getElementsByClass("tabela").first();
            Elements trList = content.getElementsByTag("tr");

            for(Element tr : trList){
                Elements tdList = tr.getElementsByTag("td");
                if(tr.hasClass("cabecalho")){
                    continue;
                }
                Product product = new Product();
                product.setId(tdList.get(0).child(0).val());
                product.setName(tdList.get(0).text().trim());
                product.setQuantity(Integer.parseInt(tdList.get(1).text().trim()));
                product.setMeasureUnity(tdList.get(2).text().trim());
                product.setDeptId(dept.getId());
                products.add(product);
            }
        } catch (IOException ex) {
            Logger.getLogger(WebScraping.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }
    
}

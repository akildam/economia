package com.economia.webscraping;

import com.economia.bean.Product;
import com.economia.bean.Department;
import com.economia.dao.DepartmentDao;
import com.economia.dao.ProductDao;
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
 * Main class.
 */
public class WebScraping {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        WebScraping ws = new WebScraping();
        
        List<Department> departments = ws.geDepartments();
        DepartmentDao departmentDao = new DepartmentDao();
        ProductDao productDao = new ProductDao();
        
        for(int i = 0; i < departments.size(); i++){
            Integer result = departmentDao.insert(departments.get(i));
            departments.get(i).setId(result); // @TODO fix: when nothing is inserted on database, ID will be 0 and products will try to add a product.dept_id = 0;
        }
        
        for(Department dept : departments){
            List<Product> products = ws.getProductsByDepartment(dept);
            for(Product product : products){
                productDao.insert(product);
            }
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
                    dept.setExternalId(arrHref[1]);
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
            String url = "http://disqueeconomia.curitiba.pr.gov.br/default.asp?GrPCod=" + dept.getExternalId();
            
            Document doc = Jsoup.connect(url).get();
            Element content = doc.getElementsByClass("tabela").first();
            Elements trList = content.getElementsByTag("tr");

            for(Element tr : trList){
                Elements tdList = tr.getElementsByTag("td");
                if(tr.hasClass("cabecalho")){
                    continue;
                }
                Product product = new Product();
                product.setExternalId(tdList.get(0).child(0).val());
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

package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;


public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findbyId ===");

        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println();

        System.out.println("=== TEST 2: seller findbyDepartment ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);

        for(Seller obj: list) {
            System.out.println(obj);
        }

        System.out.println();

        System.out.println("=== TEST 3: seller findAll ===");

        list = sellerDao.findAll();

        for(Seller obj: list) {
            System.out.println(obj);
        }

        System.out.println();


        System.out.println("=== TEST 4: seller insert ===");

        Seller seller1 = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);

        sellerDao.insert(seller1);
        System.out.println("Inserted new id = " + seller1.getId());


        System.out.println();

        System.out.println("=== TEST 5: seller update ===");
        Seller seller2 = sellerDao.findById(2);
        seller2.setName("Martha Wayne");
        sellerDao.update(seller2);

        System.out.println();

        System.out.println("=== TEST 6: seller delete ===");
        sellerDao.deleteById(2);

    }

}

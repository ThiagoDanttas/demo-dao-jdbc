package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;


public class Program2 {

    public static void main(String[] args){

        DepartmentDao dao = DaoFactory.createDepartmentDao();

        System.out.println("==== Teste 1: Department findById ====");
        Department dep = dao.findById(4);
        System.out.println(dep);


        System.out.println();
        System.out.println("==== Teste 2: Department insert ====");
        Department dep1 = new Department(6, "toys");
        dao.insert(dep1);


        System.out.println();
        System.out.println("==== Teste 3: Department update ====");
        Department dep2 = new Department(3, "foods");
        dao.update(dep2);


        System.out.println();
        System.out.println("==== Teste 4: Department update ====");
        dao.deleteById(6);



        System.out.println();
        System.out.println("==== Teste 5: Department findAll ====");
        List<Department> list = dao.findAll();

        for(Department dep3: list) {
            System.out.println(dep3);
        }


    }

}

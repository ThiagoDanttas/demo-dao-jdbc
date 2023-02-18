package model.dao.impl;

import db.src.DB;
import db.src.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.*;


public class SellerDaoJDBC implements SellerDao {

    private final Connection connection;


    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

        PreparedStatement ps = null;

        try {

            String sql = "INSERT INTO coursejdbc.seller (Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES (?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, obj.getDepartment().getId());

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                } else {
                    throw new DbException("Unexpected error! no rows affected");
                }
                DB.closeResultSet(rs);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }


    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT seller.*, department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE seller.id = ?";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if(rs.next()) {
                Department dep = instatiateDepartment(rs);
                Seller obj = instatiateSeller(rs, dep);
                return obj;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
        return null;
    }

    // Métodos auxiliares
    private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep);
        return obj;
    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }


    @Override
    public List<Seller> findAll() {

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Seller> list = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>();

        try {
            String sql = "SELECT seller.*, department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "ORDER BY Name";

            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null) {
                    dep = instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentID"), dep);
                }

                Seller obj = instatiateSeller(rs, dep);
                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }

        return list;

    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Seller> list = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>();

        try {
            String sql = "SELECT seller.*, department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentId = ? " +
                    "ORDER BY Name";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, department.getId());

            rs = ps.executeQuery();

            while(rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null) {
                    dep = instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentID"), dep);
                }

                Seller obj = instatiateSeller(rs, dep);
                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
        return list;
    }

}

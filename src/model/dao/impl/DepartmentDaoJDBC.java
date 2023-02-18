package model.dao.impl;

import db.src.DB;
import db.src.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department obj) {

        PreparedStatement ps = null;

        try {

        String sql = "INSERT INTO coursejdbc.department (Id, Name) " +
                "VALUES (?, ?)";

        ps = connection.prepareStatement(sql);
        ps.setInt(1, obj.getId());
        ps.setString(2, obj.getName());

        int inserted = ps.executeUpdate();

        if(inserted > 0) {
            System.out.println("Done! Department inserted");
        } else  {
            throw new DbException("Error! Department not inserted");
        }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Department obj) {

        PreparedStatement ps = null;

        try {
            String sql = "UPDATE coursejdbc.department " +
                    "SET id = ?, Name = ? " +
                    "WHERE id = ? ";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, obj.getId());
            ps.setString(2, obj.getName());
            ps.setInt(3, obj.getId());

            int updated = ps.executeUpdate();


            if(updated > 0) {
                System.out.println("Done ! Department ");
            } else {
                throw new DbException("Error ! Department not updated");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement ps = null;

        try {

        String sql = "DELETE FROM coursejdbc.department " +
                "WHERE id = ? ";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);


            int deleted = ps.executeUpdate();

            if(deleted > 0) {
                System.out.println("Done! Department deleted");
            } else {
                throw new DbException("Error! Department not deleted");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM coursejdbc.department " +
                    "WHERE id = ? ";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if(rs.next()) {
                return instatiateDepartment(rs);
            } else {
                throw new DbException("id not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }

        return null;

    }

    // Método auxiliar
    public static Department instatiateDepartment(ResultSet resultSet) throws SQLException {
        Department dep = new Department();
        dep.setId(resultSet.getInt("id"));
        dep.setName(resultSet.getString("Name"));
        return dep;
    }

    @Override
    public List<Department> findAll() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Department> list = new ArrayList<>();
        try {

            String sql = "SELECT * FROM coursejdbc.department";

            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(instatiateDepartment(rs));
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
        return null;
    }
}

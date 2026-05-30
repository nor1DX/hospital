package hospital.repository.jdbc;

import hospital.config.DatabaseConfig;
import hospital.model.entity.Gender;
import hospital.model.entity.Patient;
import hospital.repository.PatientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientRepositoryJdbc implements PatientRepository {

    @Override
    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            return insert(patient);
        }
        return update(patient);
    }

    private Patient insert(Patient patient) {
        String sql = "INSERT INTO patients (full_name, age, gender, department_id) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getFullName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender().name());
            stmt.setLong(4, patient.getDepartmentId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                patient.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patient;
    }

    private Patient update(Patient patient) {
        String sql = "UPDATE patients SET full_name = ?, age = ?, gender = ?, department_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getFullName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender().name());
            stmt.setLong(4, patient.getDepartmentId());
            stmt.setLong(5, patient.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patient;
    }

    @Override
    public Optional<Patient> findById(Long id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> findAll() {
        String sql = "SELECT * FROM patients ORDER BY id";
        List<Patient> result = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Patient> findByDepartmentId(Long departmentId) {
        String sql = "SELECT * FROM patients WHERE department_id = ? ORDER BY id";
        List<Patient> result = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM patients WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private Patient mapRow(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getLong("id"),
                rs.getString("full_name"),
                rs.getInt("age"),
                Gender.valueOf(rs.getString("gender")),
                rs.getLong("department_id")
        );
    }
}

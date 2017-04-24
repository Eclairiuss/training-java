package fr.ebiz.nurdiales.trainingJava.mapper;

import fr.ebiz.nurdiales.trainingJava.dto.CompanyDTO;
import fr.ebiz.nurdiales.trainingJava.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebiz on 20/04/17.
 */
public class CompanyMapper {
    private static final String COMPANY_NAME = "name";
    private static final String COMPANY_ID = "id";

    /**
     * Map a List of Company from a resultSet.
     * @param rs ResultSet from companydao.
     * @return list of company.
     * @throws SQLException Error in SQL.
     */
    public static List<Company> map2Object(ResultSet rs) throws SQLException {
        List<Company> list = new ArrayList<Company>();
        while (rs.next()) {
            list.add(new Company(rs.getInt(COMPANY_ID), rs.getString(COMPANY_NAME)));
        }
        return list;
    }

    /**
     * Map a list of CompanyDTO from a list of Company.
     * @param companies list of company.
     * @return list of CompanyDTO corresponding to companies.
     */
    public static List<CompanyDTO> map2DTO(List<Company> companies) {
        List<CompanyDTO> list = new ArrayList<CompanyDTO>();
        companies.forEach(c -> list.add(new CompanyDTO(c)));
        return list;
    }

    /**
     * Get the first object from the ResultSet.
     * @param rs ResultSet form a search.
     * @return Company corresponding to the first object from rs.
     * @throws SQLException Error in SQL.
     */
    public static Company toObject(ResultSet rs) throws SQLException {
        Company c = null;
        if (rs.next()) {
            c = new Company(rs.getInt(COMPANY_ID), rs.getString(COMPANY_NAME));
        }
        return c;
    }
}

package fr.ebiz.nurdiales.trainingJava.mapper;

import fr.ebiz.nurdiales.trainingJava.Company;
import fr.ebiz.nurdiales.trainingJava.Computer;
import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.service.CompanyManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComputerMapperTest {

    private static final int ID = 1;
    private static final int ID_COMPANY = 2;
    private static final String NAME = "NameComputer";
    private static final String NAME_COMPANY = "NameCompany";
    private static final Date INTRODUCED = Date.valueOf("1990-01-01");
    private static final Date DISCONTINUED = Date.valueOf("1991-01-01");
    private static final Company COMPANY = new Company(ID_COMPANY, NAME_COMPANY);

    @Mock
    private ResultSet resultSet;
    @Mock
    private CompanyManager companyManager;
    @Mock
    private ComputerMapper computerMapper;

    /**
     * Settings before the test.
     * @throws Exception Because.
     */
    @Before
    public void beforeTest() throws Exception {
        MockitoAnnotations.initMocks(this);
        computerMapper = mock(ComputerMapper.class);
        resultSet = mock(ResultSet.class);
        companyManager = mock(CompanyManager.class);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyString())).thenReturn(ID).thenReturn(ID_COMPANY);
        when(resultSet.getString(anyString())).thenReturn(NAME);
        when(resultSet.getDate(anyString())).thenReturn(INTRODUCED).thenReturn(DISCONTINUED);

//        when(computerMapper.getClass().getDeclaredField("COMPANY_MANAGER").get(anyInt())).thenReturn(COMPANY); //try fails
//        Deencapsulation.setField(ComputerMapper.class.getDeclaredField("COMPANY_MANAGER"), companyManager); //try fails
        setFinalStatic(ComputerMapper.class.getDeclaredField("COMPANY_MANAGER"), companyManager);

        when(companyManager.get(anyInt())).thenReturn(COMPANY);
    }

    /**
     * test for the method getById.
     * @throws SQLException Because.
     * @throws CompanyDAOException Because.
     */
    @Test
    public void testGetById() throws SQLException, CompanyDAOException {
        Computer c = computerMapper.toObject(resultSet);
        assertTrue((c.getId() == ID)
                           && (c.getName().equals(NAME))
                           && (c.getIntroduced().equals(INTRODUCED))
                           && (c.getDiscontinued().equals(DISCONTINUED))
                           && (c.getCompany().equals(COMPANY)));
    }

    /**
     * Access and update a private final static with new Object, very dirty ??
     * Found on http://stackoverflow.com/questions/30703149/mock-private-static-final-field-using-mockito-or-jmockit.
     * @param field Object to modify.
     * @param newValue new value.
     * @throws Exception Because.
     */
    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
        field.setAccessible(false);
    }
}

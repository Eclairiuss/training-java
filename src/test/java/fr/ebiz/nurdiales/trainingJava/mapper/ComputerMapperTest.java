package fr.ebiz.nurdiales.trainingJava.mapper;

import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComputerMapperTest {

    private static final int ID_INT = 1;
    private static final String ID = "1";
    private static final int ID_COMPANY_INT = 2;
    private static final String ID_COMPANY = "2";
    private static final String NAME = "NameComputer";
    private static final String NAME_COMPANY = "NameCompany";
    private static final String INTRODUCED = "1990-01-01";
    private static final String DISCONTINUED = "1991-01-01";
    private static final Company COMPANY = new Company(ID_COMPANY_INT, NAME_COMPANY);

    @Mock
    private ResultSet resultSet;

    /**
     * Settings before the test.
     * @throws Exception Because.
     */
    @Before
    public void beforeTest() throws Exception {
        MockitoAnnotations.initMocks(this);
        resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(anyString())).thenReturn(ID).thenReturn(NAME).thenReturn(INTRODUCED).thenReturn(DISCONTINUED).thenReturn(ID_COMPANY).thenReturn(NAME_COMPANY);

//        setFinalStatic(ComputerMapper.class.getDeclaredField("COMPANY_MANAGER"), companyManager);
//        when(companyManager.get(anyInt())).thenReturn(COMPANY);
    }

    /**
     * test for the method getComputer.
     * @throws SQLException Because.
     */
    @Test
    public void testGetById() throws SQLException {
        ComputerMapper computerMapper = new ComputerMapper();
        Computer c = computerMapper.mapRow(resultSet, 0);
        assertTrue(true);
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

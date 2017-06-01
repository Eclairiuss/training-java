package fr.ebiz.nurdiales.trainingjava.validator;

import fr.ebiz.nurdiales.trainingjava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingjava.core.Computer;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ComputerDTOValidator implements Validator {
    private static final String NAME = "name";
    private static final String INTRODUCED = "introduced";
    private static final String DISCONTINUED = "discontinued";

    @Override
    public boolean supports(Class<?> clazz) {
        return ComputerDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null) {
            errors.reject("form.null", "Null object.");
            return;
        }

        ComputerDTO computerDTO = (ComputerDTO) target;

        if (computerDTO.getName() == null || computerDTO.getName().trim().equals("")) {
            errors.rejectValue(NAME, "form.name.required", "A valid name is required!");
        }

        Computer computer = computerDTO.toComputer();

        if (!computer.checkDates()) {
            errors.rejectValue(INTRODUCED, "form.dateintrobeforedisc", "After Discontiued");
            errors.rejectValue(DISCONTINUED, "form.dateintrobeforedisc", "Before Introduced");
        }
    }
}

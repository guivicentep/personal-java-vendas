package br.com.gvp.vendas.validation.constraint;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.gvp.vendas.validation.NotEmptyList;

@SuppressWarnings("rawtypes")
public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List>{

	@Override
	public boolean isValid(List list, ConstraintValidatorContext context) {
		return list != null & !list.isEmpty();
	}
	
	@Override
	public void initialize(NotEmptyList constraintAnnotation) {
		
	}

}
